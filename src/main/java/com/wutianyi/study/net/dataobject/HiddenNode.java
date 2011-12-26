package com.wutianyi.study.net.dataobject;

public class HiddenNode
{
    private int id;
    private String createKey;

    public HiddenNode(String _createKey)
    {
        this.createKey = _createKey;
    }

    public HiddenNode()
    {

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCreateKey()
    {
        return createKey;
    }

    public void setCreateKey(String createKey)
    {
        this.createKey = createKey;
    }

}
