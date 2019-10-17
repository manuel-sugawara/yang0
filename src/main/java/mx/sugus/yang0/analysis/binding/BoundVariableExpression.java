package mx.sugus.yang0.analysis.binding;

public class BoundVariableExpression implements BoundExpression {

  private final VariableSymbol symbol;

  public BoundVariableExpression(VariableSymbol symbol) {
    this.symbol = symbol;
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
    return BoundNodeKind.VariableExpression;
  }
}
