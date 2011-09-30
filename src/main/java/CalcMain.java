import java.io.DataInputStream;

import antlr.CommonAST;
import antlr.RecognitionException;
import antlr.TokenStreamException;

public class CalcMain
{
    public static void main(String[] args) throws RecognitionException, TokenStreamException
    {
        CalcLexer lexer = new CalcLexer(new DataInputStream(System.in));
        CalcParser parser = new CalcParser(lexer);
        parser.expr();
        CommonAST t = (CommonAST) parser.getAST();

        System.out.println(t.toStringList());
        CalcTreeWalker walker = new CalcTreeWalker();

        int r = walker.expr(t);
        System.out.println("value is " + r);
    }
}
