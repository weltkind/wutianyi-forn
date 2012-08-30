package com.wutianyi.study.jvm;

public class A
{

    private int i = 1;
    private String a;
    private String b;
    private final int j  = 1;

    public A(String a, String b)
    {
        this.a = a;
        this.b = b;
    }

    public A()
    {

    }

    public int getI()
    {
        return i;
    }

    public void setI(int i)
    {
        this.i = i;
    }

    public String getA()
    {
        return a;
    }

    public void setA(String a)
    {
        this.a = a;
    }

    public String getB()
    {
        return b;
    }

    public void setB(String b)
    {
        this.b = b;
    }

    public String join()
    {
        return a + b;
    }
}
