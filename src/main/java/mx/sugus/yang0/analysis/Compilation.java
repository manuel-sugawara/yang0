package mx.sugus.yang0.analysis;

import mx.sugus.yang0.analysis.eval.EvalState;
import mx.sugus.yang0.analysis.binding.BoundStatement;
import mx.sugus.yang0.analysis.syntax.Diagnostics;

public class Compilation {
  private final Diagnostics diagnostics;
  private final EvalState scope;
  private final BoundStatement statement;

  public Compilation(Diagnostics diagnostics, EvalState scope, BoundStatement statement) {
    this.diagnostics = diagnostics;
    this.scope = scope;
    this.statement = statement;
  }

  public Diagnostics getDiagnostics() {
    return diagnostics;
  }

  public BoundStatement getStatement() {
    return statement;
  }

  public EvalState getScope() {
    return scope;
  }
}
