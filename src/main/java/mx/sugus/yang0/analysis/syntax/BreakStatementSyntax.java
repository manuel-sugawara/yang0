package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

public class BreakStatementSyntax implements StatementSyntax {

  private final SyntaxToken breakKeyword;

  public BreakStatementSyntax(SyntaxToken breakKeyword) {
    this.breakKeyword = breakKeyword;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.BreakStatement;
  }

  @Override
  public TextSpan getSpan() {
    return breakKeyword.getSpan();
  }

  public SyntaxToken getBreakKeyword() {
    return breakKeyword;
  }
}
