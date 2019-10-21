package mx.sugus.yang0.analysis.syntax;

public class UnaryExpressionSyntax implements ExpressionSyntax {

  private final SyntaxToken operator;
  private final ExpressionSyntax operand;

  public UnaryExpressionSyntax(SyntaxToken operator, ExpressionSyntax operand) {
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

  public ExpressionSyntax getOperand() {
    return operand;
  }

  @Override
  public String toString() {
    return "(" + operator.getSrc() + " " + operand.toString() + ")";
  }
}
