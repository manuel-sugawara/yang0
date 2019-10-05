package mx.sugus.yang0;

import static mx.sugus.yang0.SyntaxFacts.getOperatorPriority;

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
    } while (token.getKind() != SyntaxKind.Eof);
  }



  public Expression parseExpression() {
    return parseExpression(0);
  }

  private Expression parseExpression(int parentPrecedence) {
    var node = parsePrimary();

    while (true) {
      var token = peek();
      int precedence = getOperatorPriority(token);
      if (precedence == 0 || precedence <= parentPrecedence) {
        break;
      }
      ++position;
      node = new BinaryExpression(node, token, parseExpression(precedence));
    }

    return node;
  }

  private Expression parsePrimary() {
    Token token = peek();
    if (token.getKind() == SyntaxKind.OpenParen) {
      ++position;
      var node = parseExpression();
      token = peek();
      if (token.getKind() != SyntaxKind.CloseParen) {
        diagnostics.addError(token, "Expecting closing parenthesis");
      }
      ++position;
      return new ParentExpression(node);
    }

    if (token.getKind() == SyntaxKind.LongToken) {
      ++position;
      return new LongExpression(token);
    }
    return new ErrorExpression("primary", token);
  }

  private Token peek() {
    int size = tokens.size();
    if (position >= size) {
      return tokens.get(size - 1);
    }
    return tokens.get(position);
  }

  @Override
  public String toString() {
    return "Parser{" + "tokens=" + tokens + ", position=" + position + '}';
  }
}
