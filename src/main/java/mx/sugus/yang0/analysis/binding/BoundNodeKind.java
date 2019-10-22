package mx.sugus.yang0.analysis.binding;

public enum BoundNodeKind {
  LiteralExpression,
  ParentExpression,
  UnaryExpression,
  BinaryExpression,
  AssignmentExpression,
  VariableExpression,

  // Statements
  ExpressionStatement,
  BlockStatement,
  DeclareStatement,
  IfStatement,
}
