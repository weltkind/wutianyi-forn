package com.wutianyi.study.discoverygroup;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import com.wutianyi.utils.Pair;

public class JCKAnalyseUtils
{

    public static Pair<String, Integer>[] analyse(String content)
    {
        Pair<String, Integer>[] results = null;
        if (StringUtils.isBlank(content))
        {
            return null;
        }
        IKSegmentation seg = new IKSegmentation(new StringReader(content));
        try
        {
            Map<String, Integer> maps = new HashMap<String, Integer>();
            Lexeme lexeme = seg.next();
            while (null != lexeme)
            {
                String text = lexeme.getLexemeText();
                Integer count = maps.get(text);
                if (count == null)
                {
                    count = 0;
                }
                maps.put(text, ++count);
                lexeme = seg.next();
            }
            if (maps.size() > 0)
            {
                int l = 0;
                results = new Pair[maps.size()];
                for (Entry<String, Integer> entry : maps.entrySet())
                {
                    results[l] = new Pair<String, Integer>(entry.getKey(), entry.getValue());
                    ++l;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return results;
    }
}
