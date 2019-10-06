package mx.sugus.yang0.analysis.syntax;

public class BinaryExpression implements Expression {

  private final Expression left;
  private final SyntaxToken operator;
  private final Expression right;

  public BinaryExpression(Expression left, SyntaxToken operator, Expression right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.BinaryExpression;
  }

  public Expression getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
  }

  public SyntaxToken getOperator() {
    return operator;
  }

  @Override
  public String toString() {
    return "(" + operator.toString() + " " + left.toString() + " " + right.toString() + ")";
  }
}
