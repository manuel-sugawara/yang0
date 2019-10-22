package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundDeclareStatement implements BoundStatement {

  private final VariableSymbol variable;
  private final SyntaxToken varKeyword;
  private final SyntaxToken identifier;
  private final SyntaxToken equalsToken;
  private final BoundExpression initExpression;

  public BoundDeclareStatement(
      VariableSymbol variable,
      SyntaxToken varKeyword,
      SyntaxToken identifier,
      SyntaxToken equalsToken,
      BoundExpression initExpression) {
    this.variable = variable;
    this.varKeyword = varKeyword;
    this.identifier = identifier;
    this.equalsToken = equalsToken;
    this.initExpression = initExpression;
  }

  public VariableSymbol getSymbol() {
    return variable;
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

  public BoundExpression getInitExpression() {
    return initExpression;
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.DeclareStatement;
  }
}
