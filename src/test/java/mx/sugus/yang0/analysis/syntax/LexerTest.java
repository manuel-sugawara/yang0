package mx.sugus.yang0.analysis.syntax;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class LexerTest {

  @Test
  @Parameters(method = "testSingleTokenKind_Params")
  public void test(SyntaxKind kind, String text) {
    Lexer lexer = new Lexer(text, new Diagnostics());
    SyntaxToken token = lexer.next();

    Assert.assertEquals("invalid kind for text: " + text, kind, token.getKind());
  }

  @SuppressWarnings("unused")
  private static Object[][] testSingleTokenKind_Params() {
    return new Object[][] {
        { SyntaxKind.EofToken, "" },
        { SyntaxKind.PlusToken, "+" },
        { SyntaxKind.MinusToken, "-" },
        { SyntaxKind.SlashToken, "/" },
        { SyntaxKind.StartToken, "*" },
        { SyntaxKind.OpenParenToken, "(" },
        { SyntaxKind.CloseParenToken, ")" },
        { SyntaxKind.TrueKeyword, "true" },
        { SyntaxKind.FalseKeyword, "false" },
        { SyntaxKind.AmpersandAmpersandToken, "&&" },
        { SyntaxKind.PipePipeToken, "||" },
        { SyntaxKind.BangToken, "!" },

        { SyntaxKind.Identifier, "a" },
        { SyntaxKind.Identifier, "ab" },
        { SyntaxKind.Identifier, "abc" },
        { SyntaxKind.WhitespaceToken, " " },
        { SyntaxKind.WhitespaceToken, "\n" },
        { SyntaxKind.WhitespaceToken, "\r\n" },
        { SyntaxKind.WhitespaceToken, "\t" },
        { SyntaxKind.LongToken, "1" },
        { SyntaxKind.LongToken, "12" },
        { SyntaxKind.LongToken, "123" },
    };
  }

}
