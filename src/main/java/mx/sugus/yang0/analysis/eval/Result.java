package mx.sugus.yang0.analysis.eval;

public class Result {
  private static final Object NoneValue = new Object();

  public static final Result None = from(NoneValue);
  public static final Result False = from(Boolean.FALSE);
  public static final Result Continue = from(ResultState.Continue);
  public static final Result Break = from(ResultState.Break);

  private final Object value;
  private final ResultState state;

  public Result(Object value, ResultState state) {
    this.value = value;
    this.state = state;
  }

  public static Result from(Object value) {
    return new Result(value, ResultState.Pass);
  }

  public static Result from(ResultState state) {
    return new Result(NoneValue, state);
  }

  public Object getValue() {
    return value;
  }

  public ResultState getState() {
    return state;
  }

  @Override
  public String toString() {
    if (state == ResultState.Pass) {
      return value.toString();
    }
    return "[" + state + "," + value + "]";
  }

}
