package com.wutianyi.utils;

public class Pair<T, E>
{
    private T first;
    private E second;

    public Pair(T _first, E _second)
    {
        this.first = _first;
        this.second = _second;
    }

    public Pair()
    {

    }

    public T getFirst()
    {
        return first;
    }

    public void setFirst(T first)
    {
        this.first = first;
    }

    public E getSecond()
    {
        return second;
    }

    public void setSecond(E second)
    {
        this.second = second;
    }

}