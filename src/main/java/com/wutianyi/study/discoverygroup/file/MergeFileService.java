package com.wutianyi.study.discoverygroup.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

public class MergeFileService {

	public MergeResult mergeFiles(File fA, File fB) throws IOException {
		Map<String, Item> localCache = new HashMap<String, Item>();

		FileItem fileItemA = createFileItem(localCache, fA, 1);
		FileItem fileItemB = createFileItem(localCache, fB, 2);

		return mergeFileItems(fileItemA, fileItemB);
	}

	MergeResult mergeFileItems(FileItem fileItem1, FileItem fileItem2) {
		MergeResult mergeResult = new MergeResult();
		int type1 = fileItem1.type;
		int type2 = fileItem2.type;
		Item item = fileItem1.getNextItem();
		while (null != item) {
			mergeParent(mergeResult, item, type1, type2);
			mergeChildren(mergeResult, item, type2);
			item = item.getChild(type1);
		}

		return mergeResult;
	}

	void mergeChildren(MergeResult mergeResult, Item item, int type) {
		Item child = item.getChild(type);
		if (null != child) {
			while (null != child && child.getParentCount() == 1) {
				// System.out.println(child.value);
				mergeResult.addResultItems(new ResultItem(type, child
						.getLine(type), 3));
				child = child.getChild(type);
			}
		}
	}

	void mergeParent(MergeResult mergeResult, Item item, int type1, int type2) {
		Item p = item.getParentHeader(type2);
		boolean isShare = false;
		if (null != p) {
			while (p != null && p != item) {
				isShare = true;
				mergeResult.addResultItems(new ResultItem(type2, p
						.getLine(type2), 3));
				p = p.getChild(type2);
			}
		}
		if (isShare || item.getChild(type2) != null) {
			mergeResult.addResultItems(new ResultItem(type1, item
					.getLine(type1), 1));
		} else {
			mergeResult.addResultItems(new ResultItem(type1, item
					.getLine(type1), 2));
		}

		// System.out.println(item.value);
	}

	FileItem createFileItem(Map<String, Item> cache, File file, int type)
			throws IOException {
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

	public static void main(String[] args) throws IOException {
		MergeFileService fileService = new MergeFileService();
		MergeResult mergeResult = fileService.mergeFiles(new File(
				"file/rss/file/test1"), new File("file/rss/file/test2"));
		
		List<ResultItem> resultItems = mergeResult.resultItems;
		for(ResultItem r : resultItems) {
			if(r.type == 1) {
				System.out.println("Share: " + r.line);
			}
			if(r.type == 2) {
				System.out.println("Self: " + r.line);
			}
			if(r.type == 3) {
				System.out.println("his: " + r.line);
			}
		}
	}
}
