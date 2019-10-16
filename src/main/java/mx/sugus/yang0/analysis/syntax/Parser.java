package mx.sugus.yang0.analysis.syntax;

import static mx.sugus.yang0.analysis.syntax.SyntaxFacts.getBinaryOperatorPriority;
import static mx.sugus.yang0.analysis.syntax.SyntaxFacts.getUnaryOperatorPriority;

import java.util.ArrayList;
import java.util.List;
import mx.sugus.yang0.analysis.text.TextSource;

public class Parser {

  private final List<SyntaxToken> tokens;
  private final Diagnostics diagnostics;
  private int position;

  public Parser(String text) {
    this(new TextSource(text));
  }

  public Parser(TextSource source) {
    diagnostics = new Diagnostics(source);
    var lexer = new Lexer(source, diagnostics);
    tokens = new ArrayList<>();
    SyntaxToken token;
    do {
      token = lexer.next();
      if (token.getKind() != SyntaxKind.WhitespaceToken) {
        tokens.add(token);
      }
    } while (token.getKind() != SyntaxKind.EofToken);
  }

  public Diagnostics getDiagnostics() {
    return diagnostics;
  }

  public SyntaxTree parse() {
    Expression expression = parseExpression(0);
    SyntaxToken token = expect(SyntaxKind.EofToken);
    return new SyntaxTree(diagnostics, expression, token);
  }

  private Expression parseExpression(int parentPrecedence) {
    var unaryOperatorPrecedence = getUnaryOperatorPriority(peek());
    Expression left;

    if (unaryOperatorPrecedence != 0 && unaryOperatorPrecedence > parentPrecedence) {
      var operator = next();
      var operand = parsePrimary();
      left = new UnaryExpression(operator, operand);
    } else {
      left = parsePrimary();
    }

    while (true) {
      int precedence = getBinaryOperatorPriority(peek());
      if (precedence == 0 || precedence <= parentPrecedence) {
        break;
      }
      left = new BinaryExpression(left, next(), parseExpression(precedence));
    }
    return left;
  }

  private Expression parsePrimary() {
    var token = next();
    var kind = token.getKind();

    switch (kind) {
      case OpenParenToken:
        var expression = parseExpression(0);
        var end = expect(SyntaxKind.CloseParenToken);
        return new ParentExpression(token, expression, end);
      case LongToken:
      case TrueKeyword:
      case FalseKeyword:
        return new LiteralExpression(token);
      default:
        diagnostics.reportExpectingPrimaryExpression(token.getPosition(), token);
        return new ErrorExpression("primary", token);
    }
  }

  private SyntaxToken next() {
    var size = tokens.size();
    if (position >= size) {
      return tokens.get(size - 1);
    }
    return tokens.get(position++);
  }

  private SyntaxToken peek() {
    return peek(0);
  }

  private SyntaxToken peek(int offset) {
    int size = tokens.size();
    if (position + offset >= size) {
      return tokens.get(size - 1);
    }
    return tokens.get(position + offset);
  }

  private SyntaxToken expect(SyntaxKind kind) {
    var token = next();
    if (token.getKind() != kind) {
      diagnostics.reportUnexpectedToken(token.getPosition(), token, kind);
      return new SyntaxToken(kind, -1, null);
    }
    return token;
  }

  @Override
  public String toString() {
    return "Parser{" + "tokens=" + tokens + ", position=" + position + '}';
  }
}
