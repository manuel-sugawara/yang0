package mx.sugus.yang0.analysis.syntax;

import static org.junit.Assert.assertEquals;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import mx.sugus.yang0.analysis.text.TextSource;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class LexerTest {

  @SuppressWarnings("unused")
  private static Object[][] testSingleTokenKind_Params() {
    return new Object[][] {
      {SyntaxKind.EofToken, ""},
      {SyntaxKind.PlusToken, "+"},
      {SyntaxKind.MinusToken, "-"},
      {SyntaxKind.SlashToken, "/"},
      {SyntaxKind.StartToken, "*"},
      {SyntaxKind.PercentToken, "%"},
      {SyntaxKind.OpenParenToken, "("},
      {SyntaxKind.CloseParenToken, ")"},
      {SyntaxKind.TrueKeyword, "true"},
      {SyntaxKind.FalseKeyword, "false"},
      {SyntaxKind.AmpersandAmpersandToken, "&&"},
      {SyntaxKind.PipePipeToken, "||"},
      {SyntaxKind.LessThanToken, "<"},
      {SyntaxKind.LessThanEqualsToken, "<="},
      {SyntaxKind.GraterThanToken, ">"},
      {SyntaxKind.GraterThanEqualsToken, ">="},
      {SyntaxKind.EqualsEqualsToken, "=="},
      {SyntaxKind.BangEqualsToken, "!="},
      {SyntaxKind.BangToken, "!"},
      {SyntaxKind.ErrorToken, "&"},
      {SyntaxKind.ErrorToken, "|"},
      {SyntaxKind.Identifier, "a"},
      {SyntaxKind.Identifier, "ab"},
      {SyntaxKind.Identifier, "abc"},
      {SyntaxKind.WhitespaceToken, " "},
      {SyntaxKind.WhitespaceToken, "\n"},
      {SyntaxKind.WhitespaceToken, "\r\n"},
      {SyntaxKind.WhitespaceToken, "\t"},
      {SyntaxKind.LongToken, "1"},
      {SyntaxKind.LongToken, "12"},
      {SyntaxKind.LongToken, "123"},
    };
  }

  @Test
  @Parameters(method = "testSingleTokenKind_Params")
  public void testSingleTokenKind(SyntaxKind kind, String text) {
    var source = new TextSource(text);
    var lexer = new Lexer(source, new Diagnostics(source));
    var token = lexer.next();

    assertEquals("invalid kind for text: " + text, kind, token.getKind());
    var eof = lexer.next();
    assertEquals(
        "expecting EOF, got: " + eof.getKind() + ", for src: \"" + text + "\"",
        SyntaxKind.EofToken,
        eof.getKind());
  }
}
