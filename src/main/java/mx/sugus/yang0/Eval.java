package mx.sugus.yang0;

import mx.sugus.yang0.analysis.Compilation;
import mx.sugus.yang0.analysis.binding.BoundAssignmentExpression;
import mx.sugus.yang0.analysis.binding.BoundBinaryExpression;
import mx.sugus.yang0.analysis.binding.BoundBlockStatement;
import mx.sugus.yang0.analysis.binding.BoundDeclareStatement;
import mx.sugus.yang0.analysis.binding.BoundExpression;
import mx.sugus.yang0.analysis.binding.BoundExpressionStatement;
import mx.sugus.yang0.analysis.binding.BoundIfStatement;
import mx.sugus.yang0.analysis.binding.BoundLiteralExpression;
import mx.sugus.yang0.analysis.binding.BoundParentExpression;
import mx.sugus.yang0.analysis.binding.BoundStatement;
import mx.sugus.yang0.analysis.binding.BoundUnaryExpression;
import mx.sugus.yang0.analysis.binding.BoundVariableExpression;
import mx.sugus.yang0.analysis.binding.EvalState;

public class Eval {

  private final Compilation compilation;
  private EvalState state;

  public Eval(Compilation compilation) {
    this.compilation = compilation;
    this.state = compilation.getScope();
  }

  public Object eval() {
    return evalStatement(compilation.getStatement());
  }

  private Object evalStatement(BoundStatement node) {
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
      default:
        throw new IllegalStateException("unknown bound statement kind: " + kind);
    }
  }

  private Object evalIfStatement(BoundIfStatement node) {
    var condition = node.getCondition();
    var object = evalExpression(condition);
    if (Boolean.TRUE.equals(object)) {
      return evalStatement(node.getBody());
    }
    return evalStatement(node.getElseBody());
  }

  private Object evalDeclareExpression(BoundDeclareStatement node) {
    var value = evalExpression(node.getInitExpression());
    state.declare(node.getSymbol(), value);
    return value;
  }

  private Object evalExpressionStatement(BoundExpressionStatement node) {
    return evalExpression(node.getExpression());
  }

  private Object evalBlockStatement(BoundBlockStatement node) {
    Object value = null;
    state = state.push();
    for (BoundStatement statement : node.getStatements()) {
      value = evalStatement(statement);
    }
    state = state.pop();
    return value;
  }

  private Object evalExpression(BoundExpression node) {
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

  private Object evalParentExpression(BoundParentExpression expr) {
    return evalExpression(expr.getExpression());
  }

  private Object evalLiteralExpression(BoundLiteralExpression expr) {
    return expr.getValue();
  }

  private Object evalVariableExpression(BoundVariableExpression expr) {
    return state.getValue(expr.getSymbol());
  }

  private Object evalAssignmentExpression(BoundAssignmentExpression expr) {
    var value = evalExpression(expr.getInitializer());
    state.setValue(expr.getSymbol(), value);
    return value;
  }

  private Object evalUnaryExpression(BoundUnaryExpression expr) {
    var operand = evalExpression(expr.getBoundOperand());
    switch (expr.getOperatorKind()) {
      case Identity:
        return operand;
      case Negation:
        return -(long) operand;
      case LogicalNot:
        return !(boolean) operand;
      default:
        throw new IllegalStateException(
            "unexpected unary operator kind: " + expr.getOperatorKind());
    }
  }

  private Object evalBinaryExpression(BoundBinaryExpression expr) {
    var left = evalExpression(expr.getLeft());
    var right = evalExpression(expr.getRight());
    switch (expr.getOperatorKind()) {
      case Addition:
        return (long) left + (long) right;
      case Subtraction:
        return (long) left - (long) right;
      case Multiplication:
        return (long) left * (long) right;
      case Division:
        return (long) left / (long) right;
      case Modulo:
        return (long) left % (long) right;
      case LongEquality:
      case BooleanEquality:
        return left.equals(right);
      case BooleanNonEquality:
      case LongNonEquality:
        return !left.equals(right);
      case RelationalLessThan:
        return (long) left < (long) right;
      case RelationalLessThanEquals:
        return (long) left <= (long) right;
      case RelationalGraterThan:
        return (long) left > (long) right;
      case RelationalGraterThanEquals:
        return (long) left >= (long) right;
      case LogicalAnd:
        return (boolean) left && (boolean) right;
      case LogicalOr:
        return (boolean) left || (boolean) right;
      default:
        throw new IllegalStateException(
            "unexpected binary operator kind: " + expr.getOperatorKind());
    }
  }
}
