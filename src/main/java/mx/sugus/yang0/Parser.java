package mx.sugus.yang0;

import static mx.sugus.yang0.SyntaxFacts.getBinaryOperatorPriority;
import static mx.sugus.yang0.SyntaxFacts.getUnaryOperatorPriority;

import java.util.ArrayList;
import java.util.List;

public class Parser {

  private final List<Token> tokens;
  private final Diagnostics diagnostics;
  private int position;

  public Parser(String src) {
    tokens = new ArrayList<>();
    diagnostics = new Diagnostics();
    var lexer = new Lexer(src, diagnostics);
    Token token;
    do {
      token = lexer.next();
      if (token.getKind() != SyntaxKind.WhitespaceToken) {
        tokens.add(token);
      }
    } while (token.getKind() != SyntaxKind.EofToken);
  }

  public SyntaxTree parse() {
    Expression expression = parseExpression(0);
    Token token = match(SyntaxKind.EofToken);
    return new SyntaxTree(diagnostics, expression, token);
  }

  private Expression parseExpression(int parentPrecedence) {
    Expression left;
    int unaryOperatorPrecedence = getUnaryOperatorPriority(peek());
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
    var token = peek();
    if (token.getKind() == SyntaxKind.OpenParenToken) {
      var start = next();
      var node = parseExpression(0);
      var end = match(SyntaxKind.CloseParenToken);
      return new ParentExpression(start, node, end);
    }

    if (token.getKind() == SyntaxKind.LongToken) {
      ++position;
      return new LongExpression(token);
    }
    return new ErrorExpression("primary", token);
  }

  private Token next() {
    int size = tokens.size();
    if (position >= size) {
      return tokens.get(size - 1);
    }
    return tokens.get(position++);
  }

  private Token peek() {
    int size = tokens.size();
    if (position >= size) {
      return tokens.get(size - 1);
    }
    return tokens.get(position);
  }

  private Token match(SyntaxKind kind) {
    Token token = peek();
    position++;
    if (token.getKind() != kind) {
      diagnostics.addError(token, "expecting token kind: " + kind);
      return new Token(kind, -1, null);
    }
    return token;
  }

  @Override
  public String toString() {
    return "Parser{" + "tokens=" + tokens + ", position=" + position + '}';
  }
}
