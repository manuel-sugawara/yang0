package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

public class LiteralExpressionSyntax implements ExpressionSyntax {

  private final SyntaxToken value;

  public LiteralExpressionSyntax(SyntaxToken value) {
    this.value = value;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.LiteralExpression;
  }

  @Override
  public TextSpan getSpan() {
    return value.getSpan();
  }

  public SyntaxToken getValueToken() {
    return value;
  }

  public Object getValue() {
    return value.getValue();
  }

  @Override
  public String toString() {
    return value.getSrc();
  }
}
