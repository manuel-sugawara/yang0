package mx.sugus.yang0.analysis.syntax;

public enum SyntaxKind {
  // Misc
  EofToken,
  ErrorToken,

  // Whitespace
  WhitespaceToken,

  // Literals
  LongToken,
  StringLiteral,

  // Syntax
  BinaryExpression,
  ParentExpression,
  UnaryExpression,
  LiteralExpression,
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

  // Keywords
  TrueKeyword,
  FalseKeyword,
  VarKeyword,
  IfKeyword,
  ElseKeyword,
  WhileKeyword,
  BreakKeyword,
  ContinueKeyword,

  // Identifiers
  Identifier,

  // Operators
  PlusToken,
  MinusToken,
  SlashToken,
  StartToken,
  AmpersandAmpersandToken,
  PipePipeToken,
  BangToken,
  PercentToken,
  OpenParenToken,
  CloseParenToken,
  OpenBracketToken,
  CloseBracketToken,
  LessThanEqualsToken,
  LessThanToken,
  GraterThanEqualsToken,
  GraterThanToken,
  BangEqualsToken,
  EqualsEqualsToken,
  EqualsToken,
}
