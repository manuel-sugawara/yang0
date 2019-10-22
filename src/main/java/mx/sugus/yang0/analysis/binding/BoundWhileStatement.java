package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundWhileStatement implements BoundStatement {
  private final SyntaxToken whileToken;
  private final BoundExpression condition;
  private final BoundStatement body;

  public BoundWhileStatement(
      SyntaxToken whileToken, BoundExpression condition, BoundStatement body) {
    this.whileToken = whileToken;
    this.condition = condition;
    this.body = body;
  }

  public SyntaxToken getWhileToken() {
    return whileToken;
  }

  public BoundExpression getCondition() {
    return condition;
  }

  public BoundStatement getBody() {
    return body;
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.WhileStatement;
  }
}
