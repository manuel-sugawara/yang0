package mx.sugus.yang0;

import mx.sugus.yang0.analysis.Compilation;
import mx.sugus.yang0.analysis.binding.BoundBinaryExpression;
import mx.sugus.yang0.analysis.binding.BoundExpression;
import mx.sugus.yang0.analysis.binding.BoundLiteralExpression;
import mx.sugus.yang0.analysis.binding.BoundNodeKind;
import mx.sugus.yang0.analysis.binding.BoundUnaryExpression;

public class Eval {

  public Eval() {}

  public static Object eval(Compilation compilation) {
    return eval(compilation.getExpression());
  }

  public static Object eval(BoundExpression node) {
    var kind = node.getKind();

    if (kind == BoundNodeKind.LiteralExpression) {
      var expr = (BoundLiteralExpression) node;
      return expr.getValue();
    }

    if (kind == BoundNodeKind.BinaryExpression) {
      var expr = (BoundBinaryExpression) node;
      var left = (long) eval(expr.getLeft());
      var right = (long) eval(expr.getRight());
      switch (expr.getOperatorKind()) {
        case Addition:
          return left + right;
        case Subtraction:
          return left - right;
        case Multiplication:
          return left * right;
        case Division:
          return left / right;
        default:
          throw new IllegalStateException("unexpected binary operator kind: " + expr.getOperatorKind());
      }
    }

    if (kind == BoundNodeKind.UnaryExpression) {
      var expr = (BoundUnaryExpression) node;
      var operand = (long) eval(expr.getOperand());
      switch (expr.getOperatorKind()) {
        case Identity:
          return operand;
        case Negation:
          return -operand;
        default:
          throw new IllegalStateException("unexpected unary operator kind: " + expr.getOperatorKind());
      }
    }

    throw new IllegalStateException("unexpected node kind: " + kind);
  }
}
