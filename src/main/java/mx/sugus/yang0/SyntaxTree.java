package mx.sugus.yang0;

public class SyntaxTree {

  private final Diagnostics diagnostics;
  private final SyntaxNode root;
  private final Token eofToken;

  public SyntaxTree(Diagnostics diagnostics, SyntaxNode root, Token eofToken) {
    this.diagnostics = diagnostics;
    this.root = root;
    this.eofToken = eofToken;
  }

  public SyntaxNode getRoot() {
    return root;
  }

  public Token getEofToken() {
    return eofToken;
  }

  public Diagnostics getDiagnostics() {
    return diagnostics;
  }
}
