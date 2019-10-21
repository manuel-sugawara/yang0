package mx.sugus.yang0.analysis.syntax;

public class BinaryExpressionSyntax implements ExpressionSyntax {

  private final ExpressionSyntax left;
  private final SyntaxToken operator;
  private final ExpressionSyntax right;

  public BinaryExpressionSyntax(ExpressionSyntax left, SyntaxToken operator, ExpressionSyntax right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.BinaryExpression;
  }

  public ExpressionSyntax getLeft() {
    return left;
  }

  public ExpressionSyntax getRight() {
    return right;
  }

  public SyntaxToken getOperator() {
    return operator;
  }

  @Override
  public String toString() {
    return "(" + operator.getSrc() + " " + left.toString() + " " + right.toString() + ")";
  }
}
