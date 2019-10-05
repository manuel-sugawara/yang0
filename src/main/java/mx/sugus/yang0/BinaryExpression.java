package mx.sugus.yang0;

public class BinaryExpression implements SyntaxNode {

  private final SyntaxNode left;
  private final Token operand;
  private final SyntaxNode right;

  public BinaryExpression(SyntaxNode left, Token operand, SyntaxNode right) {
    this.left = left;
    this.operand = operand;
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

  public Token getOperand() {
    return operand;
  }

  @Override
  public String toString() {
    return "(" + operand.toString() + " " + left.toString() + " " + right.toString() + ")";
  }
}
