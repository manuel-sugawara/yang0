package mx.sugus.yang0.analysis.syntax;

public class AssignmentExpressionSyntax implements ExpressionSyntax {

  private final SyntaxToken identifier;
  private final SyntaxToken operator;
  private final ExpressionSyntax initializer;

  public AssignmentExpressionSyntax(SyntaxToken identifier, SyntaxToken operator, ExpressionSyntax initializer) {
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

  public ExpressionSyntax getInitializer() {
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
