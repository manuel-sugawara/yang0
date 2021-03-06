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
    var statement = parseStatement();
    var token = expect(SyntaxKind.EofToken);
    return new SyntaxTree(diagnostics, statement, token);
  }

  private StatementSyntax parseStatement() {
    var kind = peek().getKind();

    switch (kind) {
      case OpenBracketToken:
        return parseBlockStatement();
      case IfKeyword:
        return parseIfStatement();
      case VarKeyword:
        return parseDeclareStatement();
      case WhileKeyword:
        return parseWhileStatement();
      case BreakKeyword:
        return parseBreakStatement();
      case ContinueKeyword:
        return parseContinueStatement();
      default:
        return parseExpressionStatement();
    }
  }

  private StatementSyntax parseBreakStatement() {
    var keyword = expect(SyntaxKind.BreakKeyword);
    return new BreakStatementSyntax(keyword);
  }

  private StatementSyntax parseContinueStatement() {
    var keyword = expect(SyntaxKind.ContinueKeyword);
    return new ContinueStatementSyntax(keyword);
  }


  private StatementSyntax parseIfStatement() {
    var ifKeyword = expect(SyntaxKind.IfKeyword);
    var condition = parseExpression();
    var ifBody = parseStatement();
    if (peek().getKind() == SyntaxKind.ElseKeyword) {
      var elseKeyword = next();
      var elseBody = parseStatement();
      return new IfStatementSynax(ifKeyword, condition, ifBody, elseKeyword, elseBody);
    }
    return new IfStatementSynax(ifKeyword, condition, ifBody);
  }

  private StatementSyntax parseWhileStatement() {
    var whileKeyword = expect(SyntaxKind.WhileKeyword);
    var condition = parseExpression();
    var body = parseStatement();
    return new WhileStatementSyntax(whileKeyword, condition, body);
  }

  private StatementSyntax parseBlockStatement() {
    var start = expect(SyntaxKind.OpenBracketToken);
    var statements = new ArrayList<StatementSyntax>();
    loop:
    while (true) {
      var kind = peek().getKind();
      switch (kind) {
        case CloseBracketToken:
        case EofToken:
          break loop;
        default:
          statements.add(parseStatement());
      }
    }
    var end = expect(SyntaxKind.CloseBracketToken);
    return new BlockStatementSyntax(start, end, statements);
  }

  private StatementSyntax parseExpressionStatement() {
    var expression = parseExpression();
    return new ExpressionStatementSyntax(expression);
  }

  private StatementSyntax parseDeclareStatement() {
    var varKeyword = next();
    var identifier = expect(SyntaxKind.Identifier);
    var assign = expect(SyntaxKind.EqualsToken);
    var initValue = parseExpression();
    return new DeclareStatementSyntax(varKeyword, identifier, assign, initValue);
  }

  private ExpressionSyntax parseExpression() {
    return parseAssignmentExpression();
  }

  private ExpressionSyntax parseAssignmentExpression() {
    if (peek(0).getKind() == SyntaxKind.Identifier && peek(1).getKind() == SyntaxKind.EqualsToken) {
      var identifier = next();
      var operator = next();
      var expression = parseAssignmentExpression();
      return new AssignmentExpressionSyntax(identifier, operator, expression);
    }

    return parseBinaryExpression(0);
  }

  private ExpressionSyntax parseBinaryExpression(int parentPrecedence) {
    var unaryOperatorPrecedence = getUnaryOperatorPriority(peek());
    ExpressionSyntax left;

    if (unaryOperatorPrecedence != 0 && unaryOperatorPrecedence > parentPrecedence) {
      var operator = next();
      var operand = parsePrimary();
      left = new UnaryExpressionSyntax(operator, operand);
    } else {
      left = parsePrimary();
    }

    while (true) {
      int precedence = getBinaryOperatorPriority(peek());
      if (precedence == 0 || precedence <= parentPrecedence) {
        break;
      }
      left = new BinaryExpressionSyntax(left, next(), parseBinaryExpression(precedence));
    }
    return left;
  }

  private ExpressionSyntax parsePrimary() {
    var token = next();
    var kind = token.getKind();

    switch (kind) {
      case OpenParenToken:
        var expression = parseExpression();
        var end = expect(SyntaxKind.CloseParenToken);
        return new ParentExpressionSyntax(token, expression, end);
      case LongToken:
      case StringLiteral:
      case TrueKeyword:
      case FalseKeyword:
        return new LiteralExpressionSyntax(token);
      case Identifier:
        if (peek().getKind() == SyntaxKind.OpenParenToken) {
          return parseFunctionCall(token);
        } else {
          return new VariableExpressionSyntax(token);
        }
      default:
        diagnostics.reportExpectingPrimaryExpression(token);
        return new ErrorExpressionSyntax("primary", token);
    }
  }

  private ExpressionSyntax parseFunctionCall(SyntaxToken identifier) {
    var argumentsStart = expect(SyntaxKind.OpenParenToken);
    var arguments = new ArrayList<ExpressionSyntax>();
    if (peek().getKind() != SyntaxKind.CloseParenToken) {
      while (true) {
        arguments.add(parseExpression());
        if (peek().getKind() != SyntaxKind.CommaToken) {
          break;
        }
        next();
      }
    }
    var argumentsEnd = expect(SyntaxKind.CloseParenToken);
    return new FunctionCallExpressionSyntax(identifier, argumentsStart, arguments, argumentsEnd);
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
      diagnostics.reportUnexpectedToken(token, kind);
      return new SyntaxToken(kind, -1, null);
    }
    return token;
  }

  @Override
  public String toString() {
    return "Parser{" + "tokens=" + tokens + ", position=" + position + '}';
  }
}
