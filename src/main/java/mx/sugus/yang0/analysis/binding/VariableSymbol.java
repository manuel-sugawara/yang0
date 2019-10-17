package mx.sugus.yang0.analysis.binding;

import java.util.Objects;
import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class VariableSymbol {

  private final SyntaxToken name;
  private final Class type;

  public VariableSymbol(SyntaxToken name, Class type) {
    this.name = name;
    this.type = type;
  }

  public SyntaxToken getName() {
    return name;
  }

  public Class getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VariableSymbol that = (VariableSymbol) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }
}
