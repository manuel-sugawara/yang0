package mx.sugus.yang0.analysis.syntax;

public class UnaryExpression implements Expression {

  private final SyntaxToken operator;
  private final Expression operand;

  public UnaryExpression(SyntaxToken operator, Expression operand) {
    this.operator = operator;
    this.operand = operand;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.UnaryExpression;
  }

  public SyntaxToken getOperator() {
    return operator;
  }

  public Expression getOperand() {
    return operand;
  }

  @Override
  public String toString() {
    return "(" + operator.getSrc() + " " + operand.toString() + ")";
  }
}
