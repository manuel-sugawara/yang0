package mx.sugus.yang0.analysis.binding;

public class BoundBinaryExpression implements BoundExpression {

  private final BoundExpression left;
  private final BoundBinaryOperatorKind operatorKind;
  private final BoundExpression right;

  public BoundBinaryExpression(
      BoundExpression left, BoundBinaryOperatorKind operatorKind, BoundExpression right) {
    this.left = left;
    this.operatorKind = operatorKind;
    this.right = right;
  }

  public BoundExpression getLeft() {
    return left;
  }

  public BoundBinaryOperatorKind getOperatorKind() {
    return operatorKind;
  }

  public BoundExpression getRight() {
    return right;
  }

  @Override
  public Class getType() {
    return operatorKind.getReturnType();
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.BinaryExpression;
  }
}
