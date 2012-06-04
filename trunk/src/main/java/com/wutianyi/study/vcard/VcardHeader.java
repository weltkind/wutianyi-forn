package com.wutianyi.study.vcard;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class VcardHeader
{
    String name;

    String parameter;

    int index;

    int length;

    Map<Integer, Integer> indexes;

    public VcardHeader(String name, String parameter)
    {
        this.name = name;
        this.parameter = parameter;
    }

    public void getVcardHeader(String[] values, StringBuilder sb)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (StringUtils.isNotBlank(parameter))
        {
            builder.append(';');
            builder.append(parameter);
        }
        builder.append(':');
        if (null == indexes || 0 == indexes.size())
        {
            if (index < values.length)
            {
                String vv = VcardUtils.escapeString(values[index]);
                if (StringUtils.isBlank(vv))
                {
                    return;
                }
                builder.append(vv);
            }
            else
            {
                return;
            }
        }
        else
        {
            int count = 0;
            for (int i = 0; i < length; i++)
            {
                String vv = null;
                if (null != indexes.get(i))
                {
                    int index = indexes.get(i);
                    if (index < values.length)
                    {
                        vv = VcardUtils.escapeString(values[index]);
                        builder.append(vv);
                    }
                }
                if (StringUtils.isBlank(vv))
                {
                    ++count;
                }
                if (i != length - 1)
                {
                    builder.append(';');
                }
            }
            if (count == length)
            {
                return;
            }
        }
        // builder.append(VcardUtils.CRLF);
        String tmpStr = builder.toString();
        String str = VcardUtils.foldLine(tmpStr, VcardUtils.CRLF, 75, VcardUtils.SP);
        sb.append(str);
        sb.append(VcardUtils.CRLF);
    }

    public void addIndex(int hIndex, int vIndex)
    {
        if (null == indexes)
        {
            indexes = new HashMap<Integer, Integer>();
        }
        indexes.put(hIndex, vIndex);
    }

    public boolean isSingle()
    {
        if (null == indexes || 0 == indexes.size())
        {
            return true;
        }
        return false;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

}
