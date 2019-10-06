package mx.sugus.yang0.analysis.syntax;

public class SyntaxTree {

  private final Diagnostics diagnostics;
  private final SyntaxNode root;
  private final SyntaxToken eofToken;

  public SyntaxTree(Diagnostics diagnostics, SyntaxNode root, SyntaxToken eofToken) {
    this.diagnostics = diagnostics;
    this.root = root;
    this.eofToken = eofToken;
  }

  public SyntaxNode getRoot() {
    return root;
  }

  public SyntaxToken getEofToken() {
    return eofToken;
  }

  public Diagnostics getDiagnostics() {
    return diagnostics;
  }
}
