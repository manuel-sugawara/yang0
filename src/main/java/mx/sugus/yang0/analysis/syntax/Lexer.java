package mx.sugus.yang0.analysis.syntax;

import java.util.function.Predicate;
import mx.sugus.yang0.analysis.text.TextSource;

public class Lexer {

  private final TextSource source;
  private final Diagnostics diagnostics;
  private int position;


  public Lexer(TextSource source, Diagnostics diagnostics) {
    this.source = source;
    this.diagnostics = diagnostics;
  }

  public SyntaxToken next() {
    var ch = peek();
    if (Character.isWhitespace(ch)) {
      return whitespace();
    }

    if (Character.isDigit(ch)) {
      return integer();
    }

    if (Character.isJavaIdentifierStart(ch)) {
      return identifier();
    }

    var start = position;
    switch (peekAndMove()) {
      case 0:
        return new SyntaxToken(SyntaxKind.EofToken, position, "");
      case '+':
        return new SyntaxToken(SyntaxKind.PlusToken, start, "+");
      case '-':
        return new SyntaxToken(SyntaxKind.MinusToken, start, "-");
      case '*':
        return new SyntaxToken(SyntaxKind.StartToken, start, "*");
      case '/':
        return new SyntaxToken(SyntaxKind.SlashToken, start, "/");
      case '%':
        return new SyntaxToken(SyntaxKind.PercentToken, start, "%");
      case '!':
        if (matchAndMove('=')) {
          return new SyntaxToken(SyntaxKind.BangEqualsToken, start, "!=");
        } else {
          return new SyntaxToken(SyntaxKind.BangToken, start, "!");
        }
      case '=':
        if (matchAndMove('=')) {
          return new SyntaxToken(SyntaxKind.EqualsEqualsToken, start, "==");
        } else {
          break;
        }
      case '<':
        if (matchAndMove('=')) {
          return new SyntaxToken(SyntaxKind.LessThanEqualsToken, start, "<=");
        } else {
          return new SyntaxToken(SyntaxKind.LessThanToken, start, "<");
        }
      case '>':
        if (matchAndMove('=')) {
          return new SyntaxToken(SyntaxKind.GraterThanEqualsToken, start, ">=");
        } else {
          return new SyntaxToken(SyntaxKind.GraterThanToken, start, ">");
        }
      case '(':
        return new SyntaxToken(SyntaxKind.OpenParenToken, start, "(");
      case ')':
        return new SyntaxToken(SyntaxKind.CloseParenToken, start, ")");
      case '&':
        if (matchAndMove('&')) {
          return new SyntaxToken(SyntaxKind.AmpersandAmpersandToken, start, "&&");
        } else {
          break;
        }
      case '|':
        if (matchAndMove('|')) {
          return new SyntaxToken(SyntaxKind.PipePipeToken, start, "||");
        } else {
          break;
        }
    }

    diagnostics.reportUnexpectedCharacter(start, source.charAt(start));
    var text = source.substring(start, position);
    return new SyntaxToken(SyntaxKind.ErrorToken, start, text);
  }

  private SyntaxToken whitespace() {
    var start = position;
    var text = consumeWhile(Character::isWhitespace);
    return new SyntaxToken(SyntaxKind.WhitespaceToken, start, text, text);
  }

  private SyntaxToken integer() {
    var start = position;
    var text = consumeWhile(Character::isDigit);
    try {
      var value = Long.parseLong(text);
      return new SyntaxToken(SyntaxKind.LongToken, start, text, value);
    } catch (NumberFormatException e) {
      diagnostics.reportInvalidNumber(start, text, Long.class);
      return new SyntaxToken(SyntaxKind.ErrorToken, start, text);
    }
  }

  private SyntaxToken identifier() {
    var start = position;
    var text = consumeWhile(Character::isJavaIdentifierPart);
    switch (text) {
      case "true":
        return new SyntaxToken(SyntaxKind.TrueKeyword, start, text, Boolean.TRUE);
      case "false":
        return new SyntaxToken(SyntaxKind.FalseKeyword, start, text, Boolean.FALSE);
      default:
        return new SyntaxToken(SyntaxKind.Identifier, start, text);
    }
  }

  private String consumeWhile(Predicate<Character> predicate) {
    var buf = new StringBuilder();
    while (true) {
      var ch = peek();
      if (ch != 0 && predicate.test(ch)) {
        position++;
        buf.append(ch);
      } else {
        break;
      }
    }
    return buf.toString();
  }

  private char peekAndMove() {
    var ch = peek();
    if (ch != 0) {
      position++;
    }
    return ch;
  }

  private boolean matchAndMove(char expected) {
    var ch = peek();
    if (ch == expected) {
      position++;
      return true;
    }
    return false;
  }

  private char peek() {
    return peek(0);
  }

  private char peek(int offset) {
    var newPosition = (position + offset);
    if (newPosition >= source.length()) {
      return 0;
    }
    return source.charAt(newPosition);
  }
}
