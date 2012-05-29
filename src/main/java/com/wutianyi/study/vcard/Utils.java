package com.wutianyi.study.vcard;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.wutianyi.study.vcard.Wrapper.VcardWrapper;
import com.wutianyi.study.vcard.Wrapper.WrapperInterface;

import info.ineighborhood.cardme.vcard.VCardImpl;
import info.ineighborhood.cardme.vcard.VCardType;

public class Utils
{

    public static void buildValues(Header header, VcardWrapper vcard, StringBuilder builder)
    {
        VCardType vCardType = getVcardType(header.getvKey());

        if (null == vCardType)
        {
            return;
        }

        int count = 1;
        int i = 0;
        if (header.isAutoIncrement() && header.isMulti())
        {
            count = header.getCount();
        }
        switch (vCardType)
        {
        case XTENDED:
        {
            WrapperInterface[] extendedFeatures = vcard.getValues(VCardType.XTENDED);
            if (null != extendedFeatures)
            {
                for (int index = 0; index < extendedFeatures.length && i < count; index++)
                {
                    if (StringUtils.equals(header.getvKey(), extendedFeatures[index].getType()))
                    {
                        buildValue(header, extendedFeatures[index].getValue(), builder);
                        if (i != count - 1)
                        {
                            builder.append(',');
                        }
                        i++;
                    }
                }
            }

            appendEmpty(header, count - i, builder);
            break;
        }
        default:
        {
            WrapperInterface[] features = vcard.getValues(vCardType);
            if (null != features)
            {
                for (int index = 0; index < features.length && i < count; index++)
                {
                    String[] parameters = header.getParameters();
                    if (null != parameters && parameters.length > 0)
                    {
                        if (features[index].containParameter(parameters))
                        {
                            buildValue(header, features[index].getValue(), builder);
                            i++;
                            if (i < count)
                            {
                                builder.append(',');
                            }
                        }
                    }
                    else
                    {
                        buildValue(header, features[index].getValue(), builder);
                        i++;
                        if (i < count)
                        {
                            builder.append(',');
                        }
                    }
                }
            }
            appendEmpty(header, count - i, builder);
        }
        }
    }

    private static void appendEmpty(Header header, int count, StringBuilder builder)
    {

        for (int i = 0; i < count; i++)
        {
            Value[] values = header.getValues();
            if (null != values && values.length > 0)
            {
                int len = values.length;
                for (int j = 0; j < len; j++)
                {
                    builder.append("\"\"");
                    if (j != len - 1)
                    {
                        builder.append(',');
                    }
                }
            }
            else
            {
                builder.append("\"\"");
            }
            if (i != count - 1)
            {
                builder.append(',');
            }
        }
    }

    private static void buildValue(Header header, String value, StringBuilder builder)
    {
        if (null == value)
        {
            value = "";
        }
        Value[] values = header.getValues();
        if (null == values || values.length == 0)
        {
            builder.append('"');
            builder.append(value);
            builder.append('"');
        }
        else
        {
            int len = header.getSplitlength();
            int vl = values.length;
            String[] vs = value.split(";", len);
            len = vs.length;

            int i = 0;
            for (Value v : values)
            {

                if (v.escape)
                {
                    vl = vl - 1;
                    continue;
                }
                i++;
                builder.append('"');
                if (v.getIndex() < len)
                {
                    builder.append(vs[v.getIndex()]);
                }
                builder.append('"');

                if (i < vl)
                {
                    builder.append(',');
                }
            }
        }
    }

    public static ReportDO createReportDO(List<VcardWrapper> vcards, ReportDO report)
    {
        ReportDO newReport = report.clone();
        Header[] headers = newReport.getHeaders();

        for (VcardWrapper vcard : vcards)
        {
            for (Header header : headers)
            {
                VCardType vCardType = getVcardType(header.getvKey());
                if (null == vCardType)
                {
                    continue;
                }

                switch (vCardType)
                {
                case XTENDED:
                {
                    int count = 0;
                    WrapperInterface[] wrappers = vcard.getValues(vCardType);
                    if (null != wrappers)
                    {
                        for (WrapperInterface wrapper : wrappers)
                        {
                            if (StringUtils.equals(header.getdKey(), wrapper.getType()))
                            {
                                ++count;
                            }
                        }
                    }

                    if (count > 0)
                    {
                        header.setShow(true);
                        header.setAutoIncrement(true);
                        header.setMulti(true);
                        if (count > header.getCount())
                        {
                            header.setCount(count);
                        }
                    }
                    break;
                }
                default:
                {
                    int count = 0;
                    WrapperInterface[] wrappers = vcard.getValues(vCardType);
                    if (null != wrappers)
                    {
                        for (WrapperInterface wrapper : wrappers)
                        {
                            String[] parameters = header.getParameters();
                            if (null != parameters && parameters.length > 0)
                            {
                                if (wrapper.containParameter(parameters))
                                {
                                    ++count;
                                }
                            }
                            else
                            {
                                ++count;
                            }
                        }
                    }
                    if (count > 0)
                    {
                        header.setShow(true);
                        header.setAutoIncrement(true);
                        header.setMulti(true);
                        if (count > header.getCount())
                        {
                            header.setCount(count);
                        }
                    }
                }
                }
            }
        }

        return newReport;
    }

    private static VCardType getVcardType(String s)
    {
        VCardType vCardType = null;
        ;
        if (s.startsWith("X-"))
        {
            vCardType = VCardType.XTENDED;
        }
        else
        {
            try
            {
                vCardType = VCardType.valueOf(s);
            }
            catch (Exception e)
            {
            }

        }
        return vCardType;
    }
}
