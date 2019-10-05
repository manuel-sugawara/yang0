package mx.sugus.yang0;

public class Eval {

  public static long eval(SyntaxNode node) {
    SyntaxKind kind = node.getKind();
    if (kind == SyntaxKind.LongExpression) {
      LongExpression expr = (LongExpression) node;
      return expr.getValue();
    }

    if (kind == SyntaxKind.BinaryExpression) {
      BinaryExpression expr = (BinaryExpression) node;
      long left = eval(expr.getLeft());
      long right = eval(expr.getRight());
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
          throw new IllegalStateException("unexpected operand: " + expr.getOperator());
      }
    }

    throw new IllegalStateException("unexpected node kind: " + kind);
  }

}
