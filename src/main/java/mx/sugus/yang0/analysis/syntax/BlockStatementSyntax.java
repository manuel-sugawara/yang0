package mx.sugus.yang0.analysis.syntax;

import java.util.List;
import mx.sugus.yang0.analysis.text.TextSpan;

public class BlockStatementSyntax implements StatementSyntax {

  private final SyntaxToken start;
  private final SyntaxToken end;
  private final List<StatementSyntax> statements;

  public BlockStatementSyntax(
      SyntaxToken start, SyntaxToken end, List<StatementSyntax> statements) {
    this.start = start;
    this.end = end;
    this.statements = statements;
  }

  public List<StatementSyntax> getStatements() {
    return statements;
  }

  public SyntaxToken getStart() {
    return start;
  }

  public SyntaxToken getEnd() {
    return end;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.BlockStatement;
  }

  @Override
  public TextSpan getSpan() {
    return TextSpan.from(start.getSpan(), end.getSpan());
  }
}
