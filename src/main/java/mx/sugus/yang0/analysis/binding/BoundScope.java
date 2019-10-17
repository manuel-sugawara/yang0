package mx.sugus.yang0.analysis.binding;

import java.util.HashMap;
import java.util.Map;
import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundScope {

  private final BoundScope parent;

  private final Map<String, VariableSymbol> variables;

  public BoundScope() {
    this(null);
  }

  public BoundScope(BoundScope parent) {

    this.parent = parent;
    this.variables = new HashMap<>();
  }

  public VariableSymbol getDeclared(SyntaxToken identifier) {
    return variables.get(identifier.getSrc());
  }

  public VariableSymbol declare(SyntaxToken identifier, Class type) {
    var name = identifier.getSrc();
    var symbol = new VariableSymbol(identifier, type);
    variables.put(name, symbol);
    return symbol;
  }
}
