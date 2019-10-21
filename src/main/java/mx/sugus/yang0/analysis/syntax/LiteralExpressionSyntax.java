package mx.sugus.yang0.analysis.syntax;

public class LiteralExpressionSyntax implements ExpressionSyntax {

  private final SyntaxToken value;

  public LiteralExpressionSyntax(SyntaxToken value) {
    this.value = value;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.LiteralExpression;
  }

  public Object getValue() {
    return value.getValue();
  }

  @Override
  public String toString() {
    return value.getSrc();
  }
}
