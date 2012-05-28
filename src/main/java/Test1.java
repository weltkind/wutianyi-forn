import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Test1
{

    private static A a = Test2.getA();

    private static B b;

    public static void setB(B b)
    {
        Test1.b = b;
    }

    public static void testB()
    {
        System.out.println(b.toString());
    }
    
    public static void main(String[] args) throws IOException
    {
        List<String> contents = FileUtils.readLines(new File("jquery.easing.1.3.js"), "utf-8");
        for(String content : contents)
        {
            System.out.println(content);
        }
    }
    
}

class B
{

    public B()
    {
        System.out.println("init B.");
    }

    public String toString()
    {
        return "I am B.";
    }
}
