package com.wutianyi.study.neo.acl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.traversal.Evaluator;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.kernel.Traversal;

public class PrincipalRepository
{
    private final GraphDatabaseService graphDb;
    private final Index<Node> index;
    private final Node principals;
    private final Node folders;

    public PrincipalRepository(GraphDatabaseService graphDb, Index<Node> index)
    {
        this.graphDb = graphDb;
        this.index = index;
        principals = getRootNode(graphDb, RelTypes.PRINCIPALS, Principal.NAME, "principals");
        folders = getRootNode(graphDb, RelTypes.CONTENT_ROOTS, "folders", "folders");
    }

    public Principal createRootPrincipal(String name) throws Exception
    {
        Principal rootPrincipal = createPrincipal(name);
        Transaction tx = graphDb.beginTx();
        try
        {
            rootPrincipal.getUnderlyingNode().setProperty("type", "root");
            index.add(rootPrincipal.getUnderlyingNode(), Principal.NAME, "root_group");
            tx.success();
            return rootPrincipal;
        }
        finally
        {
            tx.success();
        }
    }

    public Iterable<Principal> getNumberGroups(Principal principal)
    {
        TraversalDescription td = Traversal.description().depthFirst()
                .relationships(RelTypes.IS_MEMBER_OF_GROUP, Direction.OUTGOING).evaluator(Evaluators.excludeStartPosition());
        Traverser t = td.traverse(principal.getUnderlyingNode());
        return new IterableWrapper<Principal, Path>(t)
        {

            @Override
            protected Principal underlyingObjectToObject(Path object)
            {
                return new Principal(object.endNode());
            }
        };

    }

    public Iterable<Principal> getRootGroups()
    {
        IndexHits<Node> hits = index.get(Principal.NAME, "root");
        return new IterableWrapper<Principal, Node>(hits)
        {

            @Override
            protected Principal underlyingObjectToObject(Node node)
            {
                return new Principal(node);
            }
        };
    }

    public Principal getPrincipalByName(String name)
    {
        Node principalNode = index.get(Principal.NAME, name).getSingle();
        if (null == principalNode)
        {
            throw new IllegalArgumentException("Principal[" + name + "] not found");
        }
        return new Principal(principalNode);
    }

    /**
     * 创建一个principal
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public Principal createPrincipal(String name) throws Exception
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            Node newPrincipal = graphDb.createNode();
            principals.createRelationshipTo(newPrincipal, RelTypes.PRINCIPAL);
            Node alreadyExist = index.get(Principal.NAME, name).getSingle();
            if (null != alreadyExist)
            {
                tx.failure();
                throw new Exception("Principal with this name already exist");
            }
            newPrincipal.setProperty(Principal.NAME, name);
            index.add(newPrincipal, Principal.NAME, name);
            tx.success();
            return new Principal(newPrincipal);

        }
        finally
        {
            tx.finish();
        }
    }

    private Node getRootNode(GraphDatabaseService graphDb, RelTypes relType, String name, String value)
    {
        Relationship relationShip = graphDb.getReferenceNode().getSingleRelationship(relType, Direction.OUTGOING);
        if (null != relationShip)
        {
            return relationShip.getEndNode();
        }
        else
        {
            Transaction tx = graphDb.beginTx();
            try
            {
                Node principals = graphDb.createNode();
                principals.setProperty(name, value);

                graphDb.getReferenceNode().createRelationshipTo(principals, relType);
                return principals;
            }
            finally
            {
                tx.success();
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        File dbFile = new File("acl");
        FileUtils.deleteDirectory(dbFile);
        
        
    }
}
