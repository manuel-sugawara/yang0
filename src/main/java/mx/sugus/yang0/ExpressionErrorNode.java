package mx.sugus.yang0;

public class ErrorNode implements SyntaxNode {

  private final String expecting;
  private final Token got;

  public ErrorNode(String expecting, Token got) {
    this.expecting = expecting;
    this.got = got;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.Error;
  }

  @Override
  public String toString() {
    return "Error, expecting: " + expecting + ", got: " + got.toString();
  }
}
