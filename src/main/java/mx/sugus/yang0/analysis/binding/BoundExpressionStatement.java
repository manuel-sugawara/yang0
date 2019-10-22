package mx.sugus.yang0.analysis.binding;

public class BoundExpressionStatement implements BoundStatement {

  private final BoundExpression expression;

  public BoundExpressionStatement(BoundExpression expression) {
    this.expression = expression;
  }

  public BoundExpression getExpression() {
    return expression;
  }

  @Override
  public BoundNodeKind getKind() {
    return BoundNodeKind.ExpressionStatement;
  }
}
