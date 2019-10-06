package mx.sugus.yang0.analysis.syntax;

public class Lexer {

  private final String src;
  private final Diagnostics diagnostics;
  private int position;

  public Lexer(String src, Diagnostics diagnostics) {
    this.src = src;
    this.diagnostics = diagnostics;
  }

  public SyntaxToken next() {
    var ch = peek();
    if (ch == 0) {
      return new SyntaxToken(SyntaxKind.EofToken, position, "");
    }

    var start = position;
    if (Character.isWhitespace(ch)) {
      var buf = new StringBuilder(ch);
      ++position;
      while (Character.isWhitespace(ch = peek())) {
        buf.append(ch);
        ++position;
      }
      var value = src.substring(start, position);
      return new SyntaxToken(SyntaxKind.WhitespaceToken, start, value, value);
    }

    if (Character.isDigit(ch)) {
      var buf = new StringBuilder(ch);
      ++position;
      while (Character.isDigit(ch = peek())) {
        buf.append(ch);
        ++position;
      }
      var text = src.substring(start, position);
      try {
        var value = Long.parseLong(text);
        return new SyntaxToken(SyntaxKind.LongToken, start, text, value);
      } catch (NumberFormatException e) {
        diagnostics.addError(start, "invalid long literal: \"" + text + "\": " + e.getMessage());
        return new SyntaxToken(SyntaxKind.ErrorToken, start, text);
      }
    }

    switch (ch) {
      case '+':
        ++position;
        return new SyntaxToken(SyntaxKind.PlusToken, start, "+");
      case '-':
        ++position;
        return new SyntaxToken(SyntaxKind.MinusToken, start, "-");
      case '*':
        ++position;
        return new SyntaxToken(SyntaxKind.StartToken, start, "*");
      case '/':
        ++position;
        return new SyntaxToken(SyntaxKind.SlashToken, start, "/");
      case '(':
        ++position;
        return new SyntaxToken(SyntaxKind.OpenParenToken, start, "(");
      case ')':
        ++position;
        return new SyntaxToken(SyntaxKind.CloseParenToken, start, ")");
    }

    var text = src.substring(start, ++position);
    return new SyntaxToken(SyntaxKind.ErrorToken, start, text);
  }

  private char peek() {
    if (position >= src.length()) {
      return 0;
    }
    return src.charAt(position);
  }
}
