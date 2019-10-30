package mx.sugus.yang0.analysis.eval;

import mx.sugus.yang0.analysis.binding.Type;

public class Value {

  private final Type type;
  private final Object value;

  public Value(Type type, Object value) {
    this.type = type;
    this.value = value;
  }

  private static Value fromBool(boolean value) {
    return new Value(Type.Boolean, value);
  }

  private static Value fromLong(long value) {
    return new Value(Type.Long, value);
  }

  private static Value fromString(String value) {
    return new Value(Type.String, value);
  }

  public Type getType() {
    return type;
  }

  public Object getValue() {
    return value;
  }
}
