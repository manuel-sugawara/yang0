package mx.sugus.yang0;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import mx.sugus.yang0.analysis.Compilation;
import mx.sugus.yang0.analysis.binding.Binder;
import mx.sugus.yang0.analysis.binding.BoundGlobalScope;
import mx.sugus.yang0.analysis.binding.BoundScope;
import mx.sugus.yang0.analysis.syntax.Diagnostics;
import mx.sugus.yang0.analysis.syntax.Expression;
import mx.sugus.yang0.analysis.syntax.Parser;

/** Starting point for the Yang0 REPL */
public class Main {

  public static void main(String[] args) throws java.io.IOException {
    var in = new BufferedReader(new InputStreamReader(System.in));

    var showTree = false;
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
        break;
      }
      var parser = new Parser(line);
      var tree = parser.parse();
      if (showTree) {
        System.out.println("Tree: " + tree.getRoot());
        break;
      }
      if (parser.getDiagnostics().hasErrors()) {
        reportErrors(parser.getDiagnostics());
        break;
      }
      var binder = new Binder(new BoundScope(), parser.getDiagnostics());
      var boundExpression = binder.bindExpression((Expression) tree.getRoot());
      var compilation =
          new Compilation(binder.getDiagnostics(), new BoundGlobalScope(), boundExpression);
      if (compilation.getDiagnostics().hasErrors()) {
        reportErrors(compilation.getDiagnostics());
        break;
      } else {
        var evaluator = new Eval(compilation);
        System.out.println("Result: " + evaluator.eval());
      }
    }
  }

  public static void reportErrors(Diagnostics diagnostics) {
    System.out.printf(
        "Errors while parsing input:\n%s\n",
        diagnostics.getDiagnostics().stream()
            .map(Object::toString)
            .collect(Collectors.joining("\n")));
  }
}
