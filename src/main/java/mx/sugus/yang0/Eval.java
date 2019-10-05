package mx.sugus.yang0;

public class Eval {

  public static long eval(SyntaxNode node) {
    var kind = node.getKind();
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
          throw new IllegalStateException("unexpected operator: " + expr.getOperator());
      }
    }

    throw new IllegalStateException("unexpected node kind: " + kind);
  }

}
