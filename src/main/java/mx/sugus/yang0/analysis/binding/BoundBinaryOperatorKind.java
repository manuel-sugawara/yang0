package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxKind;
import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public enum BoundBinaryOperatorKind {
  Addition(SyntaxKind.PlusToken, Type.Long, Type.Long, Type.Long),
  Subtraction(SyntaxKind.MinusToken, Type.Long, Type.Long, Type.Long),
  Multiplication(SyntaxKind.StartToken, Type.Long, Type.Long, Type.Long),
  Division(SyntaxKind.SlashToken, Type.Long, Type.Long, Type.Long),
  Modulo(SyntaxKind.PercentToken, Type.Long, Type.Long, Type.Long),

  StringConcatenation(SyntaxKind.PlusToken, Type.String, Type.String, Type.String),
  StringLongConcatenation(SyntaxKind.PlusToken, Type.String, Type.Long, Type.String),
  StringBoolConcatenation(SyntaxKind.PlusToken, Type.String, Type.Boolean, Type.String),

  LongEquality(SyntaxKind.EqualsEqualsToken, Type.Long, Type.Long, Type.Boolean),
  LongNonEquality(SyntaxKind.BangEqualsToken, Type.Long, Type.Long, Type.Boolean),

  BooleanEquality(SyntaxKind.EqualsEqualsToken, Type.Boolean, Type.Boolean, Type.Boolean),
  BooleanNonEquality(SyntaxKind.BangEqualsToken, Type.Boolean, Type.Boolean, Type.Boolean),

  RelationalLessThan(SyntaxKind.LessThanToken, Type.Long, Type.Long, Type.Boolean),
  RelationalLessThanEquals(SyntaxKind.LessThanEqualsToken, Type.Long, Type.Long, Type.Boolean),
  RelationalGraterThan(SyntaxKind.GraterThanToken, Type.Long, Type.Long, Type.Boolean),
  RelationalGraterThanEquals(SyntaxKind.GraterThanEqualsToken, Type.Long, Type.Long, Type.Boolean),
  LogicalAnd(SyntaxKind.AmpersandAmpersandToken, Type.Boolean, Type.Boolean, Type.Boolean),
  LogicalOr(SyntaxKind.PipePipeToken, Type.Boolean, Type.Boolean, Type.Boolean);

  private final SyntaxKind syntaxKind;
  private final Type leftType;
  private final Type rightType;
  private final Type returnType;

  BoundBinaryOperatorKind(SyntaxKind syntaxKind, Type leftType, Type rightType, Type returnType) {
    this.syntaxKind = syntaxKind;
    this.leftType = leftType;
    this.rightType = rightType;
    this.returnType = returnType;
  }

  public static BoundBinaryOperatorKind bindBinaryOperatorKind(
      SyntaxToken token, Type leftType, Type rightType) {
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

  public Type getLeftType() {
    return leftType;
  }

  public Type getRightType() {
    return rightType;
  }

  public Type getReturnType() {
    return returnType;
  }
}
