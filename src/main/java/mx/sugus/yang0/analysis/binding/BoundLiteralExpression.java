package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.LiteralExpressionSyntax;

public class BoundLiteralExpression implements BoundExpression {

  private final LiteralExpressionSyntax literal;

  public BoundLiteralExpression(LiteralExpressionSyntax literal) {
    this.literal = literal;
  }

  @Override
  public Class getType() {
    return literal.getValue().getClass();
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.LiteralExpression;
  }

  public Object getValue() {
    return literal.getValue();
  }
}
