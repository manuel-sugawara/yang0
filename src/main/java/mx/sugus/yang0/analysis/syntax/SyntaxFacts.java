package mx.sugus.yang0.analysis.syntax;

public class SyntaxFacts {

  public static int getUnaryOperatorPriority(SyntaxToken token) {
    switch (token.getKind()) {
      case PlusToken:
      case MinusToken:
      case BangToken:
        return 10;

      default:
        return 0;
    }
  }


  public static int getBinaryOperatorPriority(SyntaxToken token) {
    switch (token.getKind()) {

      case StartToken:
      case SlashToken:
        return 8;

      case PlusToken:
      case MinusToken:
        return 7;


      case LessThanToken:
      case LessThanEqualsToken:
      case GraterThanToken:
      case GraterThanEqualsToken:
        return 6;

      case EqualsEqualsToken:
      case BangEqualsToken:
        return 5;

      case AmpersandAmpersandToken:
        return 4;

      case PipePipeToken:
        return 3;

      default:
        return 0;
    }
  }

}
