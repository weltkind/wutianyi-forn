package com.wutianyi.anagram;

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

/**
 * @author hanjiewu
 *搜索相应的变位词
 */
public class SearchAnagram
{
    private static Logger logger = Logger.getLogger(SearchAnagram.class);
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        String[] anagramWords = new String[] {"pots", "opt", "snap", "stop",
                "tops", "pans"};
        Anagram anagram = new Anagram();
        for(String word : anagramWords) {
            anagram.addWord(word);
        }
        anagram.saveContent();
        anagram.saveIndex();
        boolean result = false;
        Handler searchHandler = new HandlerImpl(anagram.getContent_file(), anagram.getIndex_file());
        for(String word : anagramWords) {
            result = searchHandler.search(word);
            if(result) {
                logger.debug("True");
            }
            assert(result);
        }
        result = searchHandler.search("test");
        if(result) {
            logger.debug("True");
        } else {
            logger.debug("false");
        }
        assert(result);
    }
}
