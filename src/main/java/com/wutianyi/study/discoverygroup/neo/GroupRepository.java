package com.wutianyi.study.discoverygroup.neo;

import static com.wutianyi.study.discoverygroup.neo.RelTypes.*;

import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

import com.wutianyi.utils.Pair;

public class GroupRepository
{

    GraphDatabaseService graphDb;

    Node authorRoot;
    Node wordRoot;
    Index<Node> authorsIndex;
    Index<Node> wordsIndex;

    public GroupRepository(GraphDatabaseService graphDb)
    {
        this.graphDb = graphDb;
        this.authorsIndex = graphDb.index().forNodes(Constants.AUTHOR_INDEX_NAME);
        this.wordsIndex = graphDb.index().forNodes(Constants.WORD_INDEX_NAME);

        authorRoot = authorsIndex.get(Constants.AUTHOR_INDEX_P_1, Constants.AUTHOR_ROOT).getSingle();
        if (null == authorRoot)
        {
            createNode(graphDb.getReferenceNode(), authorsIndex, AUTHOR_ROOT, Constants.AUTHOR_INDEX_P_1,
                    Constants.AUTHOR_ROOT, null);
        }
        wordRoot = wordsIndex.get(Constants.WORD_INDEX_P_1, Constants.WORD_ROOT).getSingle();
        if (null == wordRoot)
        {
            createNode(graphDb.getReferenceNode(), wordsIndex, WORD_ROOT, Constants.WORD_INDEX_P_1,
                    Constants.WORD_ROOT, null);
        }
    }

    /**
     * 创建节点 节点存在、检查是否与父节点有相应的关系、not 则创建改关系
     * 
     * @param parentNode
     * @param index
     * @param reltType
     * @param name
     * @param value
     * @param params
     * @return
     */
    Node createNode(Node parentNode, Index<Node> index, RelTypes relType, String name, String value,
            Map<String, Object> params)
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            Node node = createNodeCondition(parentNode, index, relType, name, value, params);
            tx.success();
            return node;
        }
        finally
        {
            tx.finish();
        }
    }

    /**
     * 方法不能单独使用，必须嵌套到相应的事务中去
     * 
     * @param parentNode
     * @param index
     * @param relType
     * @param name
     * @param value
     * @param params
     * @return
     */
    private Node createNodeCondition(Node parentNode, Index<Node> index, RelTypes relType, String name, String value,
            Map<String, Object> params)
    {

        Node node = index.get(name, value).getSingle();
        // 检查节点是否已经存在
        if (null != node)
        {
            // 检查并且创建相应的关系
            Relationship rs = getRelationShipTo(parentNode, node, relType);
            if (null == rs)
            {
                parentNode.createRelationshipTo(node, relType);
            }
        }
        else
        {
            node = graphDb.createNode();
            node.setProperty(name, value);
            parentNode.createRelationshipTo(node, relType);
            index.add(node, name, value);
        }
        if (null != params)
        {
            for (Entry<String, Object> p : params.entrySet())
            {
                node.setProperty(p.getKey(), p.getValue());
            }
        }
        return node;
    }

    /**
     * 将单词保存起来
     * @param words
     * @return
     */
    public Pair<Integer, Node>[] createWordNodes(Map<String, Integer> words)
    {
        Pair<Integer, Node>[] pairs = new Pair[words.size()];
        Transaction tx = graphDb.beginTx();
        try
        {
            int i = 0;
            for (Entry<String, Integer> word : words.entrySet())
            {
                Node node = createNodeCondition(wordRoot, wordsIndex, WORD, Constants.WORD_INDEX_P_1, word.getKey(),
                        null);
                pairs[i] = new Pair<Integer, Node>(word.getValue(), node);
                ++ i;
            }
            tx.success();
            return pairs;
        }
        finally
        {
            tx.success();
        }
    }

    /**
     * 将信息保存起来
     * @param name
     * @param words
     */
    public void storeInformations(String name, Map<String, Integer> words)
    {
        Author author = getAuthor(name);
        Pair<Integer, Node>[] pairs = createWordNodes(words);
        author.createRelationTo(pairs);
    }

    /**
     * 检查指定节点间中指定的关系
     * 
     * @param parentNode
     * @param node
     * @param relType
     * @return
     */
    private Relationship getRelationShipTo(Node parentNode, Node node, RelTypes relType)
    {
        for (Relationship rs : parentNode.getRelationships())
        {
            if (rs.getType().equals(relType) && rs.getOtherNode(parentNode).equals(node))
            {
                return rs;
            }
        }
        return null;
    }

    /**
     * 获取指定的作者，如果没有则创建相应的节点
     * 
     * @param name
     * @return
     */
    public Author getAuthor(String name)
    {
        Node node = authorsIndex.get(Constants.WORD_INDEX_P_1, name).getSingle();
        if (null == node)
        {
            node = createNode(authorRoot, authorsIndex, WORD, Constants.AUTHOR_INDEX_P_1, name, null);
        }
        Author author = new Author(node);
        return author;
    }
    public static void main(String[] args)
    {
        
    }
}
