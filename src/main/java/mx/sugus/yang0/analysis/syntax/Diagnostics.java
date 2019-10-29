package mx.sugus.yang0.analysis.syntax;

import java.util.ArrayList;
import java.util.List;
import mx.sugus.yang0.analysis.binding.Type;
import mx.sugus.yang0.analysis.text.TextSource;
import mx.sugus.yang0.analysis.text.TextSpan;

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

  public void reportInvalidNumber(SyntaxToken token, Class type) {
    addError(token.getSpan(), "Invalid number literal '%s' of type '%s'", token.getSrc(), type);
  }

  public void reportUnexpectedCharacter(TextSpan span, String text) {
    addError(span, "Unexpected char '%s' found", text);
  }

  public void reportUnexpectedToken(SyntaxToken token, SyntaxKind kind) {
    addError(token.getSpan(), "Unexpected token, got: '%s', expecting: '%s'", token, kind);
  }

  public void reportExpectingPrimaryExpression(SyntaxToken token) {
    addError(token.getSpan(), "Unexpected input, got: '%s', expecting primary expression", token);
  }

  public void reportUnaryOperatorNotFound(SyntaxToken token, Type type) {
    addError(
        token.getSpan(), "Cannot find unary operator '%s' for type '%s'", token.getSrc(), type);
  }

  public void reportBinaryOperatorNotFound(SyntaxToken token, Type leftType, Type rightType) {
    addError(
        token.getSpan(),
        "Cannot find binary operator '%s' for types '%s' and '%s'",
        token.getSrc(),
        leftType,
        rightType);
  }

  public void reportCannotConvert(SyntaxToken token, Type fromType, Type toType) {
    addError(token.getSpan(), "Cannot convert type '%s' to '%s'", fromType, toType);
  }

  private void addError(TextSpan span, String fmt, Object... args) {
    var diagnostic = new Diagnostic(Level.Error, source, span, fmt, args);
    diagnostics.add(diagnostic);
  }

  public void reportVariableNotFound(SyntaxToken identifier) {
    addError(
        identifier.getSpan(), "Variable with name '%s' has not been declared", identifier.getSrc());
  }

  public void reportVariableAlreadyDeclared(SyntaxToken identifier) {
    addError(
        identifier.getSpan(),
        "Variable with name '%s' has been already declared",
        identifier.getSrc());
  }

  public void reportExpectingBooleanExpression(TextSpan span, Type type) {
    addError(span, "Expecting boolean expression, got expression of type: '%s'", type);
  }

  public void reportUnterminatedStringLiteral(TextSpan span) {
    addError(span, "Unterminated literal String, reached EOF while looking for \"");
  }

  static enum Level {
    Error,
    Warn
  }
}
