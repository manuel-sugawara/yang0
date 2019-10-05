package mx.sugus.yang0;

import static mx.sugus.yang0.Eval.eval;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** Starting point for the Yang0 REPL */
public class Main {

  public static void main(String[] args) throws java.io.IOException {
    BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      System.out.print("> ");
      System.out.flush();
      String line = buffer.readLine();
      if (line == null || line.length() == 0) {
        System.out.println("Bye!");
        break;
      } else {
        Parser parser = new Parser(line);
        System.out.println("parser: " + parser.toString());
        SyntaxNode expr = parser.parseExpression();
        System.out.println("Expression: " + expr);
        System.out.println("Result: " + eval(expr));
      }
    }
  }
}
