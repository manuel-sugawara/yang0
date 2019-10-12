package mx.sugus.yang0;

import static mx.sugus.yang0.Eval.eval;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import mx.sugus.yang0.analysis.Compilation;
import mx.sugus.yang0.analysis.binding.Binder;
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
      } else if (line.equals("#showTree")) {
        showTree = !showTree;
      } else {
        var parser = new Parser(line);
        var tree = parser.parse();
        var binder = new Binder(parser.getDiagnostics());
        var boundExpression = binder.bindExpression((Expression) tree.getRoot());
        var compilation = new Compilation(binder.getDiagnostics(), boundExpression);
        if (showTree) {
          System.out.println("Tree: " + tree.getRoot());
        }
        if (compilation.getDiagnostics().hasErrors()) {
          System.out.printf(
              "Errors while parsing input:\n %s\n",
              compilation.getDiagnostics().getErrors().stream().collect(Collectors.joining("\n")));
        } else {
          System.out.println("Result: " + eval(compilation));
        }
      }
    }
  }
}
