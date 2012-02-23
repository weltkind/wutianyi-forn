package com.wutianyi.study.discoverygroup.parser;

import java.io.File;

import com.wutianyi.study.discoverygroup.parser.dataobject.RssDTO;


public interface BloggerParser {
	
	public RssDTO parser(File file);
}
