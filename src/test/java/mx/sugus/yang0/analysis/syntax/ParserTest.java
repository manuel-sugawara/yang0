package mx.sugus.yang0.analysis.syntax;

import static org.junit.Assert.assertEquals;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class ParserTest {

  @SuppressWarnings("unused")
  private static Object[][] testExpression_Params() {
    return new Object[][] {
      {"1", "1"},
      {"1 + 2", "(+ 1 2)"},
      {"1 + 2 * 3", "(+ 1 (* 2 3))"},
      {"(1 + 2) * 3", "(* (+ 1 2) 3)"},
      {"1 * 2 + 3", "(+ (* 1 2) 3)"},
      {"1 * (2 + 3)", "(* 1 (+ 2 3))"},
      {"-1 + 2 * 3", "(+ (- 1) (* 2 3))"},
      {"1 + -2 * 3", "(+ 1 (* (- 2) 3))"},
      {"1 + 2 * -3", "(+ 1 (* 2 (- 3)))"},
      {"true || false && true", "(|| true (&& false true))"},
      {"true || (false && true)", "(|| true (&& false true))"},
      {"(true || false) && true", "(&& (|| true false) true)"},
      {"true && false || true", "(|| (&& true false) true)"},
      {"!true && false || true", "(|| (&& (! true) false) true)"},
      {"true && !false || true", "(|| (&& true (! false)) true)"},
      {"true || false && !true", "(|| true (&& false (! true)))"},
      {"5 + 2 == 14 / 2 && 6 >= 20", "(&& (== (+ 5 2) (/ 14 2)) (>= 6 20))"},
      {"1 <= 2 && 1 < 2", "(&& (<= 1 2) (< 1 2))"},
      {"!true", "(! true)"},
      {"!false", "(! false)"},
      {"-1", "(- 1)"},
      {"+1", "(+ 1)"},
    };
  }

  @Test
  @Parameters(method = "testExpression_Params")
  public void testExpression(String text, String expected) {

    var parser = new Parser(text);
    var tree = parser.parse();
    var root = tree.getRoot();

    assertEquals("Unexpected tree for source: \"" + text + "\"", expected, root.toString());
  }
}
