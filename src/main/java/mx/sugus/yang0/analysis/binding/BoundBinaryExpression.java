package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundBinaryExpression implements BoundExpression {

  private final BoundExpression left;
  private final BoundBinaryOperatorKind operatorKind;
  private final SyntaxToken operator;
  private final BoundExpression right;

  public BoundBinaryExpression(
      BoundExpression left, SyntaxToken operator, BoundBinaryOperatorKind operatorKind, BoundExpression right) {
    this.left = left;
    this.operator = operator;
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
  public Type getType() {
    return operatorKind.getReturnType();
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.BinaryExpression;
  }
}
