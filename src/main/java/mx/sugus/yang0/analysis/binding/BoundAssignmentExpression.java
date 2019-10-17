package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundAssignmentExpression implements BoundExpression {

  private final VariableSymbol symbol;
  private final SyntaxToken operator;
  private final BoundExpression initializer;

  public BoundAssignmentExpression(
      VariableSymbol symbol, SyntaxToken operator, BoundExpression initializer) {
    this.symbol = symbol;
    this.operator = operator;
    this.initializer = initializer;
  }

  public SyntaxToken getOperator() {
    return operator;
  }

  public BoundExpression getInitializer() {
    return initializer;
  }

  public VariableSymbol getSymbol() {
    return symbol;
  }

  @Override
  public Class getType() {
    return symbol.getType();
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.AssignmentExpression;
  }
}
