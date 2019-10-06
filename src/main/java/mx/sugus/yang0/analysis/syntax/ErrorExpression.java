package mx.sugus.yang0.analysis.syntax;

public class ErrorExpression implements Expression {

  private final String expecting;
  private final SyntaxToken got;

  public ErrorExpression(String expecting, SyntaxToken got) {
    this.expecting = expecting;
    this.got = got;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.ErrorToken;
  }

  @Override
  public String toString() {
    return "Error, expecting: " + expecting + ", got: " + got.toString();
  }
}
