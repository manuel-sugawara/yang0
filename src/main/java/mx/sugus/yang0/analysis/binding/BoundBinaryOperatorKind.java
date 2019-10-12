package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxKind;
import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public enum BoundBinaryOperatorKind {
  Addition(SyntaxKind.PlusToken, Long.class, Long.class, Long.class),
  Subtraction(SyntaxKind.MinusToken, Long.class, Long.class, Long.class),
  Multiplication(SyntaxKind.StartToken, Long.class, Long.class, Long.class),
  Division(SyntaxKind.SlashToken, Long.class, Long.class, Long.class),
  LogicalAnd(SyntaxKind.AmpersandAmpersandToken, Boolean.class, Boolean.class, Boolean.class),
  LogicalOr(SyntaxKind.PipePipeToken, Boolean.class, Boolean.class, Boolean.class);

  private final SyntaxKind syntaxKind;
  private final Class leftType;
  private final Class rightType;
  private final Class returnType;

  BoundBinaryOperatorKind(
      SyntaxKind syntaxKind, Class leftType, Class rightType, Class returnType) {
    this.syntaxKind = syntaxKind;
    this.leftType = leftType;
    this.rightType = rightType;
    this.returnType = returnType;
  }

  public static BoundBinaryOperatorKind bindBinaryOperatorKind(
      SyntaxToken token, Class leftType, Class rightType) {
    for (var operatorKind : BoundBinaryOperatorKind.values()) {
      if (token.getKind() == operatorKind.getSyntaxKind()
          && leftType == operatorKind.getLeftType()
          && rightType == operatorKind.getRightType()) {
        return operatorKind;
      }
    }
    return null;
  }

  public SyntaxKind getSyntaxKind() {
    return syntaxKind;
  }

  public Class getLeftType() {
    return leftType;
  }

  public Class getRightType() {
    return rightType;
  }

  public Class getReturnType() {
    return returnType;
  }
}
