package mx.sugus.yang0.analysis.syntax;

import java.util.ArrayList;
import java.util.List;
import mx.sugus.yang0.analysis.text.TextSource;

public class Diagnostics {

  private final List<Diagnostic> diagnostics = new ArrayList<>();
  private final TextSource source;

  public Diagnostics(TextSource source) {
    this.source = source;
  }

  public List<Diagnostic> getDiagnostics() {
    return diagnostics;
  }

  public boolean hasErrors() {
    return !diagnostics.isEmpty();
  }

  public void reportInvalidNumber(int position, String text, Class type) {
    addError(position, text.length(), "Invalid number literal '%s' of type '%s'", text, type);
  }

  public void reportUnexpectedCharacter(int position, char character) {
    addError(position, 1, "Unexpected char '%s' found", character);
  }

  public void reportUnexpectedToken(int position, SyntaxToken token, SyntaxKind kind) {
    addError(
        position,
        token.getSrc().length(),
        "Unexpected token, got: '%s', expecting: '%s'",
        token,
        kind);
  }

  public void reportExpectingPrimaryExpression(int position, SyntaxToken token) {
    addError(
        position,
        token.getSrc().length(),
        "Unexpected input, got: '%s', expecting primary expression",
        token);
  }

  public void reportUnaryOperatorNotFound(SyntaxToken token, Class type) {
    addError(
        token.getPosition(),
        token.getSrc().length(),
        "Cannot find unary operator '%s' for type '%s'",
        token.getSrc(),
        type);
  }

  public void reportBinaryOperatorNotFound(SyntaxToken token, Class leftType, Class rightType) {
    addError(
        token.getPosition(),
        token.getSrc().length(),
        "Cannot find binary operator '%s' for types '%s' and '%s'",
        token.getSrc(),
        leftType,
        rightType);
  }

  public void reportCannotConvert(SyntaxToken token, Class fromType, Class toType) {
    addError(
        token.getPosition(),
        token.getSrc().length(),
        "Cannot convert type '%s' to '%s'",
        fromType,
        toType);
  }

  private void addError(int position, int length, String fmt, Object... args) {
    var diagnostic = new Diagnostic(Level.Error, source, position, length, fmt, args);
    diagnostics.add(diagnostic);
  }

  public void reportVariableNotFound(SyntaxToken identifier) {
    addError(
        identifier.getPosition(),
        identifier.getSrc().length(),
        "Variable with name '%s' has not been declared",
        identifier.getSrc());
  }

  public void reportVariableAlreadyDeclared(SyntaxToken identifier) {
    addError(
        identifier.getPosition(),
        identifier.getSrc().length(),
        "Variable with name '%s' has been already declared",
        identifier.getSrc());
  }

  static enum Level {
    Error,
    Warn
  }
}
