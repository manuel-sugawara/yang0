package mx.sugus.yang0.analysis.syntax;

public class LiteralExpression implements Expression {

  private final SyntaxToken value;

  public LiteralExpression(SyntaxToken value) {
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
    return value.toString();
  }
}
