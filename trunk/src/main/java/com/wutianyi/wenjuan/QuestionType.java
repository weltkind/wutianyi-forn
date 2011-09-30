package com.wutianyi.wenjuan;

public enum QuestionType
{
    CHECK_BOX(1), RADIO(2);

    private int type;

    private QuestionType(int type)
    {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }
}
