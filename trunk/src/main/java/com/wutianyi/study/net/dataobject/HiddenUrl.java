package com.wutianyi.study.net.dataobject;

public class HiddenUrl
{
    private int id;
    private int fromId;
    private int toId;
    private float strength;

    public HiddenUrl()
    {

    }

    public HiddenUrl(int _fromId, int _toId, float _strenght)
    {
        this.fromId = _fromId;
        this.toId = _toId;
        this.strength = _strenght;
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
