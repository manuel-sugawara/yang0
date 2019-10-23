package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.LiteralExpressionSyntax;

public class BoundLiteralExpression implements BoundExpression {

  private final LiteralExpressionSyntax literal;

  public BoundLiteralExpression(LiteralExpressionSyntax literal) {
    this.literal = literal;
  }

  @Override
  public Type getType() {
    switch (literal.getValueToken().getKind()) {
      case TrueKeyword:
      case FalseKeyword:
        return Type.Boolean;
      case LongToken:
        return Type.Long;
      default:
        throw new IllegalStateException("unknown literal kind: " + literal.getKind());
    }
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.LiteralExpression;
  }

  public Object getValue() {
    return literal.getValue();
  }
}
