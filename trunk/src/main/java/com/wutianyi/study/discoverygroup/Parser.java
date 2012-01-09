package com.wutianyi.study.discoverygroup;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

/**
 * 解析文件的方式，并且将其输出
 * @author hanjiewu
 *
 */
public interface Parser
{
    
    public void parserFiles(File[] files, String outputFile) throws SAXException, IOException;
}
