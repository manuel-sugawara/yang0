package mx.sugus.yang0.analysis.text;


import java.util.stream.Collectors;
import mx.sugus.yang0.Eval;
import mx.sugus.yang0.analysis.Compilation;
import mx.sugus.yang0.analysis.binding.Binder;
import mx.sugus.yang0.analysis.binding.BoundGlobalScope;
import mx.sugus.yang0.analysis.binding.BoundScope;
import mx.sugus.yang0.analysis.syntax.ExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.Parser;
import org.junit.Test;

public class TextSourceTest {

  @Test
  public void test() {
    var text = new TextSource("hello\nworld\rthis\r\nis\nUnix");

    for (var boundary : text.getBoundaries()) {
      /*System.out.printf("boundary: %s'%n", boundary);
      System.out.printf(
          "text: '%s'%n", text.substring(boundary.start, boundary.start + boundary.length));

       */
    }
  }

  @Test
  public void test1() {
    var text =
        new TextSource(
            "hello\nworld\rthis\r\nis\nUnix\n\nhello\n" + "world\n" + "this\n" + "is\n" + "Unix");

    for (var x = 0; x < text.length(); x++) {
      var bounds = text.getLineAndColumn(x);
      // System.out.printf("index: %s, low: %s, high: %s%n", x, bounds[0], bounds[1]);
      // System.out.printf("%s%n%s%n", x, text.getBoundaries()[bounds[0]],
      // text.getBoundaries()[bounds[1]]);
    }
  }

  @Test
  public void test2() {
    var text =
        new TextSource(
            "hello\nworld\rthis\r\nis\nUnix\n\nhello\n" + "world\n" + "this\n" + "is\n" + "Unix");
    var boundaries = text.getBoundaries();
    for (var boundary : boundaries) {
      // System.out.println(boundary);
    }
  }

  @Test
  public void test3() {
    var parser = new Parser("(a = 10) + (a = 20) + a");
    var tree = parser.parse();
    var binder = new Binder(new BoundScope(), parser.getDiagnostics());
    var boundExpression = binder.bindExpression((ExpressionSyntax) tree.getRoot());
    var compilation =
        new Compilation(binder.getDiagnostics(), new BoundGlobalScope(), boundExpression);
    if (compilation.getDiagnostics().hasErrors()) {
      System.out.printf(
          "Errors while parsing input:\n%s\n",
          compilation.getDiagnostics().getDiagnostics().stream()
              .map(Object::toString)
              .collect(Collectors.joining("\n")));
    } else {
      System.out.println("Result: " + new Eval(compilation).eval());
    }
  }
}
