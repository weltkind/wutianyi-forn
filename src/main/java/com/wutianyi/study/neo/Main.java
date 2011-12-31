package com.wutianyi.study.neo;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class Main
{
    public static void main(String[] args)
    {
        GraphDatabaseService graphDb = new EmbeddedGraphDatabase("neo/");
        registerShutdownHook(graphDb);
        Transaction tx = graphDb.beginTx();
        try
        {
            Node firstNode = graphDb.createNode();
            Node secondNode = graphDb.createNode();
            Relationship relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
            firstNode.setProperty("message", "Hello, ");
            secondNode.setProperty("message", "world!");
            relationship.setProperty("message", "brave Neo4j");
            tx.success();
            for (Node node : graphDb.getAllNodes())
            {
                if (node.hasProperty("message"))
                {
                    System.out.println(node.getProperty("message"));
                }
                for (Relationship rel : node.getRelationships())
                {
                    System.out.println(rel.getProperty("message"));
                    rel.delete();
                }
                node.delete();
            }
            tx.success();
        }
        finally
        {
            tx.finish();
        }

    }

    private static enum RelTypes implements RelationshipType
    {
        KNOWS
    }

    public static void deleteAllNode(GraphDatabaseService graphDb)
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            for (Node node : graphDb.getAllNodes())
            {
                for (Relationship rel : node.getRelationships())
                {
                    rel.delete();
                }
                node.delete();
            }
            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }

    public static void registerShutdownHook(final GraphDatabaseService graphDb)
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {

            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        });
    }
}
