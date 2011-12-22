package com.wutianyi.study.discoverygroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;
import org.xml.sax.SAXException;

import com.wutianyi.utils.OperatorTimes;
import com.wutianyi.utils.Pair;

public class SinaParser implements Parser
{

    private DocumentBuilder documentBuilder;
    private OperatorTimes times = new OperatorTimes();

    public SinaParser() throws ParserConfigurationException
    {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

    }

    public Pair<String, Pair<String, Integer>[]> parser(File file) throws SAXException, IOException
    {
        Pair<String, Pair<String, Integer>[]> results = null;
        if (null != file && file.isFile())
        {
            Document document = documentBuilder.parse(file);
            System.out.println(file.getName());
            System.out.print("Get Title Time");
            times.start();
            String title = getTitle(document);
            times.end();
            System.out.print("Get Word Time");
            times.start();
            Map<String, Integer> words = getWords(document);
            times.end();
            if (StringUtils.isNotBlank(title))
            {
                results = new Pair<String, Pair<String, Integer>[]>();
                Pair<String, Integer>[] wordRs = new Pair[words.size()];
                results.setFirst(title);
                int size = 0;
                for (Entry<String, Integer> entry : words.entrySet())
                {
                    wordRs[size] = new Pair<String, Integer>(entry.getKey(), entry.getValue());
                    ++size;
                }
                results.setSecond(wordRs);
            }

        }
        return results;
    }

    /**
     * 鑾峰彇鍏ㄩ儴鐨勫崟璇�
     * 
     * @param document
     * @return
     */
    private Map<String, Integer> getWords(Document document)
    {
        Map<String, Integer> words = new HashMap<String, Integer>();
        NodeList items = document.getElementsByTagName("item");
        int len = items.getLength();
        Filter filter = new NonCJKFilter();
        for (int i = 0; i < len; i++)
        {
            Node item = items.item(i);
            NodeList childs = item.getChildNodes();
            int l = childs.getLength();
            for (int j = 0; j < l; j++)
            {
                Node child = childs.item(j);
                if (child.getNodeType() == Node.ELEMENT_NODE
                        && (StringUtils.equalsIgnoreCase("title", child.getNodeName()) || StringUtils.equalsIgnoreCase(
                                "description", child.getNodeName())))
                {
                    analyse(words, CharacterFilterUtils.filter(filter, child.getTextContent()));
                }
            }

        }
        return words;
    }

    private void analyse(Map<String, Integer> words, String content)
    {
        IKSegmentation seg = new IKSegmentation(new StringReader(content), true);
        try
        {
            Lexeme lexeme = seg.next();
            while (null != lexeme)
            {
                String text = lexeme.getLexemeText();
                Integer count = words.get(text);
                if (null == count)
                {
                    count = new Integer(0);
                }
                ++count;
                words.put(text, count);
                lexeme = seg.next();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 鑾峰彇鍗氬鐨則itle
     * 
     * @param document
     * @return
     */
    private String getTitle(Document document)
    {
        String title = null;

        NodeList titles = document.getElementsByTagName("title");
        if (null != titles && titles.getLength() > 0)
        {
            title = titles.item(0).getTextContent();
        }

        return title;
    }

    /**
     * 构造基本结构
     * 
     * @param file
     * @param weibos
     * @param words
     * @throws SAXException
     * @throws IOException
     */
    private void initStruct(File file, Map<String, Pair<String, Integer>[]> weibos, Map<String, Integer> words)
            throws SAXException, IOException
    {
        Pair<String, Pair<String, Integer>[]> r = parser(file);
        if (null == r)
        {
            return;
        }
        weibos.put(r.getFirst(), r.getSecond());
        for (Pair<String, Integer> p : r.getSecond())
        {
            Integer count = words.get(p.getFirst());
            if (null == count)
            {
                count = new Integer(0);
            }
            ++count;
            words.put(p.getFirst(), count);
        }
    }

    /**
     * 根据基数对短语进行过滤
     * 
     * @param maps
     * @return
     */
    private Map<String, Integer> filterWords(Map<String, Integer> maps)
    {
        Map<String, Integer> words = new HashMap<String, Integer>();
        for (Entry<String, Integer> entry : maps.entrySet())
        {
            float factor = (float) (entry.getValue() / 5.0);
            if (factor > 0.2 && factor < 0.5)
            {
                words.put(entry.getKey(), entry.getValue());
            }
        }
        return words;
    }

    private void outputResult(Map<String, Pair<String, Integer>[]> weibos, Map<String, Integer> words, String output)
            throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(new File(output));
        pw.print("Blog");
        for (Entry<String, Integer> entry : words.entrySet())
        {
            pw.print("\t" + entry.getKey());
        }
        pw.println();
        for (Entry<String, Pair<String, Integer>[]> entry : weibos.entrySet())
        {
            pw.print(entry.getKey());
            Pair<String, Integer>[] pairs = entry.getValue();
            Set<String> set = new HashSet<String>();
            for (Pair<String, Integer> p : pairs)
            {
                set.add(p.getFirst());
            }
            for (Entry<String, Integer> word : words.entrySet())
            {
                if (set.contains(word.getKey()))
                {
                    pw.print("\t" + word.getValue());
                }
                else
                {
                    pw.print("\t0");
                }

            }
            pw.println();
        }
        pw.flush();
        pw.close();
    }

    public void parserFiles(File[] files, String outputFile) throws SAXException, IOException
    {
        Map<String, Pair<String, Integer>[]> weibos = new HashMap<String, Pair<String, Integer>[]>();
        Map<String, Integer> maps = new HashMap<String, Integer>();
        for (File f : files)
        {
            initStruct(f, weibos, maps);
        }
        Map<String, Integer> words = filterWords(maps);
        outputResult(weibos, words, outputFile);

    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        File file = new File("file/rss");
        File[] files = file.listFiles();
        Parser parser = new SinaParser();
        parser.parserFiles(files, "result.txt");
    }
}
