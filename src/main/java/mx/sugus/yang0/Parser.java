package mx.sugus.yang0;

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
    return parseAdditiveExpression();
  }

  private Expression parseAdditiveExpression() {
    var node = parseMultiplicativeExpression();
    Token token;
    while (true) {
      token = peek();
      var kind = token.getKind();
      if (kind != SyntaxKind.PlusToken && kind != SyntaxKind.MinusToken) {
        break;
      }
      ++position;
      node = new BinaryExpression(node, token, parseExpression());
    }
    return node;
  }

  private Expression parseMultiplicativeExpression() {
    var node = parsePrimary();
    Token token;
    while (true) {
      token = peek();
      var kind = token.getKind();
      if (kind != SyntaxKind.StartToken && kind != SyntaxKind.SlashToken) {
        break;
      }
      ++position;
      node = new BinaryExpression(node, token, parseExpression());
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
