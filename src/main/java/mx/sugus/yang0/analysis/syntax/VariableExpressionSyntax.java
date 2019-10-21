package mx.sugus.yang0.analysis.syntax;

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
  public String toString() {
    return identifier.getSrc();
  }
}
