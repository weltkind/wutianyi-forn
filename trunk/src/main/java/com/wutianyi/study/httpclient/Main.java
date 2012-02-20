package com.wutianyi.study.httpclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	private static String url = "http://blog.sina.com.cn/main/html/alltop_more_new_1.html";
	private static DocumentBuilder documentBuilder;
	private static String[] parseTags = new String[] { "body", "table" };

	public static void main(String[] args) {
		
		// documentBuilder.parse(url);
//		HttpClient httpClient = new HttpClient();
		// httpClient.getHostConfiguration().setProxy("web-proxy.oa.com", 8080);
//		HttpMethod httpMethod = new GetMethod(url);

		try {
			documentBuilder = DocumentBuilderFactory.newInstance()
			.newDocumentBuilder();
//			httpClient.executeMethod(httpMethod);
			
			File file = new File("temp.html");
//			byte[] buf = httpMethod.getResponseBody();
//			System.out.println(buf.length);
//			httpMethod.releaseConnection();
//			FileUtils.writeByteArrayToFile(file, buf);
			
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().getElementsByTagName("td").getLength();
			NodeList nodeList = document.getElementsByTagName("td");
//			if (null != nodeList && nodeList.getLength() > 0) {
//				
//				int len = nodeList.getLength();
//				for (int i = 0; i < len; i++) {
//					Node node = nodeList.item(i);
//					System.out.println(node.getNodeName());
//				}
//			}

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
