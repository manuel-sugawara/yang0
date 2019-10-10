package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.BinaryExpression;
import mx.sugus.yang0.analysis.syntax.Diagnostics;
import mx.sugus.yang0.analysis.syntax.Expression;
import mx.sugus.yang0.analysis.syntax.LiteralExpression;
import mx.sugus.yang0.analysis.syntax.ParentExpression;
import mx.sugus.yang0.analysis.syntax.SyntaxToken;
import mx.sugus.yang0.analysis.syntax.UnaryExpression;

public class Binder {

  private final Diagnostics diagnostics;

  public Binder(Diagnostics diagnostics) {
    this.diagnostics = diagnostics;
  }

  public Diagnostics getDiagnostics() {
    return diagnostics;
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
    var type = boundOperand.getType();
    var token = syntax.getOperator();
    var kind = bindUnaryOperatorKind(token, type);

    if (kind == null) {
      diagnostics.reportUnaryOperatorNotFound(token, type);
      return boundOperand;
    }
    return new BoundUnaryExpression(kind, boundOperand);
  }

  private BoundUnaryOperatorKind bindUnaryOperatorKind(SyntaxToken token, Class type) {
    for (var operatorKind : BoundUnaryOperatorKind.values()) {
      if (token.getKind() == operatorKind.getSyntaxKind() && type == operatorKind.getType()) {
        return operatorKind;
      }
    }
    return null;
  }

  private BoundExpression bindBinaryExpression(BinaryExpression syntax) {
    var boundLeft = bindExpression(syntax.getLeft());
    var boundRight = bindExpression(syntax.getRight());
    var token = syntax.getOperator();
    var kind = bindBinaryOperatorKind(token, boundLeft.getType(), boundRight.getType());

    if (kind == null) {
      diagnostics.reportBinaryOperatorNotFound(token, boundLeft.getType(), boundRight.getType());
      return boundLeft;
    }
    return new BoundBinaryExpression(boundLeft, kind, boundRight);
  }

  private BoundBinaryOperatorKind bindBinaryOperatorKind(
      SyntaxToken token, Class leftType, Class rightType) {
    for (var operatorKind : BoundBinaryOperatorKind.values()) {
      if (token.getKind() == operatorKind.getSyntaxKind()
          && leftType == operatorKind.getLeftType()
          && rightType == operatorKind.getRigthType()) {
        return operatorKind;
      }
    }
    return null;
  }
}
