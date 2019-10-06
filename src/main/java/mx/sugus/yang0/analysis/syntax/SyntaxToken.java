package mx.sugus.yang0.analysis.syntax;

public class SyntaxToken {

  private final SyntaxKind kind;
  private final int position;
  private final String src;
  private final Object value;

  public SyntaxToken(SyntaxKind kind, int position, String src, Object value) {
    this.kind = kind;
    this.position = position;
    this.src = src;
    this.value = value;
  }

  public SyntaxToken(SyntaxKind kind, int position, String src) {
    this.kind = kind;
    this.position = position;
    this.src = src;
    this.value = null;
  }

  public String getSrc() {
    return src;
  }

  public SyntaxKind getKind() {
    return kind;
  }

  public Object getValue() {
    return value;
  }

  public int getPosition() {
    return position;
  }

  public String toString() {
    return String.format("%s:%s [%s]", kind, position, src);
  }
}
