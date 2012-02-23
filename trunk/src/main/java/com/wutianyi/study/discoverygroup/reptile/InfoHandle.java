package com.wutianyi.study.discoverygroup.reptile;

import org.htmlparser.Node;

public interface InfoHandle<T>
{
    public T handle(Node node);
}
