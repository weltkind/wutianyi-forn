package com.wutianyi.wenjuan;

public class Question
{
    private int id;
    private String description;
    private QuestionType type;

    private Answer[] answers;

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

    public QuestionType getType()
    {
        return type;
    }

    public void setType(QuestionType type)
    {
        this.type = type;
    }

    public Answer[] getAnswers()
    {
        return answers;
    }

    public void setAnswers(Answer[] answers)
    {
        this.answers = answers;
    }

}
