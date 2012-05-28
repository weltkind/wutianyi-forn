package com.wutianyi.study.vcard;

public class ReportDO
{
    private int id;
    private String name;
    Header[] headers;

    public ReportDO()
    {

    }

    public ReportDO(int id, String name, Header[] headers)
    {
        this.id = id;
        this.name = name;
        this.headers = headers;
    }

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

    public Header[] getHeaders()
    {
        return headers;
    }

    public void setHeaders(Header[] headers)
    {
        this.headers = headers;
    }

    public String toString()
    {
        String v = "[\n\tid: " + id + "\n\tdisplay_name: " + name;
        for (Header header : headers)
        {
            v += "\n\t" + header.toString();
        }
        return v + "\n]";
    }
}
