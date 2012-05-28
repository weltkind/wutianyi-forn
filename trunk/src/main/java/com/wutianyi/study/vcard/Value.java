package com.wutianyi.study.vcard;

public class Value
{
    int index;
    String displayName;
    boolean escape;

    public Value(int index, String displayName, boolean escape)
    {
        this.index = index;
        this.displayName = displayName;
        this.escape = escape;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public boolean isEscape()
    {
        return escape;
    }

    public void setEscape(boolean escape)
    {
        this.escape = escape;
    }

    public String toString()
    {
        return "[\n\tindex: " + index + "\n\tdisplay_name: " + displayName + "\n\tescape: " + escape + "\n]";
    }
}
