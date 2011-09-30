class MyParser extends Parser;
prog:	stat;
stat:	expr
		| NEWLINE;
expr:	multExpr (OP multExpr)*;
multExpr:	atom(OP_1 atom)*;
atom:	L expr R | INT | ID;

class MyLexer extends Lexer;
ID:	('a'..'z' |'A'..'Z');
INT:	('0'..'9')+;
OP:	('+' | '-');
OP_1:	('*' | '/');
L:	'(';
R:	')';
NEWLINE:	'\r''\n';
WS:	(' '|'\t'|'\n'|'\r')+;