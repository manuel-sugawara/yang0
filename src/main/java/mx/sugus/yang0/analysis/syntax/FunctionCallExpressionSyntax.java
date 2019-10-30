package mx.sugus.yang0.analysis.syntax;

import java.util.List;
import mx.sugus.yang0.analysis.text.TextSpan;

public class FunctionCallExpressionSyntax implements ExpressionSyntax {
  private final SyntaxToken identifier;
  private final SyntaxToken start;
  private final List<ExpressionSyntax> arguments;
  private final SyntaxToken end;

  public FunctionCallExpressionSyntax(
      SyntaxToken identifier,
      SyntaxToken start,
      List<ExpressionSyntax> arguments,
      SyntaxToken end) {
    this.identifier = identifier;
    this.start = start;
    this.arguments = arguments;
    this.end = end;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.FunctionCallExpression;
  }

  @Override
  public TextSpan getSpan() {
    return TextSpan.from(identifier.getSpan(), end.getSpan());
  }

  @Override
  public String toString() {
    var buf = new StringBuilder("(");
    buf.append(identifier.getSrc());

    for (var arg : arguments) {
      buf.append(" ").append(arg.toString());
    }
    buf.append(")");
    return buf.toString();
  }
}
