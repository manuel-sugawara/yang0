package mx.sugus.yang0.analysis.eval;

import mx.sugus.yang0.analysis.binding.Type;
import mx.sugus.yang0.analysis.binding.VariableSymbol;

public abstract class FunctionSymbol {

  private final String name;
  private final Type returns;
  private final VariableSymbol[] arguments;

  public FunctionSymbol(String name, Type returns, VariableSymbol[] arguments) {
    this.name = name;
    this.returns = returns;
    this.arguments = arguments;
  }

  public String getName() {
    return name;
  }

  public VariableSymbol[] getArguments() {
    return arguments;
  }

  public Type getReturns() {
    return returns;
  }

  public abstract Result invoke(EvalState scope);
}
