package com.wutianyi.study.json;

public class ImageHelpBean
{
    private String imageUrl;
    private String header;
    private String content;
    private boolean isIndexShow;

    public boolean isIndexShow()
    {
        return isIndexShow;
    }

    public void setIndexShow(boolean isIndexShow)
    {
        this.isIndexShow = isIndexShow;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

}
