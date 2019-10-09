package mx.sugus.yang0.analysis.syntax;

import java.util.ArrayList;
import java.util.List;

public class Diagnostics {

  private final List<String> errors = new ArrayList<>();

  public Diagnostics() {}

  public void addError(SyntaxToken token, String error) {
    errors.add(String.format("At: %s, %s", token.getPosition(), error));
  }

  public List<String> getErrors() {
    return errors;
  }

  public void addError(int position, String fmt, Object... args) {
    errors.add(String.format("At: %s, %s", position, String.format(fmt, args)));
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }
}
