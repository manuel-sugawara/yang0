package mx.sugus.yang0.analysis.text;

public class TextSpan {

  private final int position;
  private final int length;

  public TextSpan(int position, int length) {
    this.position = position;
    this.length = length;
  }

  public static TextSpan from(TextSpan start, TextSpan end) {
    var startPosition = start.getPosition();
    var endPosition = end.getEndPosition();
    return new TextSpan(startPosition, (endPosition - startPosition));
  }

  public int getPosition() {
    return position;
  }

  public int getLength() {
    return length;
  }

  public int getEndPosition() {
    return position + length;
  }
}
