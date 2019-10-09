package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxKind;

public enum BoundBinaryOperatorKind {
  Addition(SyntaxKind.PlusToken, Long.class, Long.class, Long.class),
  Subtraction(SyntaxKind.MinusToken, Long.class, Long.class, Long.class),
  Multiplication(SyntaxKind.StartToken, Long.class, Long.class, Long.class),
  Division(SyntaxKind.SlashToken, Long.class, Long.class, Long.class),
  LogicalAnd(SyntaxKind.AmpersandAmpersandToken, Boolean.class, Boolean.class, Boolean.class),
  LogicalOr(SyntaxKind.PipePipeToken, Boolean.class, Boolean.class, Boolean.class);

  private final SyntaxKind syntaxKind;
  private final Class leftType;
  private final Class rigthType;
  private final Class returnType;

  BoundBinaryOperatorKind(SyntaxKind syntaxKind, Class leftType, Class rigthType,
      Class returnType) {
    this.syntaxKind = syntaxKind;
    this.leftType = leftType;
    this.rigthType = rigthType;
    this.returnType = returnType;
  }

  public SyntaxKind getSyntaxKind() {
    return syntaxKind;
  }

  public Class getLeftType() {
    return leftType;
  }

  public Class getRigthType() {
    return rigthType;
  }

  public Class getReturnType() {
    return returnType;
  }
}
