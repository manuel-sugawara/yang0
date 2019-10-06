package mx.sugus.yang0;

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
  LongExpression,
  ParentExpression,
  UnaryExpression,
}
