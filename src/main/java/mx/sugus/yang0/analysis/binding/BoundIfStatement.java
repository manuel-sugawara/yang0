package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundIfStatement implements BoundStatement {

  private final SyntaxToken ifKeyword;
  private final BoundExpression condition;
  private final BoundStatement body;
  private final SyntaxToken elseKeyword;
  private final BoundStatement elseBody;

  public BoundIfStatement(
      SyntaxToken ifKeyword,
      BoundExpression condition,
      BoundStatement body,
      SyntaxToken elseKeyword,
      BoundStatement elseBody) {
    this.ifKeyword = ifKeyword;
    this.condition = condition;
    this.body = body;
    this.elseKeyword = elseKeyword;
    this.elseBody = elseBody;
  }

  public BoundIfStatement(SyntaxToken ifKeyword, BoundExpression condition, BoundStatement body) {
    this.ifKeyword = ifKeyword;
    this.condition = condition;
    this.body = body;
    this.elseKeyword = null;
    this.elseBody = null;
  }

  public SyntaxToken getIfKeyword() {
    return ifKeyword;
  }

  public BoundExpression getCondition() {
    return condition;
  }

  public BoundStatement getBody() {
    return body;
  }

  public SyntaxToken getElseKeyword() {
    return elseKeyword;
  }

  public BoundStatement getElseBody() {
    return elseBody;
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.IfStatement;
  }
}
