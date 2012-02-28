package com.wutianyi.study.discoverygroup.file;

public class ItemRelationship
{
    Item head;
    Item end;

    int type;
    
    
    public ItemRelationship(Item _head, Item _end, int _type)
    {
        this.head = _head;
        this.end = _end;
        this.type = _type;
    }
    
}
