package mx.sugus.yang0.analysis.binding;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EvalState {

  private final Map<VariableSymbol, Object> values;
  private final EvalState parent;

  public EvalState() {
    this(null);
  }

  EvalState(EvalState parent) {
    this.values = new HashMap<>();
    this.parent = parent;
  }

  public EvalState push() {
    return new EvalState(this);
  }

  public EvalState pop() {
    if (parent == null) {
      throw new IllegalStateException("");
    }

    return this.parent;
  }

  public Set<VariableSymbol> getSymbols() {
    return values.keySet();
  }

  public void declare(VariableSymbol symbol, Object value) {
    values.put(symbol, value);
  }

  public void setValue(VariableSymbol symbol, Object value) {
    if (!values.containsKey(symbol)) {
      if (parent == null) {
        throw new IllegalStateException("variable not known " + symbol);
      }
      parent.setValue(symbol, value);
    }
    values.put(symbol, value);
  }

  public Object getValue(VariableSymbol symbol) {
    if (values.containsKey(symbol)) {
      return values.get(symbol);
    }
    return parent.getValue(symbol);
  }
}
