package com.wutianyi.study.discoverygroup;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.wutianyi.utils.Pair;

public interface Parser
{
    public Pair<String, Pair<String, Integer>[]> parser(File file) throws SAXException, IOException;
}
