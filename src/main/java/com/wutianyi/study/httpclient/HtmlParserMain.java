package com.wutianyi.study.httpclient;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParserMain {
	public static void main(String[] args) {

		Map<String, String> maps = new HashMap<String, String>();

		try {
			Parser parser = new Parser("temp.html");
			AndFilter andFilter = new AndFilter(new NodeFilter[] {
					new TagNameFilter("a"),
					new HasParentFilter(new TagNameFilter("td")),
					new HasAttributeFilter("target", "_blank") });
			NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
			int size = nodeList.size();
			for (int i = 0; i < size; i++) {
				Node node = nodeList.elementAt(i);
				if (node instanceof LinkTag) {
					LinkTag link = (LinkTag) node;
					maps.put(link.getAttribute("href"), link.toPlainTextString());
				}

			}
			HttpClient httpClient = new HttpClient();
			for (Entry<String, String> entry : maps.entrySet()) {
				HttpMethod method = new GetMethod(entry.getKey());
				httpClient.executeMethod(method);
				File file = new File("file/rss/", entry.getValue() + ".xml");
				FileUtils.writeByteArrayToFile(file, method.getResponseBody());
				method.releaseConnection();
			}
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
