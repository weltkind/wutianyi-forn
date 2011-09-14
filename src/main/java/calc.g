class CalcParser extends Parser;
options{
	buildAST = true;	
}
atom:	INT;
mexpr:	atom(STAR^ atom)*;
expr:	mexpr(PLUS^ mexpr)*SEMI!;

class CalcLexer extends Lexer;
WS:	(' ' | '\t'	| '\n'	|	'\r'){_ttype=Token.SKIP;};
LPAREN:	'(';
RPAREN:	')';
STAR:	'*';
PLUS:	'+';
SEMI:	';';
INT:	('0'..'9')+;

class CalcTreeWalker extends TreeParser;
expr returns[int r]{
	int a, b;
	r = 0;
}
:	#(PLUS a=expr b=expr){r = a + b;} 
	| #(STAR a=expr b=expr){r = a * b;} 
	| i:INT{r = Integer.parseInt(i.getText());};