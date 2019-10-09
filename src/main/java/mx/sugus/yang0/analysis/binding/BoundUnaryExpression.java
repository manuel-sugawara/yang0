package mx.sugus.yang0.analysis.binding;

public class BoundUnaryExpression implements BoundExpression {

  private final BoundUnaryOperatorKind operatorKind;
  private final BoundExpression operand;

  public BoundUnaryExpression(
      BoundUnaryOperatorKind operatorKind, BoundExpression operand) {
    this.operatorKind = operatorKind;
    this.operand = operand;
  }

  public BoundUnaryOperatorKind getOperatorKind() {
    return operatorKind;
  }

  public BoundExpression getOperand() {
    return operand;
  }

  @Override
  public Class getType() {
    return operatorKind.getReturnType();
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.UnaryExpression;
  }
}
