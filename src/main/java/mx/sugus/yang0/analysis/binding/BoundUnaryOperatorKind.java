package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxKind;
import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public enum BoundUnaryOperatorKind {
  Identity(SyntaxKind.PlusToken, Long.class, Long.class),
  Negation(SyntaxKind.MinusToken, Long.class, Long.class),
  LogicalNot(SyntaxKind.BangToken, Boolean.class, Boolean.class);

  private final SyntaxKind syntaxKind;
  private final Class type;
  private final Class returnType;

  BoundUnaryOperatorKind(SyntaxKind syntaxKind, Class type, Class returnType) {
    this.syntaxKind = syntaxKind;
    this.type = type;
    this.returnType = returnType;
  }

  public static BoundUnaryOperatorKind bindUnaryOperatorKind(SyntaxToken token, Class type) {
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

  public Class getType() {
    return type;
  }

  public Class getReturnType() {
    return returnType;
  }
}
