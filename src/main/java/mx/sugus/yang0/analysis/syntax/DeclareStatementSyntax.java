package mx.sugus.yang0.analysis.syntax;

public class DeclareStatementSyntax implements StatementSyntax {

  private final SyntaxToken varKeyword;
  private final SyntaxToken identifier;
  private final SyntaxToken equalsToken;
  private final ExpressionSyntax initExpression;

  public DeclareStatementSyntax(SyntaxToken varKeyword,
      SyntaxToken name, SyntaxToken equalsToken,
      ExpressionSyntax initExpression) {
    this.varKeyword = varKeyword;
    this.identifier = name;
    this.equalsToken = equalsToken;
    this.initExpression = initExpression;
  }


  public SyntaxToken getVarKeyword() {
    return varKeyword;
  }

  public SyntaxToken getIdentifier() {
    return identifier;
  }

  public SyntaxToken getEqualsToken() {
    return equalsToken;
  }

  public ExpressionSyntax getInitExpression() {
    return initExpression;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.DeclareStatement;
  }
}
