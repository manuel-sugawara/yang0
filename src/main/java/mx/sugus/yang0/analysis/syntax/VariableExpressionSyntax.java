package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

public class VariableExpressionSyntax implements ExpressionSyntax {

  private final SyntaxToken identifier;

  public VariableExpressionSyntax(SyntaxToken identifier) {
    this.identifier = identifier;
  }

  public SyntaxToken getIdentifier() {
    return identifier;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.VariableExpression;
  }

  @Override
  public TextSpan getSpan() {
    return identifier.getSpan();
  }

  @Override
  public String toString() {
    return identifier.getSrc();
  }
}
