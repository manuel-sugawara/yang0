package mx.sugus.yang0;

import mx.sugus.yang0.analysis.Compilation;
import mx.sugus.yang0.analysis.binding.BoundAssignmentExpression;
import mx.sugus.yang0.analysis.binding.BoundBinaryExpression;
import mx.sugus.yang0.analysis.binding.BoundExpression;
import mx.sugus.yang0.analysis.binding.BoundLiteralExpression;
import mx.sugus.yang0.analysis.binding.BoundParentExpression;
import mx.sugus.yang0.analysis.binding.BoundUnaryExpression;
import mx.sugus.yang0.analysis.binding.BoundVariableExpression;

public class Eval {

  private final Compilation compilation;

  public Eval(Compilation compilation) {
    this.compilation = compilation;
  }

  public Object eval() {
    return evalExpression(compilation.getExpression());
  }

  public Object evalExpression(BoundExpression node) {
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
    var scope = compilation.getScope();
    return scope.getValue(expr.getSymbol());
  }

  private Object evalAssignmentExpression(BoundAssignmentExpression expr) {
    var value = evalExpression(expr.getInitializer());
    compilation.getScope().setValue(expr.getSymbol(), value);
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
