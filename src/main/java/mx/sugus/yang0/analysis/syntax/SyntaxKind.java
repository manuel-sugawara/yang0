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

  // Misc
  TrueKeyword,
  FalseKeyword,
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
  LessThanEqualsToken,
  LessThanToken,
  GraterThanEqualsToken,
  GraterThanToken,
  BangEqualsToken,
  EqualsEqualsToken,
  EqualsToken,
}
