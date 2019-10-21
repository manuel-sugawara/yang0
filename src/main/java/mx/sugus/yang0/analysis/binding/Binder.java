package mx.sugus.yang0.analysis.binding;

import static mx.sugus.yang0.analysis.binding.BoundBinaryOperatorKind.bindBinaryOperatorKind;
import static mx.sugus.yang0.analysis.binding.BoundUnaryOperatorKind.bindUnaryOperatorKind;

import mx.sugus.yang0.analysis.syntax.AssignmentExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.BinaryExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.Diagnostics;
import mx.sugus.yang0.analysis.syntax.ExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.LiteralExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.ParentExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.UnaryExpressionSyntax;
import mx.sugus.yang0.analysis.syntax.VariableExpressionSyntax;

public class Binder {

  private final Diagnostics diagnostics;
  private final BoundScope scope;

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
      variable = new VariableSymbol(syntax.getIdentifier(), Class.class);
    }
    return new BoundVariableExpression(variable);
  }

  private BoundExpression bindAssignmentExpression(AssignmentExpressionSyntax syntax) {
    var initializer = bindExpression(syntax.getInitializer());
    var variable = scope.getDeclared(syntax.getIdentifier());
    if (variable != null) {
      if (variable.getType() != initializer.getType()) {
        diagnostics.reportCannotConvert(
            syntax.getOperator(), variable.getType(), initializer.getType());
        return initializer;
      }
    } else {
      variable = scope.declare(syntax.getIdentifier(), initializer.getType());
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
