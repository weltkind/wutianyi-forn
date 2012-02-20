package com.wutianyi.study.discoverygroup.neo;

import static com.wutianyi.study.discoverygroup.neo.RelTypes.*;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import com.wutianyi.utils.Pair;

public class Author
{

    private final Node underlyingNode;

    public Author(Node underlyingNode)
    {
        this.underlyingNode = underlyingNode;
    }

    /**
     * 创建与单词的关系
     * @param pairs
     */
    public void createRelationTo(Pair<Integer, Node>[] pairs)
    {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        try
        {
            for (Pair<Integer, Node> words : pairs)
            {
                Relationship rs = getRelationShipTo(words.getSecond());
                if (null == rs)
                {
                    rs = underlyingNode.createRelationshipTo(words.getSecond(), USE);
                }
                rs.setProperty(Constants.AUTHOR_USE_WORD_COUNT,
                        ((Integer) rs.getProperty(Constants.AUTHOR_USE_WORD_COUNT, 0)) + words.getFirst());
            }
        }
        finally
        {
            tx.finish();
        }
    }

    /**
     * 获取作者与词之间的关系
     * 
     * @param wordNode
     * @return
     */
    private Relationship getRelationShipTo(Node wordNode)
    {
        for (Relationship rs : underlyingNode.getRelationships())
        {
            if (rs.getType().equals(USE) && rs.getOtherNode(underlyingNode).equals(wordNode))
            {
                return rs;
            }
        }

        return null;
    }
}
