package mx.sugus.yang0.analysis.syntax;

import java.util.ArrayList;
import java.util.List;

public class Diagnostics {

  private final List<String> errors = new ArrayList<>();

  public Diagnostics() {}

  public void addError(SyntaxToken token, String error) {
    errors.add(String.format("At: %s, %s", token.getPosition(), error));
  }

  public void addError(int position, String error) {
    errors.add(String.format("At: %s, %s", position, error));
  }
}
