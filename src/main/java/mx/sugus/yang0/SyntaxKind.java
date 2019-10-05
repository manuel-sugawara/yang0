package mx.sugus.yang0;

public enum  SyntaxKind {
  // Misc
  Eof,
  Error,

  // Whitespace
  Whitespace,

  // Literals
  LongLiteral,
  DoubleLiteral,

  // Operators
  PlusToken,
  MinusToken,
  SlashToken,
  StartToken,
  BinaryExpression,
  LongExpression, OpenParen, CloseParen,
}
