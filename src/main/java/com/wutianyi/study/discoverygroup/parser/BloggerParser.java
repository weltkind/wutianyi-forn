package com.wutianyi.study.discoverygroup.parser;

import java.io.File;

import com.wutianyi.study.discoverygroup.parser.dataobject.Author;

public interface BloggerParser
{

    public Author parser(File file);
}
