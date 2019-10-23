package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundContinueStatement implements BoundStatement {

  private final SyntaxToken breakKeyword;

  public BoundContinueStatement(SyntaxToken breakKeyword) {
    this.breakKeyword = breakKeyword;
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.ContinueStatement;
  }
}
