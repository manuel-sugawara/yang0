package mx.sugus.yang0.analysis.syntax;

public enum SyntaxKind {
  // Misc
  EofToken,
  ErrorToken,

  // Whitespace
  WhitespaceToken,

  // Literals
  LongToken,
  LiteralToken,

  // Operators
  PlusToken,
  MinusToken,
  SlashToken,
  StartToken,
  OpenParenToken,
  CloseParenToken,

  // Syntax
  BinaryExpression,
  LongExpression,
  ParentExpression,
  UnaryExpression, LiteralExpression, TrueKeyword, FalseKeyword, Identifier,
}
