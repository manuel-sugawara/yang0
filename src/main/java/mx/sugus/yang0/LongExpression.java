package mx.sugus.yang0;

public class LongExpression implements Expression {

  private final Token value;

  public LongExpression(Token value) {
    this.value = value;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.LongExpression;
  }

  public long getValue() {
    return (long) value.getValue();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
