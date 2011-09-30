package com.wutianyi.anagram;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class HandlerImpl implements Handler{

	private static ThreadLocal<FileChannel> fileChannelLocal = new ThreadLocal<FileChannel>();
	private static Logger logger = Logger.getLogger(HandlerImpl.class);
	
	private ExecutorService executor;

	/**
	 *内容文件位置 
	 */
	private String content_file;
	
	private String index_file;
	/**
	 * 索引
	 */
	private Map<String, Index> indexs;

	private final int MAX_THREAD_SIZE = 100;

	public HandlerImpl(String _content_file, String _index_file) {
		executor = Executors.newFixedThreadPool(MAX_THREAD_SIZE,
				new SearchThreadFactory());
		this.content_file = _content_file;
		this.index_file = _index_file;
		indexs = initIndex();
		        
	}
	
	Map<String,Index> initIndex() {
	    Map<String, Index> indexs = new HashMap<String, Index>();
	    if(StringUtils.isBlank(index_file)) {
	        return indexs;
	    }
	    BufferedReader reader = null;
	    try
        {
             reader = new BufferedReader(new InputStreamReader(new FileInputStream(index_file)));
             String line = reader.readLine();
             while(StringUtils.isNotBlank(line)) {
                 String[] index = line.split(",");
                 if(index.length == 3) {
                     try {
                         int start = Integer.parseInt(index[1]);
                         int end = Integer.parseInt(index[2]);
                         indexs.put(index[0], new Index(start, end));
                     }catch(Exception e) {
                         
                     }
                 }
                 line = reader.readLine();
             }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            if(null != reader) {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
	    return indexs;
	}

	public boolean search(String searchWord) {
		char[] anagram = searchWord.toCharArray();
		Arrays.sort(anagram);
		String anagramWord = new String(anagram);
		Index index = indexs.get(anagramWord);

		if (null == index) {
			return false;
		}

		Future<Boolean> result = executor.submit(new SearchTask(index,
				searchWord));
		boolean r = false;
		try
        {
            r = result.get();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return r;
	}

    @Override
    public String[] fetchAnagramWords(String searchWord)
    {
        return null;
    }

	private class SearchThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			return new SearchThread(r);
		}

	}

	private class SearchTask implements Callable<Boolean> {

		private Index index;
		private String searchWord;

		public SearchTask(Index _index, String _searchWord) {
			this.index = _index;
			this.searchWord = _searchWord;
		}

		@Override
		public Boolean call() throws Exception {
			FileChannel fc = fileChannelLocal.get();
			if (null == fc || null == index) {
				return false;
			}
			ByteBuffer bb = ByteBuffer.allocate(index.end - index.start);
			fc.read(bb, index.start);

			List<String> words = Arrays.asList(new String(bb.array())
					.split(","));

			if (words.contains(searchWord)) {
				return true;
			}

			return false;
		}
	}

	private class SearchThread extends Thread {
		private FileInputStream input;
		private FileChannel fc;

		public SearchThread(Runnable _runnable) {
			super(_runnable);

		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
		    fc = fileChannelLocal.get();
		    if(null == fc) {
		        try {
	                input = new FileInputStream(content_file);
	            } catch (FileNotFoundException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            fc = input.getChannel();

	            fileChannelLocal.set(fc);
		    }
			
			super.run();

		}

		@Override
		public void destroy() {
			if (null != input) {
				try {
					fc.close();
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public static void main(String[] args)
    {
        HandlerImpl handler = new HandlerImpl("context.txt", "index.txt");
        Map<String, Index> map = handler.initIndex();
        for(Entry<String, Index> entry : map.entrySet()) {
            logger.debug(entry.getKey() + "," + entry.getValue().getStart() + "," + entry.getValue().getEnd());
        }
    }

}
