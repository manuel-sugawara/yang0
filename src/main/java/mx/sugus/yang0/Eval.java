package mx.sugus.yang0;

import mx.sugus.yang0.analysis.Compilation;
import mx.sugus.yang0.analysis.binding.BoundAssignmentExpression;
import mx.sugus.yang0.analysis.binding.BoundBinaryExpression;
import mx.sugus.yang0.analysis.binding.BoundExpression;
import mx.sugus.yang0.analysis.binding.BoundGlobalScope;
import mx.sugus.yang0.analysis.binding.BoundLiteralExpression;
import mx.sugus.yang0.analysis.binding.BoundNodeKind;
import mx.sugus.yang0.analysis.binding.BoundParentExpression;
import mx.sugus.yang0.analysis.binding.BoundUnaryExpression;
import mx.sugus.yang0.analysis.binding.BoundVariableExpression;

public class Eval {

  public Eval() {}

  public static Object eval(Compilation compilation) {
    return eval(compilation.getScope(), compilation.getExpression());
  }

  public static Object eval(BoundGlobalScope scope, BoundExpression node) {
    var kind = node.getKind();

    if (kind == BoundNodeKind.LiteralExpression) {
      var expr = (BoundLiteralExpression) node;
      return expr.getValue();
    }

    if (kind == BoundNodeKind.AssignmentExpression) {
      var expr = (BoundAssignmentExpression) node;
      var value = eval(scope, expr.getInitializer());
      scope.setValue(expr.getSymbol(), value);
      return value;
    }

    if (kind == BoundNodeKind.VariableExpression) {
      var expr = (BoundVariableExpression) node;
      return scope.getValue(expr.getSymbol());
    }

    if (kind == BoundNodeKind.ParentExpression) {
      var expr = (BoundParentExpression) node;
      return eval(scope, expr.getExpression());
    }

    if (kind == BoundNodeKind.BinaryExpression) {
      var expr = (BoundBinaryExpression) node;
      var left = eval(scope, expr.getLeft());
      var right = eval(scope, expr.getRight());
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

    if (kind == BoundNodeKind.UnaryExpression) {
      var expr = (BoundUnaryExpression) node;
      var operand = eval(scope, expr.getBoundOperand());
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

    throw new IllegalStateException("unexpected node kind: " + kind);
  }
}
