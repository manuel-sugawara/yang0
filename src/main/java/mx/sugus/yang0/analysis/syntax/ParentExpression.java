package mx.sugus.yang0.analysis.syntax;

public class ParentExpression implements Expression {

  private final SyntaxToken start;
  private final Expression expr;
  private final SyntaxToken end;

  public ParentExpression(SyntaxToken start, Expression expr, SyntaxToken end) {
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
