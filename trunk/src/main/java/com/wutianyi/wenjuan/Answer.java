package com.wutianyi.wenjuan;

public class Answer
{

    private int id;
    private String description;

    /**
     * 如果是1就表示other，需要提供输入框供用户填写 其他的值则不用
     */
    private int type;

    public Answer()
    {
    }

    public Answer(int id, String description, int type)
    {
        this.id = id;
        this.description = description;
        this.type = type;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}
