package mx.sugus.yang0.analysis.eval;

import java.util.function.Function;
import mx.sugus.yang0.analysis.binding.Type;
import mx.sugus.yang0.analysis.binding.VariableSymbol;
import mx.sugus.yang0.analysis.syntax.SyntaxKind;
import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class SystemFunctionSymbol extends FunctionSymbol {

  public static SystemFunctionSymbol Print = from("println", Type.None, args -> {
    System.out.println(args[0]);
    return Result.None;
  });

  private final Function<Object[], Result> handler;

  public SystemFunctionSymbol(
      String name, Type returns, VariableSymbol[] arguments, Function<Object[], Result> handler) {
    super(name, returns, arguments);
    this.handler = handler;
  }

  public static SystemFunctionSymbol from(
      String name, Type returns, Function<Object[], Result> handler, Object... args) {
    if (args.length % 2 != 0) {
      throw new IllegalArgumentException();
    }
    var arguments = new VariableSymbol[args.length / 2];
    for (var x = 0; x < args.length; x += 2) {
      var identifier = new SyntaxToken(SyntaxKind.Identifier, 0, (String) args[x]);
      var type = (Type) args[x + 1];
      arguments[x / 2] = new VariableSymbol(identifier, type);
    }

    return new SystemFunctionSymbol(name, returns, arguments, handler);
  }

  @Override
  public Result invoke(EvalState scope) {
    Object[] args = new Object[this.getArguments().length];
    var x = 0;
    for (VariableSymbol argument : getArguments()) {
      var value = scope.getValue(argument);
      args[x++] = value;
    }
    return handler.apply(args);
  }
}
