package mx.sugus.yang0.analysis.syntax;

public enum SyntaxKind {
  // Misc
  EofToken,
  ErrorToken,

  // Whitespace
  WhitespaceToken,

  // Literals
  LongToken,

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

  // Keywords
  TrueKeyword,
  FalseKeyword,
  VarKeyword,
  IfKeyword,
  ElseKeyword,
  WhileKeyword,

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
