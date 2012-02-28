package com.wutianyi.study.discoverygroup.file;

public class FileItem
{
    Item head;
    Item curItem;
    Item nextItem;
    int type;

    public FileItem(int _type)
    {
        this.type = _type;
    }

    void addNextItem(Item item)
    {
        if (null == head)
        {
            head = item;
            curItem = head;
        }
        else
        {
            curItem.addChild(item, type);
            item.addParent(curItem, type);
            curItem = item;
        }
    }

    Item getNextItem()
    {
        if (null == nextItem)
        {
            nextItem = head;
        }
        else
        {
            nextItem = nextItem.getChild(type);
        }
        return nextItem;
    }
}
