package com.wutianyi.anagram;

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.wutianyi.utils.CountTime;

/**
 * @author hanjiewu
 *搜索相应的变位词
 */
public class SearchAnagram
{
    private static Logger logger = Logger.getLogger(SearchAnagram.class);
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        final String[] anagramWords = new String[] {"pots", "opt", "snap", "stop",
                "tops", "pans"};
        Anagram anagram = new Anagram();
        for(String word : anagramWords) {
            anagram.addWord(word);
        }
        anagram.saveContent();
        anagram.saveIndex();
        boolean result = false;
        final Handler searchHandler = new HandlerImpl(anagram.getContent_file(), anagram.getIndex_file());
        
        CountTime countTime = new CountTime(new Runnable()
        {
            
            @Override
            public void run()
            {
                boolean result = false;
                
                while(true) {
                    for(String word : anagramWords) {
                        result = searchHandler.search(word);
                        if(!result) {
                            logger.debug("false");
                        }
                    }
                    try
                    {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e)
                    {
                        logger.error(e.getMessage());
                    }
                }
            }
        }, 10);
        
    }
}
