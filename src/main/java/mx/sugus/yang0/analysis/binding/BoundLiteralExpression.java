package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.LiteralExpression;

public class BoundLiteralExpression implements BoundExpression {

  private final LiteralExpression literal;

  public BoundLiteralExpression(LiteralExpression literal) {
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
