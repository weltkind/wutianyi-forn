package com.wutianyi.study.discoverygroup.parser.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;
import org.xml.sax.SAXException;

import com.wutianyi.study.discoverygroup.CharacterFilterUtils;
import com.wutianyi.study.discoverygroup.Filter;
import com.wutianyi.study.discoverygroup.NonCJKFilter;
import com.wutianyi.study.discoverygroup.parser.BloggerParser;
import com.wutianyi.study.discoverygroup.parser.dataobject.Author;
import com.wutianyi.study.discoverygroup.parser.dataobject.Blogger;
import com.wutianyi.study.discoverygroup.parser.dataobject.RssDTO;

public class RssBloggerParserImpl implements BloggerParser {

	private DocumentBuilder documentBuilder;
	private Filter filter = new NonCJKFilter();

	public RssBloggerParserImpl() {
		try {
			documentBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public RssDTO parser(File file) {

		if (null == file || !file.exists() || !file.isFile()) {
			return null;
		}

		try {
			RssDTO rss = new RssDTO();
			Document document = documentBuilder.parse(file);
			Author author = getAuthor(document);
			List<Blogger> bloggers = getBloggers(document);
			rss.setAuthor(author);
			rss.setBloggers(bloggers);
			return rss;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private List<Blogger> getBloggers(Document document) {
		NodeList items = document.getElementsByTagName("item");
		List<Blogger> bloggers = new ArrayList<Blogger>();
		if (null != items) {
			int len = items.getLength();
			for (int i = 0; i < len; i++) {
				Node node = items.item(i);
				Blogger blogger = getBlogger(node);
				if (null != blogger) {
					bloggers.add(blogger);
				}
			}
		}

		return bloggers;
	}

	private Blogger getBlogger(Node node) {
		NodeList children = node.getChildNodes();
		String title = null;
		String url = null;
		String category = null;
		String content = null;
		if (null != children) {
			int len = children.getLength();
			for (int i = 0; i < len; i++) {
				Node child = children.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE
						&& StringUtils.equals("title", child.getNodeName())) {
					title = child.getTextContent();
				}
				if (child.getNodeType() == Node.ELEMENT_NODE
						&& StringUtils.equals("link", child.getNodeName())) {
					url = child.getTextContent();
				}
				if (child.getNodeType() == Node.ELEMENT_NODE
						&& StringUtils
								.equals("description", child.getNodeName())) {
					content = CharacterFilterUtils.filter(filter, child
							.getTextContent());
				}
				if (child.getNodeType() == Node.ELEMENT_NODE
						&& StringUtils.equals("category", child.getNodeName())) {
					category = child.getTextContent();
				}
			}
		}
		if (StringUtils.isBlank(url) || StringUtils.isBlank(title)) {
			return null;
		}
		Map<String, Integer> words = parserContent(content);
		return new Blogger(title, url, category, null, words);
	}

	private Map<String, Integer> parserContent(String content) {
		Map<String, Integer> words = new HashMap<String, Integer>();
		if (StringUtils.isBlank(content)) {
			return words;
		}
		IKSegmentation seg = new IKSegmentation(new StringReader(content));

		try {
			Lexeme lexeme = seg.next();
			while (null != lexeme) {
				String text = lexeme.getLexemeText();
				Integer count = words.get(text);
				if (null == count) {
					count = 0;
				}
				++count;
				words.put(text, count);

				lexeme = seg.next();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return words;
	}

	private Author getAuthor(Document document) {

		String name = getRootElementValue(document, "title");
		String url = getRootElementValue(document, "link");
		String description = getRootElementValue(document, "description");

		return new Author(name, url, description);
	}

	private String getRootElementValue(Document document, String tagName) {
		NodeList nodelist = document.getElementsByTagName(tagName);
		String value = null;
		if (null != nodelist && nodelist.getLength() > 0) {
			Node node = nodelist.item(0);

			if (node.getNodeType() == Node.ELEMENT_NODE
					&& (StringUtils.equals(tagName, node.getNodeName()))) {
				value = node.getTextContent();
			}
		}
		return value;

	}

	public static void main(String[] args) {
		BloggerParser parser = new RssBloggerParserImpl();
		File file = new File("file/rss/自由主妇.xml");
		RssDTO rss = parser.parser(file);
		
		Author author = rss.getAuthor();
		List<Blogger> bloggers = rss.getBloggers();
		System.out.println(author);
		for(Blogger blogger : bloggers) {
			System.out.println(blogger);
			Map<String, Integer> words = blogger.getWords();
			for(Entry<String, Integer> word : words.entrySet()) {
				System.out.println(word.getKey() + " : " + word.getValue());
			}
			System.out.println("-----------------------------------------------------------");
		}
	}

}
