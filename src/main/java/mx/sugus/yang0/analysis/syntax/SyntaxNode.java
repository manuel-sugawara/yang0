package mx.sugus.yang0.analysis.syntax;

import mx.sugus.yang0.analysis.text.TextSpan;

public interface SyntaxNode {

  SyntaxKind getKind();

  TextSpan getSpan();
}
