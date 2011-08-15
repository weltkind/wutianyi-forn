package com.wutianyi.anagram;

import java.util.concurrent.ExecutionException;

public interface Handler
{

    /**
     * 是否存在单词列表中
     * 
     * @param searchWord
     * @return
     */
    public boolean search(String searchWord);

    /**
     * 获取所有的变位词
     * 
     * @param searchWord
     * @return
     */
    public String[] fetchAnagramWords(String searchWord);
}
