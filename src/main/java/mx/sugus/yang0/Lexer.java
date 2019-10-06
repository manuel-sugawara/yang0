package mx.sugus.yang0;

public class Lexer {

  private final String src;
  private final Diagnostics diagnostics;
  private int position;

  public Lexer(String src, Diagnostics diagnostics) {
    this.src = src;
    this.diagnostics = diagnostics;
  }

  public Token next() {
    var ch = peek();
    if (ch == 0) {
      return new Token(SyntaxKind.EofToken, position, "");
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
      return new Token(SyntaxKind.WhitespaceToken, start, value, value);
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
        return new Token(SyntaxKind.LongToken, start, text, value);
      } catch (NumberFormatException e) {
        diagnostics.addError(start, "invalid long literal: \"" + text + "\": " + e.getMessage());
        return new Token(SyntaxKind.ErrorToken, start, text);
      }
    }

    switch (ch) {
      case '+':
        ++position;
        return new Token(SyntaxKind.PlusToken, start, "+");
      case '-':
        ++position;
        return new Token(SyntaxKind.MinusToken, start, "-");
      case '*':
        ++position;
        return new Token(SyntaxKind.StartToken, start, "*");
      case '/':
        ++position;
        return new Token(SyntaxKind.SlashToken, start, "/");
      case '(':
        ++position;
        return new Token(SyntaxKind.OpenParenToken, start, "(");
      case ')':
        ++position;
        return new Token(SyntaxKind.CloseParenToken, start, ")");
    }

    var text = src.substring(start, ++position);
    return new Token(SyntaxKind.ErrorToken, start, text);
  }

  private char peek() {
    if (position >= src.length()) {
      return 0;
    }
    return src.charAt(position);
  }
}
