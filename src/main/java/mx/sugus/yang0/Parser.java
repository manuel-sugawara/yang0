package mx.sugus.yang0;

import java.util.ArrayList;
import java.util.List;

public class Parser {

  private final List<Token> tokens;
  private int position;

  public Parser(String src) {
    tokens = new ArrayList<>();
    Lexer lexer = new Lexer(src);
    Token token;
    do {
      token = lexer.next();
      if (token.getKind() != SyntaxKind.Whitespace) {
        tokens.add(token);
      }
    } while (token.getKind() != SyntaxKind.Eof);
  }

  public SyntaxNode parseExpression() {
    return parseAdditiveExpression();
  }

  private SyntaxNode parseAdditiveExpression() {
    SyntaxNode node = parseMultiplicativeExpression();
    Token token;
    while (true) {
      token = peek();
      SyntaxKind kind = token.getKind();
      if (kind != SyntaxKind.PlusToken && kind != SyntaxKind.MinusToken) {
        break;
      }
      ++position;
      node = new BinaryExpression(node, token, parseExpression());
    }
    return node;
  }

  private SyntaxNode parseMultiplicativeExpression() {
    SyntaxNode node = parsePrimary();
    Token token;
    while (true) {
      token = peek();
      SyntaxKind kind = token.getKind();
      if (kind != SyntaxKind.StartToken && kind != SyntaxKind.SlashToken) {
        break;
      }
      ++position;
      node = new BinaryExpression(node, token, parseExpression());
    }
    return node;
  }

  private SyntaxNode parsePrimary() {
    Token token = peek();
    if (token.getKind() == SyntaxKind.OpenParen) {
      ++position;
      SyntaxNode node = parseExpression();
      token = peek();
      if (token.getKind() != SyntaxKind.CloseParen) {}
      ++position;
      return node;
    }

    if (token.getKind() == SyntaxKind.LongLiteral) {
      ++position;
      return new LongExpression(token);
    }
    return new ErrorNode("primary", token);
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
