package com.wutianyi.study.neo;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.TraversalPosition;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.Traversal;

public class MatrixTest
{
    private enum RelTypes implements RelationshipType
    {
        NEO_NODE, KNOWS, CODED_BY;
    }

    private static final String MATRIX_DB = "matrix-db";
    private static GraphDatabaseService graphDb;

    @BeforeClass
    public static void setUp()
    {
        deleteFileDirectory(new File(MATRIX_DB));
        graphDb = new EmbeddedGraphDatabase(MATRIX_DB);
        Main.registerShutdownHook(graphDb);
        createNodespace();
    }

    private static void createNodespace()
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            Node thomas = graphDb.createNode();
            thomas.setProperty("name", "Thomas Anderson");
            thomas.setProperty("age", 29);

            // connect Neo/Thomas to the reference node
            Node referenceNode = graphDb.getReferenceNode();
            referenceNode.createRelationshipTo(thomas, RelTypes.NEO_NODE);

            Node trinity = graphDb.createNode();
            trinity.setProperty("name", "Trinity");
            Relationship rel = thomas.createRelationshipTo(trinity, RelTypes.KNOWS);
            rel.setProperty("age", "3 days");
            Node morpheus = graphDb.createNode();
            morpheus.setProperty("name", "Morpheus");
            morpheus.setProperty("rank", "Captain");
            morpheus.setProperty("occupation", "Total badass");
            thomas.createRelationshipTo(morpheus, RelTypes.KNOWS);
            rel = morpheus.createRelationshipTo(trinity, RelTypes.KNOWS);
            rel.setProperty("age", "12 years");
            Node cypher = graphDb.createNode();
            cypher.setProperty("name", "Cypher");
            cypher.setProperty("last name", "Reagan");
            trinity.createRelationshipTo(cypher, RelTypes.KNOWS);
            rel = morpheus.createRelationshipTo(cypher, RelTypes.KNOWS);
            rel.setProperty("disclosure", "public");
            Node smith = graphDb.createNode();
            smith.setProperty("name", "Agent Smith");
            smith.setProperty("version", "1.0b");
            smith.setProperty("language", "C++");
            rel = cypher.createRelationshipTo(smith, RelTypes.KNOWS);
            rel.setProperty("disclosure", "secret");
            rel.setProperty("age", "6 months");
            Node architect = graphDb.createNode();
            architect.setProperty("name", "The Architect");
            smith.createRelationshipTo(architect, RelTypes.CODED_BY);

            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }

    private static Traverser getFriendsN(final Node person)
    {
        TraversalDescription td = Traversal.description().breadthFirst()
                .relationships(RelTypes.KNOWS, Direction.OUTGOING).evaluator(Evaluators.excludeStartPosition());

        Traversal.description().breadthFirst().relationships(RelTypes.KNOWS, Direction.OUTGOING)
                .relationships(RelTypes.CODED_BY, Direction.OUTGOING)
                .evaluator(Evaluators.returnWhereLastRelationshipTypeIs(RelTypes.CODED_BY));
        return td.traverse(person);
    }

    private static org.neo4j.graphdb.Traverser getFriends(final Node person)
    {
        return person.traverse(Order.BREADTH_FIRST, StopEvaluator.END_OF_GRAPH, ReturnableEvaluator.ALL_BUT_START_NODE,
                RelTypes.KNOWS, Direction.OUTGOING);
    }

    @Test
    public void printNeoFriends() throws Exception
    {
        Node neoNode = getNeoNode();
        System.out.println(neoNode.getProperty("name") + "'s friends:");
        // START SNIPPET: friends-usage
        org.neo4j.graphdb.Traverser friendsTraverser = getFriends(neoNode);
        int numberOfFriends = 0;
        for (Node friendNode : friendsTraverser)
        {
            System.out.println("At depth " + friendsTraverser.currentPosition().depth() + " => "
                    + friendNode.getProperty("name"));
            // END SNIPPET: friends-usage
            numberOfFriends++;
            // START SNIPPET: friends-usage
        }
        // END SNIPPET: friends-usage
        assertEquals(4, numberOfFriends);
    }

    // START SNIPPET: find-hackers
    private static org.neo4j.graphdb.Traverser findHackers(final Node startNode)
    {
        return startNode.traverse(Order.BREADTH_FIRST, StopEvaluator.END_OF_GRAPH, new ReturnableEvaluator()
        {
            @Override
            public boolean isReturnableNode(final TraversalPosition currentPos)
            {
                return !currentPos.isStartNode() && currentPos.lastRelationshipTraversed().isType(RelTypes.CODED_BY);
            }
        }, RelTypes.CODED_BY, Direction.OUTGOING, RelTypes.KNOWS, Direction.OUTGOING);
    }

    @Test
    public void printMatrixHackers() throws Exception
    {
        System.out.println("Hackers:");
        // START SNIPPET: find--hackers-usage
        org.neo4j.graphdb.Traverser traverser = findHackers(getNeoNode());
        int numberOfHackers = 0;
        for (Node hackerNode : traverser)
        {
            System.out.println("At depth " + traverser.currentPosition().depth() + " => "
                    + hackerNode.getProperty("name"));
            // END SNIPPET: find--hackers-usage
            numberOfHackers++;
            // START SNIPPET: find--hackers-usage
        }
        // END SNIPPET: find--hackers-usage
        assertEquals(1, numberOfHackers);
    }

    private static Node getNeoNode()
    {
        return graphDb.getReferenceNode().getSingleRelationship(RelTypes.NEO_NODE, Direction.OUTGOING).getEndNode();
    }

    private static void deleteFileDirectory(final File file)
    {
        if (!file.exists())
        {
            return;
        }
        if (file.isDirectory())
        {
            for (File child : file.listFiles())
            {
                deleteFileDirectory(child);
            }
        }
        else
        {
            file.delete();
        }
    }
}
