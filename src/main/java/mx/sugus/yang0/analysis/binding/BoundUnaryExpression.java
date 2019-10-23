package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundUnaryExpression implements BoundExpression {

  private final SyntaxToken operator;
  private final BoundUnaryOperatorKind operatorKind;
  private final BoundExpression boundOperand;

  public BoundUnaryExpression(
      SyntaxToken operator, BoundUnaryOperatorKind operatorKind, BoundExpression boundOperand) {
    this.operator = operator;
    this.operatorKind = operatorKind;
    this.boundOperand = boundOperand;
  }

  public BoundUnaryOperatorKind getOperatorKind() {
    return operatorKind;
  }

  public BoundExpression getBoundOperand() {
    return boundOperand;
  }

  @Override
  public Type getType() {
    return operatorKind.getReturnType();
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.UnaryExpression;
  }
}
