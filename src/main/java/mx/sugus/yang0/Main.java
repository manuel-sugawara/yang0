package mx.sugus.yang0;

import static mx.sugus.yang0.Eval.eval;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** Starting point for the Yang0 REPL */
public class Main {

  public static void main(String[] args) throws java.io.IOException {
    var in = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      System.out.print("> ");
      System.out.flush();
      var line = in.readLine();
      if (line == null || line.length() == 0) {
        System.out.println("Bye!");
        break;
      } else {
        var parser = new Parser(line);
        var tree = parser.parse();
        System.out.println("Expression: " + tree.getRoot());
        System.out.println("Result: " + eval(tree.getRoot()));
      }
    }
  }
}
