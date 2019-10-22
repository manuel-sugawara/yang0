package mx.sugus.yang0.analysis.syntax;

import java.lang.annotation.ElementType;
import mx.sugus.yang0.analysis.text.TextSpan;

public class IfStatementSynax implements StatementSyntax {

  private final SyntaxToken ifKeyword;
  private final ExpressionSyntax condition;
  private final StatementSyntax body;
  private final SyntaxToken elseKeyword;
  private final StatementSyntax elseBody;

  public IfStatementSynax(
      SyntaxToken ifKeyword,
      ExpressionSyntax condition,
      StatementSyntax body,
      SyntaxToken elseKeyword,
      StatementSyntax elseBody) {
    this.ifKeyword = ifKeyword;
    this.condition = condition;
    this.body = body;
    this.elseKeyword = elseKeyword;
    this.elseBody = elseBody;
  }

  public IfStatementSynax(
      SyntaxToken ifKeyword, ExpressionSyntax condition, StatementSyntax body) {
    this.ifKeyword = ifKeyword;
    this.condition = condition;
    this.body = body;
    this.elseBody = null;
    this.elseKeyword = null;
  }

  public SyntaxToken getIfKeyword() {
    return ifKeyword;
  }

  public ExpressionSyntax getCondition() {
    return condition;
  }

  public StatementSyntax getBody() {
    return body;
  }

  public SyntaxToken getElseKeyword() {
    return elseKeyword;
  }

  public StatementSyntax getElseBody() {
    return elseBody;
  }

  @Override
  public SyntaxKind getKind() {
    return SyntaxKind.IfStatement;
  }

  @Override
  public TextSpan getSpan() {
    var end = elseKeyword == null ? body.getSpan() : elseBody.getSpan();
    return TextSpan.from(ifKeyword.getSpan(), end);
  }
}
