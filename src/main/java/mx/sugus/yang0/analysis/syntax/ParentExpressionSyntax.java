package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

public class ParentExpressionSyntax implements ExpressionSyntax {

  private final SyntaxToken start;
  private final ExpressionSyntax expression;
  private final SyntaxToken end;

  public ParentExpressionSyntax(SyntaxToken start, ExpressionSyntax expression, SyntaxToken end) {
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

  public ExpressionSyntax getExpression() {
    return expression;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.ParentExpression;
  }

  @Override
  public TextSpan getSpan() {
    return TextSpan.from(start.getSpan(), end.getSpan());
  }

  @Override
  public String toString() {
    return expression.toString();
  }
}
