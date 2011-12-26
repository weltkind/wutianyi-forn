package com.wutianyi.study.net.dataobject;

public class WordHidden
{
    private int id;
    private int fromId;
    private int toId;
    private float strength;

    public WordHidden()
    {

    }

    public WordHidden(int _fromId, int _toId, float _strength)
    {
        this.fromId = _fromId;
        this.toId = _toId;
        this.strength = _strength;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getFromId()
    {
        return fromId;
    }

    public void setFromId(int fromId)
    {
        this.fromId = fromId;
    }

    public int getToId()
    {
        return toId;
    }

    public void setToId(int toId)
    {
        this.toId = toId;
    }

    public float getStrength()
    {
        return strength;
    }

    public void setStrength(float strength)
    {
        this.strength = strength;
    }

}
