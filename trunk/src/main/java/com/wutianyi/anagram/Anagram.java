/**
 * 
 */

package com.wutianyi.anagram;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;

/**
 * 测试数据 pans pots opt snap stop tops
 * 
 * anps--pans snap opst--pots stop tops opt--opt
 * 
 * @author wutianyi
 * 
 */
public class Anagram {

	private String content_file = "content.txt";
	private String index_file = "index.txt";

	Map<String, List<String>> anagramWords = new HashMap<String, List<String>>();
	Map<String, Index> anagramIndexs = new HashMap<String, Index>();

	/**
	 * 增加一个单词 建立的结构是一个变位词对应一个词列表
	 * 
	 * @param word
	 */
	void addWord(String word) {

		if (StringUtils.isBlank(word)) {
			return;
		}
		// 变位词是单词的字母按顺序排序
		char[] anagram = word.toCharArray();
		Arrays.sort(anagram);

		String anagramWord = new String(anagram);

		List<String> words = anagramWords.get(anagramWord);

		if (null == words) {
			words = new ArrayList<String>();
			anagramWords.put(anagramWord, words);
		}

		if (!words.contains(word)) {
			words.add(word);
		}
	}

	/**
	 * 将建立的内容保存到文件中
	 */
	void saveContent() {

		FileOutputStream output = null;

		try {
			output = new FileOutputStream(content_file);
			FileChannel fc = output.getChannel();
			int start = 0;
			for (Entry<String, List<String>> entry : anagramWords.entrySet()) {
				String[] words = entry.getValue().toArray(new String[0]);
				ByteBuffer buffer = ByteBuffer.wrap(StringUtils
						.join(words, ',').getBytes());
				fc.write(buffer);
				int end = start + buffer.capacity();
				constructIndex(entry.getKey(), start, end);
				start = end;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != output) {
				try {
					output.flush();
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	void saveIndex() {
	    
	    if(null == anagramIndexs || 0 == anagramIndexs.size()) {
	        return;
	    }
	    PrintWriter pw = null;
	    try
        {
            pw = new PrintWriter(new FileOutputStream(index_file));
            for(Entry<String, Index> entry : anagramIndexs.entrySet()) {
                
                String index = entry.getKey() + "," + entry.getValue().getStart() + "," + entry.getValue().getEnd();
                pw.println(index);
            }
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(null != pw) {
                pw.close();
            }
        }
	}

	/**
	 * 建立索引
	 * 
	 * @param anagram
	 *            ， 变位词
	 * @param start
	 *            ， 文件中开始的位置
	 * @param end
	 *            ，文件中结束的位置
	 */
	private void constructIndex(String anagram, int start, int end) {

		if (StringUtils.isBlank(anagram)) {
			return;
		}
		anagramIndexs.put(anagram, new Index(start, end));
	}

	public Map<String, Index> getAnagramIndexs() {
		return anagramIndexs;
	}
	
	public String getContent_file()
    {
        return content_file;
    }

    public String getIndex_file()
    {
        return index_file;
    }

    public static void main(String[] args) throws IOException,
			InterruptedException, ExecutionException {

		Anagram anagram = new Anagram();

		final String[] tempWord = new String[] { "pots", "opt", "snap", "stop",
				"tops", "pans" };
		// BufferedReader reader = new BufferedReader(new InputStreamReader(
		// System.in));
		// String line = reader.readLine();
		// while (!"byte".equals(line)) {
		// anagram.addWord(line);
		// line = reader.readLine();
		// }
		for (String word : tempWord) {
			anagram.addWord(word);
		}
		anagram.saveContent();
		anagram.saveIndex();
		// Thread.sleep(1000);
//		boolean result = handler.search("pans");

	}
}
