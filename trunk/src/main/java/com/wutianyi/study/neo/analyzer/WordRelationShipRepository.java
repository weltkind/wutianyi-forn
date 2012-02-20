package com.wutianyi.study.neo.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.wutianyi.study.discoverygroup.SinaParser;
import com.wutianyi.utils.OperatorTimes;

/**
 * 需要定义好相关事务的大小问题
 * 
 * @author hanjie.wuhj
 * 
 */
public class WordRelationShipRepository
{

    private static final String ROOT_WORD = "root_word";

    /**
     * word节点中保存的名称
     */
    public static final String NAME = "word";
    /**
     * word 中next 关系中的次数 
     */
    static final String COUNT = "count";

    private GraphDatabaseService graphDb;

    private Index<Node> index;

    private Index<Relationship> relationIndex;

    private Node root;

    private OperatorTimes operatorTimes = new OperatorTimes();

    public WordRelationShipRepository(GraphDatabaseService graphDb)
    {
        this.graphDb = graphDb;
        // graphDb = new EmbeddedGraphDatabase(DATA_PATH);
        // 关闭相应的数据库
        // Runtime.getRuntime().addShutdownHook(new Thread()
        // {
        // @Override
        // public void run()
        // {
        // graphDb.shutdown();
        // }
        // });
        index = graphDb.index().forNodes("words");
        relationIndex = graphDb.index().forRelationships("relationship");
        // 拿到根节点
        root = index.get(NAME, ROOT_WORD).getSingle();
        if (null == root)
        {
            try
            {
                Transaction tx = graphDb.beginTx();
                try
                {
                    root = graphDb.createNode();
                    root.setProperty(NAME, ROOT_WORD);
                    index.add(root, NAME, ROOT_WORD);
                    // 创建节点之间的关系
                    graphDb.getReferenceNode().createRelationshipTo(root, RelTypes.ROOT_WORDS);
                }
                finally
                {
                    tx.success();
                    tx.finish();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将一个有关联的单词组添加到图中
     * 
     * @param c
     */
    public void addWords(Collection<String> c)
    {
        operatorTimes.start();
        if (null == c || c.size() == 0)
        {
            return;
        }
        if (1 == c.size())
        {
            getNodeByWord(c.iterator().next());
        }
        else
        {
            int i = 0;
            String word = null;
            String nextWord = null;
            for (String w : c)
            {
                if (i == 0)
                {
                    word = w;
                    nextWord = w;
                    i++;
                    continue;
                }
                else
                {
                    word = nextWord;
                }
                nextWord = w;
                operatorTimes.start();
                getWordRelationShips(word, nextWord);
                System.out.print("GetWordRelationShips ");
                operatorTimes.end();
            }
        }
        System.out.print("AddWords Total ");
        operatorTimes.end();
    }

    /**
     * 获取两个单词之间的关系
     * 
     * @param word
     * @param nextWord
     * @return
     */
    private Relationship getWordRelationShips(String word, String nextWord)
    {
        Node wordNode = getNodeByWord(word);
        Node nextWordNode = getNodeByWord(nextWord);

        Relationship relationShip = null;
        // 获取两个单词之家的联系
        if (wordNode.hasRelationship())
        {
            for (Relationship rs : wordNode.getRelationships())
            {
                if (rs.getOtherNode(wordNode).equals(nextWordNode))
                {
                    relationShip = rs;
                    break;
                }
            }
        }

        // 创建关系并且设置关系中的count属性的值
        relationShip = createRelationShip(wordNode, nextWordNode, relationShip);
        return relationShip;
    }

    /**
     * 如果relationship是null则创建关系 修改关系的值
     * 
     * @param word
     * @param nextWord
     * @param relationShip
     * @return
     */
    private Relationship createRelationShip(Node word, Node nextWord, Relationship relationShip)
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            if (null == relationShip)
            {
                relationShip = word.createRelationshipTo(nextWord, RelTypes.NEXT_WORD);
                relationShip.setProperty(NAME, word.getProperty(NAME) + "_" + nextWord.getProperty(NAME));
                relationIndex.add(relationShip, NAME, word.getProperty(NAME) + "_" + nextWord.getProperty(NAME));
            }
            relationShip.setProperty(COUNT, (Integer) relationShip.getProperty(COUNT, 0) + 1);
            tx.success();
            return relationShip;
        }
        finally
        {
            tx.finish();
        }
    }

    /**
     * 根据word去获取节点
     * 
     * @param word
     * @return
     */
    private Node getNodeByWord(String word)
    {
        Node node = index.get(NAME, word).getSingle();

        if (null == node)
        {
            try
            {
                node = createWordNode(NAME, word, null);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return node;
    }

    /**
     * 创建节点
     * 
     * @param name
     * @param value
     * @param parameters
     * @return
     * @throws Exception
     */
    private Node createWordNode(String name, String value, Map<String, Object> parameters) throws Exception
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            Node node = graphDb.createNode();
            node.setProperty(name, value);
            Node existNode = index.get(name, value).getSingle();
            if (null != existNode)
            {
                tx.failure();
                throw new Exception("The name" + name + " of the node already exist!");
            }
            if (null != parameters)
            {
                for (Entry<String, Object> entry : parameters.entrySet())
                {
                    node.setProperty(entry.getKey(), entry.getValue());
                }
            }
            root.createRelationshipTo(node, RelTypes.WORD);
            index.add(node, name, value);
            tx.success();
            return node;
        }
        finally
        {
            tx.finish();
        }
    }

    private enum RelTypes implements RelationshipType
    {
        ROOT_WORDS, WORD, NEXT_WORD;
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        String dbPath = "worddb";
        final GraphDatabaseService graphDb = new EmbeddedGraphDatabase(dbPath);
        SinaParser parser = new SinaParser();
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        });
        WordRelationShipRepository repository = new WordRelationShipRepository(graphDb);
        File file = new File("file/rss");
        File[] files = file.listFiles();
        int sum = 0;
        for (File f : files)
        {
            if (f.isDirectory())
            {
                continue;
            }
            Document document = documentBuilder.parse(f);
            Map<String, Integer> words = parser.getWords(document);
            repository.addWords(words.keySet());
            sum += words.size();
        }
        System.out.println("total words: " + sum);
        // List<String> test = new ArrayList<String>();
        // test.add("TEst_1");
        // test.add("test_2");
        // repository.addWords(test);
    }

}
