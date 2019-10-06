package mx.sugus.yang0;

public class Eval {

  public static long eval(SyntaxNode node) {
    var kind = node.getKind();
    if (kind == SyntaxKind.ParentExpression) {
      return eval(((ParentExpression) node).getExpression());
    }

    if (kind == SyntaxKind.LongExpression) {
      var expr = (LongExpression) node;
      return expr.getValue();
    }

    if (kind == SyntaxKind.BinaryExpression) {
      var expr = (BinaryExpression) node;
      var left = eval(expr.getLeft());
      var right = eval(expr.getRight());
      switch (expr.getOperator().getKind()) {
        case PlusToken:
          return left + right;
        case MinusToken:
          return left - right;
        case StartToken:
          return left * right;
        case SlashToken:
          return left / right;
        default:
          throw new IllegalStateException("unexpected binary operator: " + expr.getOperator());
      }
    }

    if (kind == SyntaxKind.UnaryExpression) {
      var expr = (UnaryExpression) node;
      var operand = eval(expr.getOperand());
      switch (expr.getOperator().getKind()) {
        case PlusToken:
          return operand;
        case MinusToken:
          return -operand;
        default:
          throw new IllegalStateException("unexpected unary operator: " + expr.getOperator());
      }
    }

    throw new IllegalStateException("unexpected node kind: " + kind);
  }
}
