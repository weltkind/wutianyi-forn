class LexPascal extends Lexer;
WS:	(' '
	|	'\t'
	|	'\n'
	|	'\r')+
		{$setType(Token.SKIP);};
protected
INT:	('0'..'9')+;
protected
REAL:	INT '.' INT;
RANGE:	"..";
RANGE_OR_INT:	(INT "..")=> INT{$setType(INT);}
	|(INT '.')=> REAL{$setType(REAL);}
	| INT;
protected
EXPR:	INT('.' (INT)?)?;
protected
VARIABLE:	'A'..'Z' ('A'..'Z'| ' ' | '0'..'9')*
	{/*strip space from end */}
;
protected
DO_HEADER options{ignore = WS;}:	"DO" INT VARIABLE '=' EXPR ',';
DO_OR_VAR:	(DO_HEADER)=>"DO" {$setType(DO_HEADER);}|VARIABLE {$setType(VARIABLE);};

DEFINE:	{getColumn() == 1}? "#define" ID;

BEGIN_TABLE: '['{this.inTable = true;};
ROW_SEP:	{this.inTable}? "------";
END_TABLE: ']'{this.inTable =false;};




class Pascal extends Parser;
prog:	INT(RANGE INT {System.out.println("INT.. INT");} | EOF{System.out.println("plain old INt");})
	| REAL{System.out.println("Token REAL");};