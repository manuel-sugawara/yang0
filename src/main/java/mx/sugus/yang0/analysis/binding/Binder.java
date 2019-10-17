package mx.sugus.yang0.analysis.binding;

import static mx.sugus.yang0.analysis.binding.BoundBinaryOperatorKind.bindBinaryOperatorKind;
import static mx.sugus.yang0.analysis.binding.BoundUnaryOperatorKind.bindUnaryOperatorKind;

import mx.sugus.yang0.analysis.syntax.AssignmentExpression;
import mx.sugus.yang0.analysis.syntax.BinaryExpression;
import mx.sugus.yang0.analysis.syntax.Diagnostics;
import mx.sugus.yang0.analysis.syntax.Expression;
import mx.sugus.yang0.analysis.syntax.LiteralExpression;
import mx.sugus.yang0.analysis.syntax.ParentExpression;
import mx.sugus.yang0.analysis.syntax.UnaryExpression;
import mx.sugus.yang0.analysis.syntax.VariableExpression;

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

  public BoundExpression bindExpression(Expression syntax) {
    var kind = syntax.getKind();
    switch (kind) {
      case LiteralExpression:
        return bindLiteralExpression((LiteralExpression) syntax);
      case UnaryExpression:
        return bindUnaryExpression((UnaryExpression) syntax);
      case BinaryExpression:
        return bindBinaryExpression((BinaryExpression) syntax);
      case ParentExpression:
        return bindParentExpression((ParentExpression) syntax);
      case AssignmentExpression:
        return bindAssignmentExpression((AssignmentExpression) syntax);
      case VariableExpression:
        return bindVariableExpression((VariableExpression) syntax);
      default:
        throw new IllegalStateException("Unknown expression kind: " + kind);
    }
  }

  private BoundExpression bindVariableExpression(VariableExpression syntax) {
    var variable = scope.getDeclared(syntax.getIdentifier());
    if (variable == null) {
      diagnostics.reportVariableNotFound(syntax.getIdentifier());
      variable = new VariableSymbol(syntax.getIdentifier(), Class.class);
    }
    return new BoundVariableExpression(variable);
  }

  private BoundExpression bindAssignmentExpression(AssignmentExpression syntax) {
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

  private BoundExpression bindParentExpression(ParentExpression syntax) {
    var boundExpression = bindExpression(syntax.getExpression());
    return new BoundParentExpression(syntax.getStart(), boundExpression, syntax.getEnd());
  }

  private BoundExpression bindLiteralExpression(LiteralExpression syntax) {
    return new BoundLiteralExpression(syntax);
  }

  private BoundExpression bindUnaryExpression(UnaryExpression syntax) {
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

  private BoundExpression bindBinaryExpression(BinaryExpression syntax) {
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
