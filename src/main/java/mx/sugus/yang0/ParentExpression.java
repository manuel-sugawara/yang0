package mx.sugus.yang0;

public class ParentExpression implements Expression {

  private final Token start;
  private final Expression expr;
  private final Token end;

  public ParentExpression(Token start, Expression expr, Token end) {
    this.start = start;
    this.expr = expr;
    this.end = end;
  }

  public Expression getExpression() {
    return expr;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.ParentExpression;
  }
}
