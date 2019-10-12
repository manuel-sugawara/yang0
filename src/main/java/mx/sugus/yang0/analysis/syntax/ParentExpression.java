package mx.sugus.yang0.analysis.syntax;

public class ParentExpression implements Expression {

  private final SyntaxToken start;
  private final Expression expression;
  private final SyntaxToken end;

  public ParentExpression(SyntaxToken start, Expression expression, SyntaxToken end) {
    this.start = start;
    this.expression = expression;
    this.end = end;
  }

  public SyntaxToken getStart() {
    return start;
  }

  public SyntaxToken getEnd() {
    return end;
  }

  public Expression getExpression() {
    return expression;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.ParentExpression;
  }

  @Override
  public String toString() {
    return expression.toString();
  }
}
