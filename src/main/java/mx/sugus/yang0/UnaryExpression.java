package mx.sugus.yang0;

public class UnaryExpression implements Expression {

  private final Token operator;
  private final Expression operand;

  public UnaryExpression(Token operator, Expression operand) {
    this.operator = operator;
    this.operand = operand;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.UnaryExpression;
  }

  public Token getOperator() {
    return operator;
  }

  public Expression getOperand() {
    return operand;
  }
}
