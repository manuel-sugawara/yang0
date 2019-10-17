package mx.sugus.yang0.analysis.binding;

import java.util.HashMap;
import java.util.Map;

public class BoundGlobalScope {

  private final Map<VariableSymbol, Object> values;

  public BoundGlobalScope() {
    values = new HashMap<>();
  }

  public void setValue(VariableSymbol symbol, Object value) {
    values.put(symbol, value);
  }

  public Object getValue(VariableSymbol symbol) {
    return values.get(symbol);
  }
}
