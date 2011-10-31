package com.wutianyi.study.weight;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main
{

    private AtomicInteger degree;

    private Map<Position, Object> objs;

    public Main(WeightObject... _objs)
    {
        objs = new HashMap<Position, Object>();
        degree = new AtomicInteger();
        if (null != _objs)
        {
            for (WeightObject obj : _objs)
            {
                addObjs(obj);
            }
        }
    }

    public void addObjs(WeightObject obj)
    {
        if (null == obj)
        {
            return;
        }
        addObjs(obj.obj, obj.weight);
    }

    public void addObjs(Object obj, int weight)
    {
        if (null == obj)
        {
            return;
        }
        weight = weight >= 0 ? weight : 0;

        Position p = new Position(degree.get(), degree.addAndGet(weight));
        objs.put(p, obj);
    }

    public Object getObj()
    {
        int position = (int) (Math.random() * degree.get());
        return objs.get(new Position(position));
    }

    public static void main(String[] args)
    {
        Main m = new Main(new WeightObject("wutianyi", 4));
        for (int i = 0; i < 10; i++)
        {
            System.out.println(m.getObj());
        }
        
        for(int i = 0 ; i < 10 ; i ++)
        {
            System.out.println(new Integer(i).hashCode());
        }
    
    }
}

class WeightObject
{
    Object obj;
    int weight;

    WeightObject(Object _obj, int _weight)
    {
        this.obj = _obj;
        this.weight = _weight;
    }

}

class Position implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 5207924378423934175L;
    int begin;
    int end;
    int position;

    Position(int _begin, int _end)
    {
        this.begin = _begin;
        this.end = _end;
    }

    Position(int _position)
    {
        this.position = _position;
    }

    public int hashCode()
    {
        
        return 0;
    }
    
    public boolean equals(Object obj)
    {
        if (obj instanceof Position)
        {
            Position _position = (Position) obj;
            if (_position.begin == begin && _position.end == end)
            {
                return true;
            }
            if (_position.position >= begin && _position.position < end)
            {
                return true;
            }
        }
        return false;
    }

}
