package mx.sugus.yang0.analysis.syntax;

import java.util.ArrayList;
import java.util.List;

public class Diagnostics {

  private final List<String> errors = new ArrayList<>();

  public Diagnostics() {}
  
  public List<String> getErrors() {
    return errors;
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  public void reportInvalidNumber(int start, String text, Class type) {
    addError(start, "Invalid number literal '%s' of type '%s'", text, type);
  }

  public void reportUnexpectedCharacter(int start, char character) {
    addError(start, "Unexpected char '%s' found", character);
  }

  public void reportUnexpectedToken(int start, SyntaxToken token, SyntaxKind kind) {
    addError(start, "Unexpected token, got: '%s', expecting: '%s'", token, kind);
  }

  public void reportExpectingPrimaryExpression(int start, SyntaxToken token) {
    addError(start, "Unexpected input, got: '%s', expecting primary expression", token);
  }

  public void reportUnaryOperatorNotFound(SyntaxToken token, Class type) {
    addError(
        token.getPosition(), "Cannot find unary operator '%s' for type '%s'", token.getSrc(), type);
  }

  public void reportBinaryOperatorNotFound(SyntaxToken token, Class leftType, Class rightType) {
    addError(
        token.getPosition(),
        "Cannot find binary operator '%s' for types '%s' and '%s'",
        token.getSrc(),
        leftType,
        rightType);
  }

  private void addError(int position, String fmt, Object... args) {
    errors.add(String.format("At: %s, %s", position, String.format(fmt, args)));
  }
}
