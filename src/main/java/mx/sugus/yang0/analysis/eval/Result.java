package mx.sugus.yang0.analysis.eval;

public class Result {

  public static final Result False = from(Boolean.FALSE);

  private final Object value;
  private final EvalState state;

  public Result(Object value, EvalState state) {
    this.value = value;
    this.state = state;
  }

  public static Result from(Object value) {
    return new Result(value, EvalState.Pass);
  }

  public Object getValue() {
    return value;
  }

  public EvalState getState() {
    return state;
  }

  @Override
  public String toString() {
    if (state == EvalState.Pass) {
      return value.toString();
    }
    return "[" + state + "," + value + "]";
  }

}
