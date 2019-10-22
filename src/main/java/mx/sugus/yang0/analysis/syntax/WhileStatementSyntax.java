package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

public class WhileStatementSyntax implements StatementSyntax {

  private final SyntaxToken whileToken;
  private final ExpressionSyntax condition;
  private final StatementSyntax body;

  public WhileStatementSyntax(SyntaxToken whileToken,
      ExpressionSyntax condition, StatementSyntax body) {
    this.whileToken = whileToken;
    this.condition = condition;
    this.body = body;
  }

  public SyntaxToken getWhileToken() {
    return whileToken;
  }

  public ExpressionSyntax getCondition() {
    return condition;
  }

  public StatementSyntax getBody() {
    return body;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.WhileStatement;
  }

  @Override
  public TextSpan getSpan() {
    return TextSpan.from(whileToken.getSpan(), body.getSpan());
  }
}
