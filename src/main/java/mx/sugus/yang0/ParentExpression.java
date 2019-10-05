package mx.sugus.yang0;

public class ParentExpression implements Expression {

  private final Expression expr;

  public ParentExpression(Expression expr) {
    this.expr = expr;
  }

  public Expression getExpression() {
    return expr;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.ParentExpression;
  }
}
