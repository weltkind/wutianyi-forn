package com.wutianyi.study.discoverygroup.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author hanjie.wuhj
 *现在做的只是合并
 */
public class FileMain {

	public static void main(String[] args) throws IOException {
		Map<String, Item> cache = new HashMap<String, Item>();
		FileItem fileItem1 = createFileItem(cache, "file/rss/file/test1", 1);
		printItem(fileItem1);
		FileItem fileItem2 = createFileItem(cache, "file/rss/file/test2", 2);
		printItem(fileItem2);
		mergetFileItems(fileItem1, fileItem2);
	}

	private static void mergetFileItems(FileItem fileItem1, FileItem fileItem2) {
		int type1 = fileItem1.type;
		int type2 = fileItem2.type;
		Item item = fileItem1.getNextItem();
		while (null != item) {
			mergeParent(item, type2);
			mergeChildren(item, type2);
			item = item.getChild(type1);
		}
	}

	private static void mergeChildren(Item item, int type) {
		Item child = item.getChild(type);
		if (null != child) {
			while (null != child && child.getParentCount() == 1) {
				System.out.println(child.value);
				child = child.getChild(type);
			}
		}
	}

	private static void mergeParent(Item item, int type) {
		Item p = item.getParentHeader(type);
		if (null != p) {
			while (p != null && p != item) {
				System.out.println(p.value);
				p = p.getChild(type);
			}
		}
		System.out.println(item.value);
	}

	private static FileItem createFileItem(Map<String, Item> cache,
			String file, int type) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));

		FileItem fileItem = new FileItem(type);

		String line = reader.readLine();
		while (StringUtils.isNotBlank(line)) {
			String md5 = DigestUtils.md5Hex(line.trim());
			Item item = cache.get(md5);
			if (null == item) {
				item = new Item(md5, line.trim());
				cache.put(md5, item);
			}
			fileItem.addNextItem(item);
			line = reader.readLine();
		}
		return fileItem;
	}

	private static void printItem(FileItem fileItem) {
		System.out.println("Iterator File Item!");
		Item i = fileItem.getNextItem();
		while (null != i) {
			System.out.println(i.md5);
			i = fileItem.getNextItem();
		}
	}
}
