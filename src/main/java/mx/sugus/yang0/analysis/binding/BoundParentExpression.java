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

  public SyntaxToken getStart() {
    return start;
  }

  public BoundExpression getExpression() {
    return expression;
  }

  public SyntaxToken getEnd() {
    return end;
  }

  @Override
  public Type getType() {
    return expression.getType();
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.ParentExpression;
  }
}
