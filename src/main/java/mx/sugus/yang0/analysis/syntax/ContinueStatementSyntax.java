package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

public class ContinueStatementSyntax implements StatementSyntax {

  private final SyntaxToken continueKeyword;

  public ContinueStatementSyntax(SyntaxToken continueKeyword) {
    this.continueKeyword = continueKeyword;
  }

  public SyntaxToken getContinueKeyword() {
    return continueKeyword;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.ContinueStatement;
  }

  @Override
  public TextSpan getSpan() {
    return continueKeyword.getSpan();
  }
}
