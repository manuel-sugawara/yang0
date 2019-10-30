package mx.sugus.yang0.analysis.syntax;

import java.util.function.Predicate;
import mx.sugus.yang0.analysis.text.TextSource;
import mx.sugus.yang0.analysis.text.TextSpan;

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
      case ',':
        return new SyntaxToken(SyntaxKind.CommaToken, start, ",");
      case '"':
        return string();
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
          return new SyntaxToken(SyntaxKind.EqualsToken, start, "=");
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
      case '{':
        return new SyntaxToken(SyntaxKind.OpenBracketToken, start, "(");

      case '}':
        return new SyntaxToken(SyntaxKind.CloseBracketToken, start, "(");
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

    var text = source.substring(start, position);
    var errorToken = new SyntaxToken(SyntaxKind.ErrorToken, start, text);
    diagnostics.reportUnexpectedCharacter(errorToken.getSpan(), text);
    return errorToken;
  }

  private SyntaxToken string() {
    var start = position - 1;
    var done = false;
    var buf = new StringBuilder();
    while (!done) {
      var ch = peekAndMove();
      switch (ch) {
        case 0:
          diagnostics.reportUnterminatedStringLiteral(new TextSpan(start, buf.length()));
          done = true;
          break;
        case '"':
          done = true;
          break;
        case '\\':
          buf.append(scanEscapeSequence());
          break;
        default:
          buf.append(ch);
          break;
      }
    }
    var text = source.substring(start, position);
    return new SyntaxToken(SyntaxKind.StringLiteral, start, text, buf.toString());
  }

  private char scanEscapeSequence() {
    var ch = peekAndMove();
    switch (ch) {
      case 'n':
        return '\n';
      case 'r':
        return '\r';
      case 't':
        return '\t';
      default:
        return ch;
    }
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
      var errorToken = new SyntaxToken(SyntaxKind.ErrorToken, start, text);
      diagnostics.reportInvalidNumber(errorToken, Long.class);
      return errorToken;
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
      case "var":
        return new SyntaxToken(SyntaxKind.VarKeyword, start, text);
      case "if":
        return new SyntaxToken(SyntaxKind.IfKeyword, start, text);
      case "else":
        return new SyntaxToken(SyntaxKind.ElseKeyword, start, text);
      case "while":
        return new SyntaxToken(SyntaxKind.WhileKeyword, start, text);
      case "break":
        return new SyntaxToken(SyntaxKind.BreakKeyword, start, text);
      case "continue":
        return new SyntaxToken(SyntaxKind.ContinueKeyword, start, text);
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
