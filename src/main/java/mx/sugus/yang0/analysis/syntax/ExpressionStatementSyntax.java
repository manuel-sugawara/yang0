package mx.sugus.yang0.analysis.syntax;

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
  public String toString() {
    return expression.toString();
  }
}
