package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.syntax.Diagnostics.Level;
import mx.sugus.yang0.analysis.text.TextSource;
import mx.sugus.yang0.analysis.text.TextSpan;

public class Diagnostic {
  private final Level level;
  private final TextSource source;
  private final TextSpan span;
  private final String message;
  private final Object[] args;

  public Diagnostic(
      Level level, TextSource source, TextSpan span, String message, Object[] args) {
    this.source = source;
    this.level = level;
    this.span = span;
    this.message = message;
    this.args = args;
  }

  public Level getLevel() {
    return level;
  }

  public int getLineNo() {
    var lineAndColumn = source.getLineAndColumn(span.getPosition());
    return lineAndColumn[0] + 1;
  }

  public int getColumnNo() {
    var lineAndColumn = source.getLineAndColumn(span.getPosition());
    return lineAndColumn[1] + 1;
  }

  public String getLine() {
    var lineAndColumn = source.getLineAndColumn(span.getPosition());
    return source.getLine(lineAndColumn[0]);
  }

  @Override
  public String toString() {
    var lineAndColumn = source.getLineAndColumn(span.getPosition());
    var buf = new StringBuilder();
    buf.append(source.getName())
        .append(":[")
        .append(lineAndColumn[0] + 1)
        .append(',')
        .append(lineAndColumn[1] + 1)
        .append("] ");

    var prefixLen = buf.length() - 1;

    buf.append(getLine()).append('\n');
    for (var x = 0; x < prefixLen; x++) {
      buf.append('·');
    }
    for (var x = prefixLen; x <= (prefixLen + lineAndColumn[1]); x++) {
      buf.append(' ');
    }
    for (var x = 0; x < span.getLength(); x++) {
      buf.append('^');
    }
    buf.append('\n');
    for (var x = 0; x < prefixLen; x++) {
      buf.append('·');
    }
    buf.append(' ');
    buf.append(String.format(message, args));
    return buf.toString();
  }
}
