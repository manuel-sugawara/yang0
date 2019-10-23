package mx.sugus.yang0.analysis.binding;

import static mx.sugus.yang0.analysis.binding.BoundBinaryOperatorKind.bindBinaryOperatorKind;
import static mx.sugus.yang0.analysis.binding.BoundUnaryOperatorKind.bindUnaryOperatorKind;

import java.util.stream.Collectors;
import mx.sugus.yang0.analysis.syntax.AssignmentExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.BinaryExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.BlockStatementSyntax;
import mx.sugus.yang0.analysis.syntax.DeclareStatementSyntax;
import mx.sugus.yang0.analysis.syntax.Diagnostics;
import mx.sugus.yang0.analysis.syntax.ExpressionStatementSyntax;
import mx.sugus.yang0.analysis.syntax.ExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.IfStatementSynax;
import mx.sugus.yang0.analysis.syntax.LiteralExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.ParentExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.StatementSyntax;
import mx.sugus.yang0.analysis.syntax.UnaryExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.VariableExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.WhileStatementSyntax;

public class Binder {

  private final Diagnostics diagnostics;
  private BoundScope scope;

  public Binder(BoundScope scope, Diagnostics diagnostics) {
    this.scope = scope;
    this.diagnostics = diagnostics;
  }

  public Diagnostics getDiagnostics() {
    return diagnostics;
  }

  public BoundScope getScope() {
    return scope;
  }

  public BoundStatement bindStatement(StatementSyntax syntax) {
    var kind = syntax.getKind();
    switch (kind) {
      case ExpressionStatement:
        return bindExpressionStatement((ExpressionStatementSyntax) syntax);
      case BlockStatement:
        return bindBlockStatement((BlockStatementSyntax) syntax);
      case DeclareStatement:
        return bindDeclareStatement((DeclareStatementSyntax) syntax);
      case IfStatement:
        return bindIfStatement((IfStatementSynax) syntax);
      case WhileStatement:
        return bindWhileStatement((WhileStatementSyntax) syntax);
      default:
        throw new IllegalStateException("unknown statement kind: " + kind);
    }
  }

  private BoundStatement bindWhileStatement(WhileStatementSyntax syntax) {
    var condition =syntax.getCondition();
    var boundCondition = bindExpression(condition);
    if (boundCondition.getType() != Type.Boolean) {
      diagnostics.reportExpectingBooleanExpression(condition.getSpan(), boundCondition.getType());
    }
    var body = bindStatement(syntax.getBody());

    return new BoundWhileStatement(syntax.getWhileToken(), boundCondition, body);
  }

  private BoundStatement bindIfStatement(IfStatementSynax syntax) {
    var condition =syntax.getCondition();
    var boundCondition = bindExpression(condition);
    if (boundCondition.getType() != Type.Boolean) {
      diagnostics.reportExpectingBooleanExpression(condition.getSpan(), boundCondition.getType());
    }
    var ifBody = bindStatement(syntax.getBody());
    if (syntax.getElseKeyword() != null) {
      var elseBody = bindStatement(syntax.getElseBody());
      return new BoundIfStatement(
          syntax.getIfKeyword(), boundCondition, ifBody, syntax.getElseKeyword(), elseBody);
    }
    return new BoundIfStatement(syntax.getIfKeyword(), boundCondition, ifBody);
  }

  private BoundStatement bindDeclareStatement(DeclareStatementSyntax syntax) {
    var variable = scope.getLocallyDeclared(syntax.getIdentifier());
    var initExpression = bindExpression(syntax.getInitExpression());
    if (variable != null) {
      diagnostics.reportVariableAlreadyDeclared(syntax.getIdentifier());
    } else {
      variable = scope.declare(syntax.getIdentifier(), initExpression.getType());
    }
    return new BoundDeclareStatement(
        variable,
        syntax.getVarKeyword(),
        syntax.getIdentifier(),
        syntax.getEqualsToken(),
        initExpression);
  }

  private BoundStatement bindBlockStatement(BlockStatementSyntax syntax) {
    scope = scope.push();
    var statements =
        syntax.getStatements().stream().map(this::bindStatement).collect(Collectors.toList());
    scope = scope.pop();
    return new BoundBlockStatement(syntax.getStart(), syntax.getEnd(), statements);
  }

  private BoundStatement bindExpressionStatement(ExpressionStatementSyntax syntax) {
    var expression = bindExpression(syntax.getExpression());
    return new BoundExpressionStatement(expression);
  }

  public BoundExpression bindExpression(ExpressionSyntax syntax) {
    var kind = syntax.getKind();
    switch (kind) {
      case LiteralExpression:
        return bindLiteralExpression((LiteralExpressionSyntax) syntax);
      case UnaryExpression:
        return bindUnaryExpression((UnaryExpressionSyntax) syntax);
      case BinaryExpression:
        return bindBinaryExpression((BinaryExpressionSyntax) syntax);
      case ParentExpression:
        return bindParentExpression((ParentExpressionSyntax) syntax);
      case AssignmentExpression:
        return bindAssignmentExpression((AssignmentExpressionSyntax) syntax);
      case VariableExpression:
        return bindVariableExpression((VariableExpressionSyntax) syntax);
      default:
        throw new IllegalStateException("Unknown expression kind: " + kind);
    }
  }

  private BoundExpression bindVariableExpression(VariableExpressionSyntax syntax) {
    var variable = scope.getDeclared(syntax.getIdentifier());
    if (variable == null) {
      diagnostics.reportVariableNotFound(syntax.getIdentifier());
      variable = new VariableSymbol(syntax.getIdentifier(), Type.None);
    }
    return new BoundVariableExpression(variable);
  }

  private BoundExpression bindAssignmentExpression(AssignmentExpressionSyntax syntax) {
    var initializer = bindExpression(syntax.getInitializer());
    var variable = scope.getDeclared(syntax.getIdentifier());
    if (variable == null) {
      diagnostics.reportVariableNotFound(syntax.getIdentifier());
      return initializer;
    }
    if (variable.getType() != initializer.getType()) {
      diagnostics.reportCannotConvert(
          syntax.getOperator(), variable.getType(), initializer.getType());
      return initializer;
    }
    return new BoundAssignmentExpression(variable, syntax.getOperator(), initializer);
  }

  private BoundExpression bindParentExpression(ParentExpressionSyntax syntax) {
    var boundExpression = bindExpression(syntax.getExpression());
    return new BoundParentExpression(syntax.getStart(), boundExpression, syntax.getEnd());
  }

  private BoundExpression bindLiteralExpression(LiteralExpressionSyntax syntax) {
    return new BoundLiteralExpression(syntax);
  }

  private BoundExpression bindUnaryExpression(UnaryExpressionSyntax syntax) {
    var boundOperand = bindExpression(syntax.getOperand());
    var type = boundOperand.getType();
    var operator = syntax.getOperator();
    var kind = bindUnaryOperatorKind(operator, type);

    if (kind == null) {
      diagnostics.reportUnaryOperatorNotFound(operator, type);
      return boundOperand;
    }
    return new BoundUnaryExpression(operator, kind, boundOperand);
  }

  private BoundExpression bindBinaryExpression(BinaryExpressionSyntax syntax) {
    var boundLeft = bindExpression(syntax.getLeft());
    var boundRight = bindExpression(syntax.getRight());
    var operator = syntax.getOperator();
    var kind = bindBinaryOperatorKind(operator, boundLeft.getType(), boundRight.getType());
    if (kind == null) {
      diagnostics.reportBinaryOperatorNotFound(operator, boundLeft.getType(), boundRight.getType());
      return boundLeft;
    }
    return new BoundBinaryExpression(boundLeft, operator, kind, boundRight);
  }
}
