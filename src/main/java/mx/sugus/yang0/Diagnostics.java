package mx.sugus.yang0;

import java.util.ArrayList;
import java.util.List;

public class Diagnostics {

  private final List<String> errors = new ArrayList<>();

  public Diagnostics() {}

  public void addError(Token token, String error) {
    errors.add(String.format("At: %s, %s", token.getPosition(), error));
  }

  public void addError(int position, String error) {
    errors.add(error);
  }
}
