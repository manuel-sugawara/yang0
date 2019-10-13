package mx.sugus.yang0.analysis.text;

import java.util.ArrayList;

public class TextSource {
  private final String name;
  private final String text;
  private final LineBoundary[] boundaries;

  public TextSource(String text) {
    this("<user input>", text);
  }

  public TextSource(String name, String text) {
    this.name = name;
    this.text = text;
    this.boundaries = computeLineBoundaries();
  }

  public String getName() {
    return name;
  }

  public String getText() {
    return text;
  }

  public String getLine(int line) {
    var boundary = boundaries[line];
    return substring(boundary.start, boundary.start + boundary.length);
  }

  public String substring(int start, int end) {
    return text.substring(start, end);
  }

  public char charAt(int index) {
    if (index >= text.length()) {
      return '\0';
    }
    return text.charAt(index);
  }

  public int length() {
    return text.length();
  }

  public LineBoundary[] getBoundaries() {
    return boundaries;
  }

  public int[] getLineAndColumn(int position) {

    var low = 0;
    var high = boundaries.length - 1;

    while (low < high) {
      var mid = low + (high - low) / 2;
      var boundary = boundaries[mid];
      if (boundary.isPositionInside(position)) {
        low = mid;
        break;
      }
      if (position < boundary.start) {
        high = mid - 1;
      } else {
        low = mid + 1;
      }
    }
    if (boundaries[high].isPositionInside(position)) {
      low = high;
    }
    return new int[] {low, position - (boundaries[low].start)};
  }

  private LineBoundary[] computeLineBoundaries() {
    var position = 0;
    var start = 0;
    var length = text.length();
    var boundaries = new ArrayList<LineBoundary>();
    while (position < length) {
      var lineBreakLength = lineBreakLength(position);
      if (lineBreakLength == 0) {
        position++;
      } else {
        var boundary = new LineBoundary(start, position - start, lineBreakLength);
        boundaries.add(boundary);
        position += lineBreakLength;
        start = position;
      }
    }
    var boundary = new LineBoundary(start, position - start, 0);
    boundaries.add(boundary);
    return boundaries.toArray(new LineBoundary[0]);
  }

  private int lineBreakLength(int position) {
    if (charAt(position) == '\r' && charAt(position + 1) == '\n') {
      return 2;
    }

    if (charAt(position) == '\r' || charAt(position) == '\n') {
      return 1;
    }

    return 0;
  }

  class LineBoundary {
    final int start;
    final int length;
    final int lineBreakLength;

    public LineBoundary(int start, int length, int lineBreakLength) {
      this.start = start;
      this.length = length;
      this.lineBreakLength = lineBreakLength;
    }

    public boolean isPositionInside(int position) {
      return start <= position && position <= (start + length + lineBreakLength);
    }

    @Override
    public String toString() {
      return "LineBoundary{"
          + "start="
          + start
          + ", length="
          + length
          + ", lineBreakLength="
          + lineBreakLength
          + ", source='"
          + TextSource.this.substring(start, start + length)
          + "'"
          + '}';
    }
  }
}
