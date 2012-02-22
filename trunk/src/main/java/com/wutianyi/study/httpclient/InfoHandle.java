package com.wutianyi.study.httpclient;

import org.htmlparser.Node;

public interface InfoHandle<T>
{
    public T handle(Node node);
}
