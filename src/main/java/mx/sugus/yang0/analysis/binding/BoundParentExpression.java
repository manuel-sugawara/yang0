package mx.sugus.yang0.analysis.binding;

import mx.sugus.yang0.analysis.syntax.SyntaxToken;

public class BoundParentExpression implements BoundExpression {

  private final SyntaxToken start;
  private final BoundExpression expression;
  private final SyntaxToken end;

  public BoundParentExpression(SyntaxToken start, BoundExpression expression, SyntaxToken end) {
    this.start = start;
    this.expression = expression;
    this.end = end;
  }

  @Override
  public Class getType() {
    return null;
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.ParentExpression;
  }
}
