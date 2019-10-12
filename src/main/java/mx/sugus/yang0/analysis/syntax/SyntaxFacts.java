package mx.sugus.yang0.analysis.syntax;

public class SyntaxFacts {

  public static int getUnaryOperatorPriority(SyntaxToken token) {
    switch (token.getKind()) {
      case PlusToken:
      case MinusToken:
      case BangToken:
        return 5;

      default:
        return 0;
    }
  }


  public static int getBinaryOperatorPriority(SyntaxToken token) {
    switch (token.getKind()) {
      case AmpersandAmpersandToken:
        return 4;

      case PipePipeToken:
        return 3;

      case StartToken:
      case SlashToken:
        return 2;

      case PlusToken:
      case MinusToken:
        return 1;


      default:
        return 0;
    }
  }

}
