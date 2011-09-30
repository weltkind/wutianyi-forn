package com.wutianyi.wenjuan;

public class Questionnaire
{
    private int id;
    private String name;
    private Question[] questions;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Question[] getQuestions()
    {
        return questions;
    }

    public void setQuestions(Question[] questions)
    {
        this.questions = questions;
    }

}
