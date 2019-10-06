package mx.sugus.yang0.analysis.binding;

public class BoundLiteralExpression implements BoundExpression {

  private final Object value;

  public BoundLiteralExpression(Object value) {
    this.value = value;
  }

  @Override
  public Class getType() {
    return value.getClass();
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.LiteralExpression;
  }

  public Object getValue() {
    return value;
  }
}
