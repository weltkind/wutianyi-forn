package com.wutianyi.study.discoverygroup.reptile;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


/**
 * @author hanjie.wuhj
 *
 */
public class HtmlParser
{
    Parser parser;

    public HtmlParser(String content) throws ParserException
    {
        parser = new Parser();
        parser.setInputHTML(content);
    }

    /**
     * @param <T>
     * @param handle 具体提取每个node的信息
     * @param filters 过滤的条件
     * @return
     * @throws ParserException
     */
    public <T> List<T> parser(InfoHandle<T> handle, NodeFilter... filters) throws ParserException
    {
        List<T> results = new ArrayList<T>();
        AndFilter andFilter = new AndFilter(filters);
        NodeList nodelist = parser.extractAllNodesThatMatch(andFilter);

        int size = nodelist.size();
        for (int i = 0; i < size; i++)
        {
            Node node = nodelist.elementAt(i);
            T r = handle.handle(node);
            if(null != r)
            {
                results.add(r);
            }
        }
        return results;
    }
}
