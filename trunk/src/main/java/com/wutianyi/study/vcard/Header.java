package com.wutianyi.study.vcard;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Header implements Cloneable
{
    /**
     * vcard property
     */
    String vKey;

    /**
     * vkey 的parameters
     */
    String[] parameters;
    /**
     * display property
     */
    String dKey;

    /**
     * value delimiter
     */
    String delimiter;

    boolean multi;

    boolean autoIncrement;

    int count;

    Value[] values;

    boolean isExtend;

    int splitlength;

    boolean show;

    boolean escapeParameter;

    Map<String, Value> valueIndexes;

    public Header(String vKey, String dKey, String[] parameters, String delimiter, boolean multi,
            boolean autoIncrement, int count, Value[] values, boolean isExtend, int splitlength, boolean show,
            boolean escapeParameter)
    {
        this.vKey = vKey;
        this.dKey = dKey;
        this.parameters = parameters;
        this.delimiter = delimiter;
        this.multi = multi;
        this.autoIncrement = autoIncrement;
        this.count = count;
        this.values = values;
        this.isExtend = isExtend;
        this.splitlength = splitlength;
        this.show = show;
        this.escapeParameter = escapeParameter;
        if (null != values && 0 != values.length)
        {
            valueIndexes = new HashMap<String, Value>();
            for (Value value : values)
            {
                valueIndexes.put(value.getDisplayName(), value);
            }
        }
    }

    public Value getValueByDisplayName(String displayName)
    {
        if (null != valueIndexes)
        {
            return valueIndexes.get(displayName);
        }
        return null;
    }

    public String getvKey()
    {
        return vKey;
    }

    public void setvKey(String vKey)
    {
        this.vKey = vKey;
    }

    public String[] getParameters()
    {
        return parameters;
    }

    public void setParameters(String[] parameters)
    {
        this.parameters = parameters;
    }

    public String getdKey()
    {
        return dKey;
    }

    public void setdKey(String dKey)
    {
        this.dKey = dKey;
    }

    public String getDelimiter()
    {
        return delimiter;
    }

    public void setDelimiter(String delimiter)
    {
        this.delimiter = delimiter;
    }

    public boolean isMulti()
    {
        return multi;
    }

    public void setMulti(boolean multi)
    {
        this.multi = multi;
    }

    public boolean isAutoIncrement()
    {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement)
    {
        this.autoIncrement = autoIncrement;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public Value[] getValues()
    {
        return values;
    }

    public void setValues(Value[] values)
    {
        this.values = values;
    }

    public boolean isExtend()
    {
        return isExtend;
    }

    public void setExtend(boolean isExtend)
    {
        this.isExtend = isExtend;
    }

    public int getSplitlength()
    {
        return splitlength;
    }

    public void setSplitlength(int splitlength)
    {
        this.splitlength = splitlength;
    }

    public Header clone()
    {
        try
        {
            if (show)
            {
                return this;
            }

            Header header = (Header) super.clone();
            // header.setAutoIncrement(false);
            header.setCount(0);
            // header.setMulti(false);
            // header.setShow(false);
            return header;
        }
        catch (CloneNotSupportedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public boolean isShow()
    {
        return show;
    }

    public void setShow(boolean show)
    {
        this.show = show;
    }

    public boolean isEscapeParameter()
    {
        return escapeParameter;
    }

    public void setEscapeParameter(boolean escapeParameter)
    {
        this.escapeParameter = escapeParameter;
    }

    public String toString()
    {
        String v = "[\n\tvcard: " + vKey + "\n\tdisplay_name: " + dKey + "\n\tparameters: "
                + StringUtils.join(parameters, ",") + "\n\tdelimiter: " + delimiter + "\n\tmulti: " + multi
                + "\n\tauto_increment: " + autoIncrement + "\n\tcount: " + count + "\n\tsplitlength: " + splitlength;

        for (Value value : values)
        {
            v += "\n\t" + value.toString();
        }
        v += "\n]";
        return v;
    }
}
