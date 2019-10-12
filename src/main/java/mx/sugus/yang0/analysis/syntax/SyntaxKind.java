package mx.sugus.yang0.analysis.syntax;

public enum SyntaxKind {
  // Misc
  EofToken,
  ErrorToken,

  // Whitespace
  WhitespaceToken,

  // Literals
  LongToken,

  // Operators
  PlusToken,
  MinusToken,
  SlashToken,
  StartToken,
  OpenParenToken,
  CloseParenToken,

  // Syntax
  BinaryExpression,
  ParentExpression,
  UnaryExpression,
  LiteralExpression,
  TrueKeyword,
  FalseKeyword,
  Identifier,
  AmpersandAmpersandToken,
  PipePipeToken,
  BangToken,
}
