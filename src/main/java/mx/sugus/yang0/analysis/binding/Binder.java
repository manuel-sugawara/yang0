package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.BinaryExpression;
import mx.sugus.yang0.analysis.syntax.Diagnostics;
import mx.sugus.yang0.analysis.syntax.Expression;
import mx.sugus.yang0.analysis.syntax.LiteralExpression;
import mx.sugus.yang0.analysis.syntax.ParentExpression;
import mx.sugus.yang0.analysis.syntax.SyntaxKind;
import mx.sugus.yang0.analysis.syntax.UnaryExpression;

public class Binder {

  private final Diagnostics diagnostics;

  public Binder(Diagnostics diagnostics) {
    this.diagnostics = diagnostics;
  }

  public BoundExpression bindExpression(Expression syntax) {
    switch (syntax.getKind()) {
      case LiteralExpression:
        return bindLiteralExpression((LiteralExpression) syntax);
      case UnaryExpression:
        return bindUnaryExpression((UnaryExpression) syntax);
      case BinaryExpression:
        return bindBinaryExpression((BinaryExpression) syntax);
      case ParentExpression:
        return bindExpression(((ParentExpression) syntax).getExpression());
      default:
        throw new IllegalStateException("Unknown syntax kind: " + syntax.getKind());
    }
  }

  private BoundExpression bindLiteralExpression(LiteralExpression syntax) {
    return new BoundLiteralExpression(syntax.getValue());
  }

  private BoundExpression bindUnaryExpression(UnaryExpression syntax) {
    var boundOperand = bindExpression(syntax.getOperand());
    var kind = bindUnaryOperatorKind(syntax.getKind(), boundOperand.getType());
    if (kind == null) {
      diagnostics.addError(
          syntax.getOperator().getPosition(),
          "Unary operator '"
              + syntax.getOperator().getSrc()
              + "' is not defined for type: '"
              + boundOperand.getType()
              + "'");
      return boundOperand;
    }
    return new BoundUnaryExpression(kind, boundOperand);
  }

  private BoundUnaryOperatorKind bindUnaryOperatorKind(SyntaxKind kind, Class type) {
    if (type != Long.class) {
      return null;
    }
    switch (kind) {
      case MinusToken:
        return BoundUnaryOperatorKind.Negation;
      case PlusToken:
        return BoundUnaryOperatorKind.Identity;
      default:
        throw new IllegalStateException("Unknown unary syntax kind: " + kind);
    }
  }

  private BoundExpression bindBinaryExpression(BinaryExpression syntax) {
    var boundLeft = bindExpression(syntax.getLeft());
    var kind = bindBinaryOperatorKind(syntax.getOperator().getKind(), boundLeft.getType());
    if (kind == null) {
      diagnostics.addError(
          syntax.getOperator().getPosition(),
          "Binary operator '"
              + syntax.getOperator().getSrc()
              + "' is not defined for type: '"
              + boundLeft.getType()
              + "'");
    }
    var boundRight = bindExpression(syntax.getRight());
    return new BoundBinaryExpression(boundLeft, kind, boundRight);
  }

  private BoundBinaryOperatorKind bindBinaryOperatorKind(SyntaxKind kind, Class type) {
    if (type != Long.class) {
      return null;
    }
    switch (kind) {
      case MinusToken:
        return BoundBinaryOperatorKind.Substraction;
      case PlusToken:
        return BoundBinaryOperatorKind.Addition;
      case StartToken:
        return BoundBinaryOperatorKind.Multiplication;
      case SlashToken:
        return BoundBinaryOperatorKind.Division;
      default:
        throw new IllegalStateException("Unknown binary syntax kind: " + kind);
    }
  }
}
