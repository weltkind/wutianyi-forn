class MyParser extends Parser;
idList : (ID)+;//解析规则定义
class MyLexer extends Lexer;
ID:('a'..'z')+;//记号定义

WS:(' ' | '\t' | '\n'{newline();}|'\r')+
{$setType(Token.SKIP);};
INT returns[int v]:('0'..'9')+{v=Integer.valueOf($getText)}
