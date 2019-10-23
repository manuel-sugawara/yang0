package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxKind;
import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public enum BoundUnaryOperatorKind {
  Identity(SyntaxKind.PlusToken, Type.Long, Type.Long),
  Negation(SyntaxKind.MinusToken, Type.Long, Type.Long),
  LogicalNot(SyntaxKind.BangToken, Type.Boolean, Type.Boolean);

  private final SyntaxKind syntaxKind;
  private final Type type;
  private final Type returnType;

  BoundUnaryOperatorKind(SyntaxKind syntaxKind, Type type, Type returnType) {
    this.syntaxKind = syntaxKind;
    this.type = type;
    this.returnType = returnType;
  }

  public static BoundUnaryOperatorKind bindUnaryOperatorKind(SyntaxToken token, Type type) {
    for (var operatorKind : BoundUnaryOperatorKind.values()) {
      if (token.getKind() == operatorKind.getSyntaxKind() && type == operatorKind.getType()) {
        return operatorKind;
      }
    }
    return null;
  }

  public SyntaxKind getSyntaxKind() {
    return syntaxKind;
  }

  public Type getType() {
    return type;
  }

  public Type getReturnType() {
    return returnType;
  }
}
