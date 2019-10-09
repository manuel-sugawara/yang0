package mx.sugus.yang0.analysis;

import mx.sugus.yang0.analysis.binding.BoundExpression;
import mx.sugus.yang0.analysis.syntax.Diagnostics;

public class Compilation {
private final Diagnostics diagnostics;
private final BoundExpression expression;

  public Compilation(Diagnostics diagnostics,
      BoundExpression expression) {
    this.diagnostics = diagnostics;
    this.expression = expression;
  }

  public Diagnostics getDiagnostics() {
    return diagnostics;
  }

  public BoundExpression getExpression() {
    return expression;
  }

}
