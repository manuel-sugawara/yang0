package mx.sugus.yang0.analysis.binding;

public enum BoundNodeKind {
  // Expressions
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
  WhileStatement,
  BreakStatement,
  ContinueStatement,
}
