package com.wutianyi.study.vcard;

import info.ineighborhood.cardme.vcard.VCardType;

import java.util.HashMap;
import java.util.Map;

public class ReportDO implements Cloneable
{
    private int id;
    private String name;
    Header[] headers;

    String reportHeader;
    Map<VCardType, Header> indexes;

    public ReportDO()
    {

    }

    public ReportDO(int id, String name, Header[] headers)
    {
        this.id = id;
        this.name = name;
        this.headers = headers;
        initIndexex();
    }

    private void initIndexex()
    {
        indexes = new HashMap<VCardType, Header>();
        for (Header header : headers)
        {
            try
            {
                indexes.put(VCardType.valueOf(header.getvKey()), header);
            }
            catch (Exception e)
            {

            }
        }
    }

    private void initReportHeader()
    {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        int l = getHeaders().length - 1;
        for (Header header : getHeaders())
        {
            if (header.show)
            {
                Value[] values = header.getValues();
                if (null == values || values.length == 0)
                {
                    builder.append('"');
                    builder.append(header.getdKey());
                    builder.append('"');
                    if (header.isMulti() && header.isAutoIncrement())
                    {
                        for (int j = 2; j <= header.getCount(); j++)
                        {
                            builder.append(',');
                            builder.append('"');
                            builder.append(header.getdKey());
                            builder.append(' ');
                            builder.append(j);
                            builder.append('"');
                        }
                    }

                }
                else
                {
                    int index = 0;
                    int len = values.length - 1;

                    for (Value value : values)
                    {
                        if (!value.escape)
                        {
                            builder.append('"');
                            builder.append(value.getDisplayName());
                            builder.append('"');
                            if (index != len)
                            {
                                builder.append(',');
                            }
                        }
                        ++index;
                    }
                    if (header.isMulti() && header.isAutoIncrement())
                    {
                        for (int j = 1; j < header.getCount(); j++)
                        {
                            builder.append(',');
                            index = 0;
                            for (Value value : values)
                            {
                                if (!value.escape)
                                {
                                    builder.append('"');
                                    builder.append(value.getDisplayName());
                                    builder.append(' ');
                                    builder.append(j);
                                    builder.append('"');
                                    if (index != len)
                                    {
                                        builder.append(',');
                                    }
                                }
                                ++index;
                            }
                        }
                    }
                }
            }

            if (i != l && header.isShow())
            {
                builder.append(',');
            }
            i++;
        }
        reportHeader = builder.toString();

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Header[] getHeaders()
    {
        return headers;
    }

    public void setHeaders(Header[] headers)
    {
        this.headers = headers;
    }

    public String getReportHeader()
    {
        // if(StringUtils.isBlank(reportHeader));
        initReportHeader();
        return reportHeader;
    }

    public String toString()
    {
        String v = "[\n\tid: " + id + "\n\tdisplay_name: " + name;
        for (Header header : headers)
        {
            v += "\n\t" + header.toString();
        }
        return v + "\n]";
    }

    public ReportDO clone()
    {
        try
        {
            ReportDO r = (ReportDO) super.clone();
            Header[] headers = new Header[this.getHeaders().length];
            for (int i = 0; i < getHeaders().length; i++)
            {
                headers[i] = getHeaders()[i].clone();
            }
            r.setHeaders(headers);
            r.initIndexex();
            return r;
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return this;
    }
}
