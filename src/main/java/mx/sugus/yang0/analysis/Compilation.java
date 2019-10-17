package mx.sugus.yang0.analysis;

import mx.sugus.yang0.analysis.binding.BoundExpression;
import mx.sugus.yang0.analysis.binding.BoundGlobalScope;
import mx.sugus.yang0.analysis.syntax.Diagnostics;

public class Compilation {
  private final Diagnostics diagnostics;
  private final BoundGlobalScope scope;
  private final BoundExpression expression;

  public Compilation(Diagnostics diagnostics, BoundGlobalScope scope, BoundExpression expression) {
    this.diagnostics = diagnostics;
    this.scope = scope;
    this.expression = expression;
  }

  public Diagnostics getDiagnostics() {
    return diagnostics;
  }

  public BoundExpression getExpression() {
    return expression;
  }

  public BoundGlobalScope getScope() {
    return scope;
  }
}
