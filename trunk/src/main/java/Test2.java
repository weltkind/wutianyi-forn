public class Test2
{

    public static A getA()
    {
        Test1.testB();
        return new A();
    }

}

class A
{
    public A()
    {
        System.out.println("init A.");
    }

}
