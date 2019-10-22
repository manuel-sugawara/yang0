package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

public class ExpressionStatementSyntax implements StatementSyntax {

  private final ExpressionSyntax expression;

  public ExpressionStatementSyntax(ExpressionSyntax expression) {
    this.expression = expression;
  }

  public ExpressionSyntax getExpression() {
    return expression;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.ExpressionStatement;
  }

  @Override
  public TextSpan getSpan() {
    return expression.getSpan();
  }

  @Override
  public String toString() {
    return expression.toString();
  }
}
