package mx.sugus.yang0;

public class Lexer {

  private final String src;
  private int position;

  public Lexer(String src) {
    this.src = src;
  }


  public Token next() {
    char ch = peek();
    if (ch == 0) {
      return new Token(SyntaxKind.Eof, position, "", null);
    }

    int start = position;

    if (Character.isWhitespace(ch)) {
      StringBuilder buf = new StringBuilder(ch);
      position++;
      while (Character.isWhitespace(ch = peek())) {
        buf.append(ch);
        ++position;
      }
      String value = src.substring(start, position);
      return new Token(SyntaxKind.Whitespace, start, value, value);
    }

    if (Character.isDigit(ch)) {
      StringBuilder buf = new StringBuilder(ch);
      position++;
      while (Character.isDigit(ch = peek())) {
        buf.append(ch);
        ++position;
      }
      String text = src.substring(start, position);
      Long value = Long.parseLong(text);
      return new Token(SyntaxKind.LongLiteral, start, text, value);
    }

    switch(ch) {
      case '+':
        position++;
        return new Token(SyntaxKind.PlusToken, start, "+");
      case '-':
        position++;
        return new Token(SyntaxKind.MinusToken, start, "-");
      case '*':
        position++;
        return new Token(SyntaxKind.StartToken, start, "*");
      case '/':
        position++;
        return new Token(SyntaxKind.SlashToken, start, "/");
      case '(':
        position++;
        return new Token(SyntaxKind.OpenParen, start, "(");
      case ')':
        position++;
        return new Token(SyntaxKind.CloseParen, start, ")");
    }

    String text = src.substring(start, ++position);
    return new Token(SyntaxKind.Error, start, text);
  }

  private boolean hasNext() {
    return position + 1 < src.length();
  }

  private char peek() {
    if (position >= src.length()) {
      return 0;
    }
    return src.charAt(position);
  }

}
