package mx.sugus.yang0;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import mx.sugus.yang0.analysis.Compilation;
import mx.sugus.yang0.analysis.binding.Binder;
import mx.sugus.yang0.analysis.binding.EvalState;
import mx.sugus.yang0.analysis.binding.BoundScope;
import mx.sugus.yang0.analysis.syntax.Diagnostics;
import mx.sugus.yang0.analysis.syntax.Parser;
import mx.sugus.yang0.analysis.syntax.StatementSyntax;

/** Starting point for the Yang0 REPL */
public class Main {

  public static void main(String[] args) throws java.io.IOException {
    var in = new BufferedReader(new InputStreamReader(System.in));

    var showTree = false;
    var state = new EvalState();
    while (true) {
      System.out.print("> ");
      System.out.flush();
      var line = in.readLine();
      if (line == null || line.length() == 0) {
        System.out.println("Bye!");
        break;
      }
      if (line.equals("#showTree")) {
        showTree = !showTree;
        System.out.println("showTree " + showTree);
        continue;
      }
      var parser = new Parser(line);
      var tree = parser.parse();
      if (showTree) {
        System.out.println("Tree: " + tree.getRoot());
        continue;
      }
      if (parser.getDiagnostics().hasErrors()) {
        reportErrors(parser.getDiagnostics());
        continue;
      }
      var binder = new Binder(getScope(state), parser.getDiagnostics());
      var boundStatement = binder.bindStatement((StatementSyntax) tree.getRoot());
      var compilation =
          new Compilation(binder.getDiagnostics(), state, boundStatement);
      if (compilation.getDiagnostics().hasErrors()) {
        reportErrors(compilation.getDiagnostics());
        continue;
      } else {
        var evaluator = new Eval(compilation);
        System.out.println("Result: " + evaluator.eval());
      }
    }
  }

  public static BoundScope getScope(EvalState state) {
    var scope = new BoundScope();
    for (var symbol : state.getSymbols()) {
      scope.declareSymbol(symbol);
    }
    return scope;
  }

  public static void reportErrors(Diagnostics diagnostics) {
    System.out.printf(
        "Errors while parsing input:\n%s\n",
        diagnostics.getDiagnostics().stream()
            .map(Object::toString)
            .collect(Collectors.joining("\n")));
  }
}
