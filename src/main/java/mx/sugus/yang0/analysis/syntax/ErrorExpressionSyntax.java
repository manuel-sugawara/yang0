package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

public class ErrorExpressionSyntax implements ExpressionSyntax {

  private final String expecting;
  private final SyntaxToken got;

  public ErrorExpressionSyntax(String expecting, SyntaxToken got) {
    this.expecting = expecting;
    this.got = got;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.ErrorToken;
  }

  @Override
  public TextSpan getSpan() {
    return got.getSpan();
  }

  @Override
  public String toString() {
    return "Error, expecting: " + expecting + ", got: " + got.toString();
  }
}
