package com.wutianyi.study.neo.acl;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class Principal
{
    /**
     * 建立索引的名称
     */
    static String NAME = "principal";

    private final Node underlyingNode;

    public Principal(Node underlyingNode)
    {
        this.underlyingNode = underlyingNode;
    }

    public Node getUnderlyingNode()
    {
        return underlyingNode;
    }

    @Override
    public int hashCode()
    {
        return underlyingNode.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Principal && ((Principal) obj).getUnderlyingNode().equals(underlyingNode);
    }

    private GraphDatabaseService graphDb()
    {
        return underlyingNode.getGraphDatabase();
    }

    public void addNumberShip(Principal group)
    {
        Transaction tx = graphDb().beginTx();
        try
        {
            Relationship rs = getNumberRelationShipTo(group);
            if (null == rs)
            {
                rs = underlyingNode.createRelationshipTo(group.getUnderlyingNode(), RelTypes.IS_MEMBER_OF_GROUP);
            }
            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }

    /**
     * 
     * @param group
     * @return
     */
    private Relationship getNumberRelationShipTo(Principal group)
    {
        Node groupNode = group.getUnderlyingNode();
        for (Relationship relationShip : underlyingNode.getRelationships())
        {
            if (relationShip.getOtherNode(underlyingNode).equals(groupNode))
            {
                return relationShip;
            }
        }
        return null;
    }
}
