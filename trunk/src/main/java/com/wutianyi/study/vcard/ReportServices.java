package com.wutianyi.study.vcard;

import info.ineighborhood.cardme.vcard.VCardImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wutianyi.study.vcard.Wrapper.VcardWrapper;
import com.wutianyi.utils.MyStringUtils;

public class ReportServices
{
    private static Map<Integer, ReportDO> reports;

    static
    {
        try
        {
            init();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void init() throws ParserConfigurationException, SAXException, IOException
    {
        reports = new HashMap<Integer, ReportDO>();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(ReportServices.class
                .getResourceAsStream("/com/wutianyi/study/vcard/configs.xml"));
        NodeList reportNodes = document.getElementsByTagName("report");

        for (int i = 0; i < reportNodes.getLength(); i++)
        {
            Node node = reportNodes.item(i);
            ReportDO report = createReport(node);
            if (null != report)
            {
                reports.put(report.getId(), report);
            }
        }
    }

    private static ReportDO createReport(Node node)
    {
        if (!node.hasAttributes())
        {
            return null;
        }

        List<Header> headers = new ArrayList<Header>();
        NamedNodeMap attrs = node.getAttributes();

        int id = getPropertyInt(attrs, ReportConstants.report_id, -1);
        if (-1 == id)
        {
            return null;
        }
        String name = getProperty(attrs, ReportConstants.report_name);

        NodeList headerNodes = node.getChildNodes();

        for (int i = 0; i < headerNodes.getLength(); i++)
        {
            Header header = createHeader(headerNodes.item(i));
            if (null != header)
            {
                headers.add(header);
            }
        }

        if (headers.size() == 0)
        {
            return null;
        }

        return new ReportDO(id, name, headers.toArray(new Header[]
        {}));
    }

    private static Header createHeader(Node item)
    {
        if (!item.hasAttributes())
        {
            return null;
        }
        NamedNodeMap attrs = item.getAttributes();

        String vKey = getProperty(attrs, ReportConstants.header_vcard);
        String dKey = getProperty(attrs, ReportConstants.display_name);
        String delimiter = getProperty(attrs, ReportConstants.header_delimiter);
        boolean multi = getPropertyBoolean(attrs, ReportConstants.header_multi);
        boolean autoIncrement = getPropertyBoolean(attrs, ReportConstants.header_auto_increment);
        boolean extend = getPropertyBoolean(attrs, ReportConstants.header_extend);
        String parameterStr = getProperty(attrs, ReportConstants.header_parameter);
        int count = getPropertyInt(attrs, ReportConstants.header_count, 0);
        int splitlength = getPropertyInt(attrs, ReportConstants.header_split_length, 0);
        boolean show = getPropertyBoolean(attrs, ReportConstants.header_show);
        boolean escapeParameter = getPropertyBoolean(attrs, ReportConstants.header_escape_parameter);
        if (StringUtils.isBlank(vKey) || StringUtils.isBlank(dKey))
        {
            return null;
        }

        List<Value> values = new ArrayList<Value>();
        NodeList valueNodes = item.getChildNodes();
        if (null != valueNodes)
        {
            for (int i = 0; i < valueNodes.getLength(); i++)
            {
                Value value = createValue(valueNodes.item(i));
                if (null != value)
                {
                    values.add(value);
                }
            }
        }
        String[] parameters = StringUtils.isNotBlank(parameterStr) ? parameterStr.split(";") : null;

        return new Header(vKey, dKey, parameters, delimiter, multi, autoIncrement, count, values.toArray(new Value[]
        {}), extend, splitlength, show, escapeParameter);
    }

    private static Value createValue(Node item)
    {
        if (!item.hasAttributes())
        {
            return null;
        }
        NamedNodeMap attrs = item.getAttributes();
        int index = getPropertyInt(attrs, ReportConstants.value_index, -1);
        String displayName = getProperty(attrs, ReportConstants.display_name);
        boolean escape = getPropertyBoolean(attrs, ReportConstants.value_escape);
        return new Value(index, displayName, escape);
    }

    private static boolean getPropertyBoolean(NamedNodeMap attrs, String name)
    {
        if (null != attrs)
        {
            Node attr = attrs.getNamedItem(name);
            if (null != attr)
            {
                return MyStringUtils.convertBoolean(attr.getTextContent(), false);
            }
        }
        return false;
    }

    private static String getProperty(NamedNodeMap attrs, String name)
    {
        if (null != attrs)
        {
            Node attr = attrs.getNamedItem(name);
            if (null != attr)
            {
                return attr.getTextContent();
            }
        }

        return null;
    }

    private static int getPropertyInt(NamedNodeMap attrs, String name, int defaultValue)
    {
        if (null != attrs)
        {
            Node attr = attrs.getNamedItem(name);
            if (null != attr)
            {
                return MyStringUtils.convertInt(attr.getTextContent(), defaultValue);
            }
        }
        return defaultValue;
    }

    public static ReportDO getReport(int id)
    {
        return reports.get(id);
    }

    public static String createVcardFiles(byte[] datas, int reportId)
    {
        ReportDO report = reports.get(reportId);
        if (null == report)
        {
            return "";
        }
        String[] lines = new String(datas).split(VcardUtils.CRLF);
        if (lines.length < 2)
        {
            return "";
        }

        VcardHeader[] vcardHeaders = initVcardHeader(lines[0], report);
        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < lines.length; i++)
        {
            builder.append("BEGIN:VCARD");
            builder.append(VcardUtils.CRLF);
            String[] values = splitStr(lines[i]);
            for (VcardHeader vcardHeader : vcardHeaders)
            {
                vcardHeader.getVcardHeader(values, builder);
            }
            builder.append("END:VCARD");
            builder.append(VcardUtils.CRLF);
        }

        return builder.toString();

    }

    private static VcardHeader[] initVcardHeader(String headerStr, ReportDO report)
    {

        if (StringUtils.isBlank(headerStr))
        {
            return null;
        }
        Map<String, VcardHeader> maps = new HashMap<String, VcardHeader>();
        String[] headers = splitStr(headerStr);
        int i = 0;
        for (String header : headers)
        {
            int index = -1;
            int countIndex = header.lastIndexOf(VcardUtils.SP);
            if (countIndex != -1)
            {
                index = MyStringUtils.convertInt(header.substring(countIndex + 1, header.length()), -1);
            }
            String displayName = header;

            if (index != -1)
            {
                displayName = header.substring(0, countIndex);
            }

            Header h = report.getHeaderByDisplayName(displayName);
            if (null != h)
            {
                String key = h.getdKey();
                if (index != -1)
                {
                    key += VcardUtils.SP + index;
                }
                VcardHeader vcardHeader = maps.get(key);
                if (null == vcardHeader)
                {
                    vcardHeader = new VcardHeader(h.getvKey(), StringUtils.join(h.getParameters(), ';'));
                    vcardHeader.setLength(h.getSplitlength());
                    vcardHeader.setIndex(i);
                    maps.put(key, vcardHeader);
                }
                Value value = h.getValueByDisplayName(displayName);
                if (null != value)
                {
                    vcardHeader.addIndex(value.getIndex(), i);
                }
            }
            ++i;
        }
        return maps.values().toArray(new VcardHeader[0]);
    }

    private static String[] splitStr(String str)
    {
        boolean inline = false;
        List<String> strs = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            switch (c)
            {
            case '"':
            {
                inline = !inline;
                break;
            }
            case ',':
            {
                if (!inline)
                {
                    strs.add(builder.toString());
                    builder.delete(0, builder.length());
                }
                else
                {
                    builder.append(c);
                }
                break;
            }
            default:
                builder.append(c);
            }
        }
        if (builder.length() > 0)
        {
            strs.add(builder.toString());
        }
        return strs.toArray(new String[0]);
    }

    public static void main(String[] args) throws IOException
    {
        // TestParser testParser = new TestParser();
        // List<VCardImpl> vcards = testParser.importVCards();
        //
        // List<VcardWrapper> vs = new ArrayList<VcardWrapper>();
        // for (VCardImpl v : vcards)
        // {
        // vs.add(new VcardWrapper(v));
        // }
        //
        // ReportDO report = getReport(1);
        // ReportDO newReport = Utils.createReportDO(vs, report);
        //
        // System.out.println(newReport.getReportHeader());
        // System.out.println(report.getReportHeader());
        byte[] datas = FileUtils.readFileToByteArray(new File(ReportServices.class.getResource(
                "/com/wutianyi/study/vcard/resource.csv").getFile()));
        System.out.println(createVcardFiles(datas, 1));
    }
}
