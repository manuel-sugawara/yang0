package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundBreakStatement implements BoundStatement {

  private final SyntaxToken breakKeyword;

  public BoundBreakStatement(SyntaxToken breakKeyword) {
    this.breakKeyword = breakKeyword;
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.BreakStatement;
  }
}
