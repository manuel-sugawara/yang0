package mx.sugus.yang0.analysis.text;

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
      //System.out.printf("index: %s, low: %s, high: %s%n", x, bounds[0], bounds[1]);
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
      //System.out.println(boundary);
    }
  }
}
