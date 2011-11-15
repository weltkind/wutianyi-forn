package com.wutianyi.study.weight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main
{

    private int degree = 0;

    private int hashCode = 0;

    private Map<Position, Object> objs;

    private Map<Integer, Integer> hashCodeMap = new HashMap<Integer, Integer>();

    // 轮询
    private List<Object> listObjects;
    private AtomicInteger index;

    public Main(WeightObject... _objs)
    {
        objs = new HashMap<Position, Object>();
        listObjects = new ArrayList<Object>();
        index = new AtomicInteger();
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
        synchronized (this)
        {
            if (null == obj)
            {
                return;
            }
            weight = weight >= 0 ? weight : 0;
            int begin = degree;
            for (int i = 0; i < weight; i++)
            {
                hashCodeMap.put(degree, hashCode);
                degree++;
            }
            Position p = new Position(begin, degree, hashCode);
            hashCode++;
            objs.put(p, obj);
            listObjects.add(obj);
        }
    }

    public Object getObj()
    {
        int position = (int) (Math.random() * degree);
        int hashCode = hashCodeMap.get(position);
        return objs.get(new Position(position, hashCode));
    }

    public Object getObjRoll()
    {
        int _index = index.getAndIncrement();
        _index = _index % hashCode;
        if (index.get() > 100000)
        {
            index.set(0);
        }
        return listObjects.get(_index);
    }

    public static void main(String[] args)
    {
        Main m = new Main(new WeightObject("wutianyi", 4));
        m.addObjs(1, 2);
        m.addObjs(2, 0);
        m.addObjs(3, 3);
        for (int i = 0; i < 10; i++)
        {
            System.out.println(m.getObj());
        }
        System.out.println("-------");
        for(int i = 0; i < 10; i ++)
        {
            System.out.println(m.getObjRoll());
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
    int hashCode;

    Position(int _begin, int _end, int _hashCode)
    {
        this.begin = _begin;
        this.end = _end;
        this.hashCode = _hashCode;
    }

    Position(int _position, int _hashCode)
    {
        this.position = _position;
        this.hashCode = _hashCode;
    }

    public int hashCode()
    {

        return hashCode;
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
            if (position >= _position.begin && position < _position.end)
            {
                return true;
            }
        }

        return false;
    }

}
