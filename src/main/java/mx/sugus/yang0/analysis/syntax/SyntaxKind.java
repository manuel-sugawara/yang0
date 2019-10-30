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

  // Expressions
  BinaryExpression,
  ParentExpression,
  UnaryExpression,
  LiteralExpression,
  AssignmentExpression,
  VariableExpression,
  FunctionCallExpression,

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
  CommaToken,
}
