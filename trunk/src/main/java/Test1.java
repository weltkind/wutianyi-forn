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
    
    public static void main(String[] args)
    {
        Test1.setB(new B());
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
