package mx.sugus.yang0.analysis.eval;

import mx.sugus.yang0.analysis.Compilation;
import mx.sugus.yang0.analysis.binding.BoundAssignmentExpression;
import mx.sugus.yang0.analysis.binding.BoundBinaryExpression;
import mx.sugus.yang0.analysis.binding.BoundBlockStatement;
import mx.sugus.yang0.analysis.binding.BoundBreakStatement;
import mx.sugus.yang0.analysis.binding.BoundContinueStatement;
import mx.sugus.yang0.analysis.binding.BoundDeclareStatement;
import mx.sugus.yang0.analysis.binding.BoundExpression;
import mx.sugus.yang0.analysis.binding.BoundExpressionStatement;
import mx.sugus.yang0.analysis.binding.BoundIfStatement;
import mx.sugus.yang0.analysis.binding.BoundLiteralExpression;
import mx.sugus.yang0.analysis.binding.BoundParentExpression;
import mx.sugus.yang0.analysis.binding.BoundStatement;
import mx.sugus.yang0.analysis.binding.BoundUnaryExpression;
import mx.sugus.yang0.analysis.binding.BoundVariableExpression;
import mx.sugus.yang0.analysis.binding.BoundWhileStatement;

public class Eval {

  private final Compilation compilation;
  private EvalState state;

  public Eval(Compilation compilation) {
    this.compilation = compilation;
    this.state = compilation.getScope();
  }

  public Result eval() {
    return evalStatement(compilation.getStatement());
  }

  private Result evalStatement(BoundStatement node) {
    var kind = node.getKind();
    switch (kind) {
      case ExpressionStatement:
        return evalExpressionStatement((BoundExpressionStatement) node);
      case BlockStatement:
        return evalBlockStatement((BoundBlockStatement) node);
      case DeclareStatement:
        return evalDeclareExpression((BoundDeclareStatement) node);
      case IfStatement:
        return evalIfStatement((BoundIfStatement) node);
      case WhileStatement:
        return evalWhileStatement((BoundWhileStatement) node);
      case BreakStatement:
        return evalBreakStatement((BoundBreakStatement) node);
      case ContinueStatement:
        return evalContinueStatement((BoundContinueStatement) node);
      default:
        throw new IllegalStateException("unknown bound statement kind: " + kind);
    }
  }

  private Result evalContinueStatement(BoundContinueStatement node) {
    return Result.Continue;
  }

  private Result evalBreakStatement(BoundBreakStatement node) {
    return Result.Break;
  }

  private Result evalWhileStatement(BoundWhileStatement node) {
    var condition = node.getCondition();
    var conditionResult = evalExpression(condition);
    var result = Result.None;
    while (Boolean.TRUE.equals(conditionResult.getValue())) {
      result = evalStatement(node.getBody());
      conditionResult = evalExpression(condition);

      System.out.printf(
          "result: %s,%s; condition: %s, %s\n",
          result.getValue(),
          result.getState(),
          conditionResult.getValue(),
          conditionResult.getState());

      if (result == Result.Break) {
        break;
      }
    }
    return result;
  }

  private Result evalIfStatement(BoundIfStatement node) {
    var condition = node.getCondition();
    var result = evalExpression(condition);
    if (Boolean.TRUE.equals(result.getValue())) {
      return evalStatement(node.getBody());
    } else if (node.getElseKeyword() != null) {
      return evalStatement(node.getElseBody());
    } else {
      return Result.False;
    }
  }

  private Result evalDeclareExpression(BoundDeclareStatement node) {
    var result = evalExpression(node.getInitExpression());
    state.declare(node.getSymbol(), result.getValue());
    return result;
  }

  private Result evalExpressionStatement(BoundExpressionStatement node) {
    return evalExpression(node.getExpression());
  }

  private Result evalBlockStatement(BoundBlockStatement node) {
    Result result = null;
    state = state.push();
    for (BoundStatement statement : node.getStatements()) {
      result = evalStatement(statement);
      if (result == Result.Continue || result == Result.Break) {
        return result;
      }
    }
    state = state.pop();
    return result;
  }

  private Result evalExpression(BoundExpression node) {
    var kind = node.getKind();
    switch (kind) {
      case LiteralExpression:
        return evalLiteralExpression((BoundLiteralExpression) node);
      case AssignmentExpression:
        return evalAssignmentExpression((BoundAssignmentExpression) node);
      case VariableExpression:
        return evalVariableExpression((BoundVariableExpression) node);
      case ParentExpression:
        return evalParentExpression((BoundParentExpression) node);
      case BinaryExpression:
        return evalBinaryExpression((BoundBinaryExpression) node);
      case UnaryExpression:
        return evalUnaryExpression((BoundUnaryExpression) node);
      default:
        throw new IllegalStateException("unexpected node kind: " + kind);
    }
  }

  private Result evalParentExpression(BoundParentExpression expr) {
    return evalExpression(expr.getExpression());
  }

  private Result evalLiteralExpression(BoundLiteralExpression expr) {
    return Result.from(expr.getValue());
  }

  private Result evalVariableExpression(BoundVariableExpression expr) {
    return Result.from(state.getValue(expr.getSymbol()));
  }

  private Result evalAssignmentExpression(BoundAssignmentExpression expr) {
    var result = evalExpression(expr.getInitializer());
    state.setValue(expr.getSymbol(), result.getValue());
    return result;
  }

  private Result evalUnaryExpression(BoundUnaryExpression expr) {
    var operand = evalExpression(expr.getBoundOperand()).getValue();
    switch (expr.getOperatorKind()) {
      case Identity:
        return Result.from(operand);
      case Negation:
        return Result.from(-(long) operand);
      case LogicalNot:
        return Result.from(!(boolean) operand);
      default:
        throw new IllegalStateException(
            "unexpected unary operator kind: " + expr.getOperatorKind());
    }
  }

  private Result evalBinaryExpression(BoundBinaryExpression expr) {
    var left = evalExpression(expr.getLeft()).getValue();
    switch (expr.getOperatorKind()) {
      case Addition:
        return Result.from((long) left + (long) evalExpression(expr.getRight()).getValue());
      case Subtraction:
        return Result.from((long) left - (long) evalExpression(expr.getRight()).getValue());
      case Multiplication:
        return Result.from((long) left * (long) evalExpression(expr.getRight()).getValue());
      case Division:
        return Result.from((long) left / (long) evalExpression(expr.getRight()).getValue());
      case Modulo:
        return Result.from((long) left % (long) evalExpression(expr.getRight()).getValue());
      case StringConcatenation:
      case StringBoolConcatenation:
      case StringLongConcatenation:
        return Result.from(left.toString() + evalExpression(expr.getRight()).getValue().toString());
      case LongEquality:
      case BooleanEquality:
        return Result.from(left.equals(evalExpression(expr.getRight())));
      case BooleanNonEquality:
      case LongNonEquality:
        return Result.from(!left.equals(evalExpression(expr.getRight())));
      case RelationalLessThan:
        return Result.from((long) left < (long) evalExpression(expr.getRight()).getValue());
      case RelationalLessThanEquals:
        return Result.from((long) left <= (long) evalExpression(expr.getRight()).getValue());
      case RelationalGraterThan:
        return Result.from((long) left > (long) evalExpression(expr.getRight()).getValue());
      case RelationalGraterThanEquals:
        return Result.from((long) left >= (long) evalExpression(expr.getRight()).getValue());
      case LogicalAnd:
        return Result.from((boolean) left && (boolean) evalExpression(expr.getRight()).getValue());
      case LogicalOr:
        return Result.from((boolean) left || (boolean) evalExpression(expr.getRight()).getValue());
      default:
        throw new IllegalStateException(
            "unexpected binary operator kind: " + expr.getOperatorKind());
    }
  }

}
