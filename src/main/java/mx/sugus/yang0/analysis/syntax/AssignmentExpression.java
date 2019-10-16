package mx.sugus.yang0.analysis.syntax;

public class AssignmentExpression implements Expression {

  private final SyntaxToken identifier;
  private final SyntaxToken operator;
  private final Expression initializer;

  public AssignmentExpression(SyntaxToken identifier, SyntaxToken operator, Expression initializer) {
    this.identifier = identifier;
    this.operator = operator;
    this.initializer = initializer;
  }

  public SyntaxToken getIdentifier() {
    return identifier;
  }

  public SyntaxToken getOperator() {
    return operator;
  }

  public Expression getInitializer() {
    return initializer;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.AssignmentExpression;
  }

  public String toString() {
    return "(= " + identifier.getSrc() + " " + initializer + ")";
  }
}
