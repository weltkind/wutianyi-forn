package com.wutianyi.study.discoverygroup.file;

public class ItemRelationship
{
    Item head;
    Item end;

    int type;
    int line;
    
    public ItemRelationship(Item _head, Item _end,int _line, int _type)
    {
        this.head = _head;
        this.end = _end;
        this.type = _type;
        this.line = _line;
    }
    
}
