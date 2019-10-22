package mx.sugus.yang0.analysis.binding;

import java.util.List;
import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundBlockStatement implements BoundStatement {
  private final SyntaxToken start;
  private final SyntaxToken end;
  private final List<BoundStatement> statements;

  public BoundBlockStatement(SyntaxToken start, SyntaxToken end,
      List<BoundStatement> statements) {
    this.start = start;
    this.end = end;
    this.statements = statements;
  }

  public List<BoundStatement> getStatements() {
    return statements;
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.BlockStatement;
  }
}
