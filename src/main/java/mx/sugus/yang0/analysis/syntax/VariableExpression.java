package mx.sugus.yang0.analysis.syntax;

public class VariableExpression implements Expression {

  private final SyntaxToken identifier;

  public VariableExpression(SyntaxToken identifier) {
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
