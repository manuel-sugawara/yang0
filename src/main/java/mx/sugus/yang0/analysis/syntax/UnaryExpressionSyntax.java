package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

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

  @Override
  public TextSpan getSpan() {
    return TextSpan.from(operator.getSpan(), operand.getSpan());
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
