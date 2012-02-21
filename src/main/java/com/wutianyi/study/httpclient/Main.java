package com.wutianyi.study.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class Main {

	private static String url = "http://blog.sina.com.cn/main/html/alltop_more_new_1.html";

	private static String prefix_url = "http://blog.sina.com.cn/rss/";

	public static void main(String[] args) throws InterruptedException {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		HttpClient httpClient = new HttpClient(connectionManager);
		Set<String> nextPages = new HashSet<String>();
		parseIndex(httpClient, url, executorService, nextPages, true);
		for (String nextPage : nextPages) {
			parseIndex(httpClient, nextPage, executorService, null, false);
		}
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
	}

	static void parseIndex(HttpClient httpClient, String url,
			ExecutorService executorService, Set<String> nextPages,
			boolean fetchNextPage) {
		HttpMethod getMethod = new GetMethod(url);
		Set<String> deals = new HashSet<String>();
		try {
			getMethod
					.addRequestHeader("Content-Type", "text/html; charset=gbk");
			httpClient.executeMethod(getMethod);
			Parser parser = new Parser();
			parser.setInputHTML(new String(getMethod.getResponseBody(), "gbk"));
			AndFilter andFilter = new AndFilter(new NodeFilter[] {
					new TagNameFilter("a"),
					new HasParentFilter(new TagNameFilter("td")) });
			NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
			int size = nodeList.size();
			for (int i = 0; i < size; i++) {
				Node node = nodeList.elementAt(i);
				if (node instanceof LinkTag) {
					LinkTag link = (LinkTag) node;
					if (StringUtils.equals(link.getAttribute("class"), "a02")) {
						if (fetchNextPage) {
							nextPages.add("http://blog.sina.com.cn/main/html/"
									+ link.getAttribute("href"));
						}
					} else {
						if (deals.contains(link.getAttribute("href"))) {
							continue;
						}
						deals.add(link.getAttribute("href"));
						String href = prefix_url
								+ link.getAttribute("href").substring(
										link.getAttribute("href").lastIndexOf(
												'/') + 1) + ".xml";
						executorService.execute(new DownloadTask(httpClient,
								href, link.toPlainTextString().replaceAll("/",
										"").replaceAll(":", "").replaceAll(
										"\\s", "")));
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static class DownloadTask implements Runnable {
		private static final String parentpath = "file/rss";
		HttpClient httpClient;
		String url;
		String name;

		public DownloadTask(HttpClient _httpClient, String _url, String _name) {
			this.httpClient = _httpClient;
			this.url = _url;
			this.name = _name;
		}

		@Override
		public void run() {
			HttpMethod method = new GetMethod(url);
			try {
				httpClient.executeMethod(method);
				FileOutputStream outputStream = new FileOutputStream(new File(
						parentpath, name + ".xml"));
				InputStream inputStream = method.getResponseBodyAsStream();
				byte[] buffers = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(buffers)) != -1) {
					outputStream.write(buffers, 0, len);
				}

				outputStream.flush();
				outputStream.close();
				method.releaseConnection();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
