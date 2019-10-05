package mx.sugus.yang0;

public enum SyntaxKind {
  // Misc
  Eof,
  ErrorToken,

  // WhitespaceToken
  WhitespaceToken,

  // Literals
  LongToken,
  DoubleToken,

  // Operators
  PlusToken,
  MinusToken,
  SlashToken,
  StartToken,

  // Syntax
  BinaryExpression,
  LongExpression,
  OpenParen,
  CloseParen,
  ParentExpression,
}
