package mx.sugus.yang0;

public class BinaryExpression implements Expression {

  private final SyntaxNode left;
  private final Token operator;
  private final SyntaxNode right;

  public BinaryExpression(SyntaxNode left, Token operator, SyntaxNode right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.BinaryExpression;
  }

  public SyntaxNode getLeft() {
    return left;
  }

  public SyntaxNode getRight() {
    return right;
  }

  public Token getOperator() {
    return operator;
  }

  @Override
  public String toString() {
    return "(" + operator.toString() + " " + left.toString() + " " + right.toString() + ")";
  }
}
