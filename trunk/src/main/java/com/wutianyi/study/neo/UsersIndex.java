package com.wutianyi.study.neo;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class UsersIndex
{

    private static GraphDatabaseService graphDb = new EmbeddedGraphDatabase("neo/");
    private static Index<Node> nodeIndex = graphDb.index().forNodes("nodes");

    public static void main(String[] args)
    {
        Main.registerShutdownHook(graphDb);
        Transaction tx = graphDb.beginTx();
        try
        {
            Node usersReferenceNode = graphDb.createNode();
            graphDb.getReferenceNode().createRelationshipTo(usersReferenceNode, RelTypes.USERS_REFERENCE);
            for (int id = 0; id < 100; id++)
            {
                Node userNode = createAndIndexUser(idToUserName(id));
                usersReferenceNode.createRelationshipTo(userNode, RelTypes.USER);
            }
            tx.success();
        }
        finally
        {
            tx.finish();
        }
        int idToFind = 45;
        Node foundUser = nodeIndex.get("username_key", idToUserName(idToFind)).getSingle();
        System.out.println("The username of user " + idToFind + " is" + foundUser.getProperty("username_key"));
        Main.deleteAllNode(graphDb);
    }

    private static enum RelTypes implements RelationshipType
    {
        USERS_REFERENCE, USER;
    }

    private static String idToUserName(final int id)
    {
        return "user" + id + "@neo4j.org";
    }

    private static Node createAndIndexUser(final String username)
    {
        Node node = graphDb.createNode();
        node.setProperty("username_key", username);
        nodeIndex.add(node, "username_key", username);
        return node;
    }
}
