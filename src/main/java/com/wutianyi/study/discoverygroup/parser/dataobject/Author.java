package com.wutianyi.study.discoverygroup.parser.dataobject;

import java.util.List;

/**
 * @author hanjiewu
 * 
 */
public class Author
{

    int id;
    String name;
    String url;
    String description;

    List<Blogger> bloggers;

    public Author(String _name, String _url, String _description)
    {
        this.name = _name;
        this.url = _url;
        this.description = _description;
    }

    public Author()
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

    @Override
    public String toString()
    {
        return "Author name: " + name + "\turl: " + url + "\tdescription: " + description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Blogger> getBloggers()
    {
        return bloggers;
    }

    public void setBloggers(List<Blogger> bloggers)
    {
        this.bloggers = bloggers;
    }

}
