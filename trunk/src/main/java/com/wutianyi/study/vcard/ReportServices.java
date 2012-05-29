package com.wutianyi.study.vcard;

import info.ineighborhood.cardme.vcard.VCardImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
        {}), extend, splitlength);
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

    public static void main(String[] args) throws IOException
    {
        TestParser testParser = new TestParser();
        List<VCardImpl> vcards = testParser.importVCards();

        List<VcardWrapper> vs = new ArrayList<VcardWrapper>();
        for (VCardImpl v : vcards)
        {
            vs.add(new VcardWrapper(v));
        }

        ReportDO report = getReport(1);
        ReportDO newReport = Utils.createReportDO(vs, report);

        System.out.println(newReport.getReportHeader());
        System.out.println(report.getReportHeader());
    }
}
