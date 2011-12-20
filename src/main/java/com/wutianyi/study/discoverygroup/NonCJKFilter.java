package com.wutianyi.study.discoverygroup;

import org.wltea.analyzer.help.CharacterHelper;

/**
 * 过滤掉英文
 * @author hanjiewu
 *
 */
public class NonCJKFilter implements Filter
{

    @Override
    public boolean isFilter(char c)
    {
        return !CharacterHelper.isCJKCharacter(c);
    }

}
