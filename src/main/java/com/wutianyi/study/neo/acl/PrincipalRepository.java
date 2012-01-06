package com.wutianyi.study.neo.acl;

import static com.wutianyi.study.neo.acl.RelTypes.*;

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
        folders = getRootNode(graphDb, RelTypes.CONTENT_ROOTS, FileContent.FILE_CONTENT, "folders");
    }

    public void addSecurity(Principal principal, FileContent fileContent, byte flag)
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            Relationship rs = getSecurityRelationShip(principal, fileContent);
            if (rs == null)
            {
                rs = principal.getUnderlyingNode().createRelationshipTo(fileContent.getUnderlyingNode(), SECURITY);
            }
            rs.setProperty("flags", flag);
            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }

    public byte getSecurityFlags(Principal principal, FileContent fileContent)
    {

        Iterable<Principal> principals = getNumberGroups(principal);
        Iterable<FileContent> parents = fileContent.getParents();
        byte flag = 0;

        for (Principal p : principals)
        {
            for (FileContent f : parents)
            {
                Iterable<Relationship> rss = p.getUnderlyingNode().getRelationships(SECURITY, Direction.OUTGOING);
                for (Relationship rs : rss)
                {
                    if (rs.getOtherNode(p.getUnderlyingNode()).equals(f.getUnderlyingNode()))
                    {
                        flag = (byte) ((Byte) rs.getProperty("flags") | flag);
                    }
                }
            }
        }

        return flag;
    }

    private Relationship getSecurityRelationShip(Principal principal, FileContent fileContent)
    {
        Node principalNode = principal.getUnderlyingNode();
        Node fileContentNode = fileContent.getUnderlyingNode();

        for (Relationship rs : principalNode.getRelationships())
        {
            if (rs.getType().equals(SECURITY))
            {
                if (rs.getOtherNode(principalNode).equals(fileContentNode))
                {
                    return rs;
                }
            }
        }
        return null;
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
                .relationships(RelTypes.IS_MEMBER_OF_GROUP, Direction.OUTGOING);
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

    public FileContent getFileContentByName(String name)
    {
        Node fileContentNode = index.get(FileContent.FILE_CONTENT, name).getSingle();
        if (null == fileContentNode)
        {
            throw new IllegalArgumentException("FileContent[" + name + "] not found");
        }
        return new FileContent(fileContentNode);
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

    public FileContent createFileContent(String name, FileContent parent) throws Exception
    {
        return createFileContent(name, parent, HAS_CHILD_CONTENT);
    }

    public FileContent createFileContent(String name, FileContent parent, RelTypes relType) throws Exception
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            Node newFileContent = graphDb.createNode();
            newFileContent.setProperty(FileContent.FILE_CONTENT, name);
            parent.getUnderlyingNode().createRelationshipTo(newFileContent, relType);
            Node existNode = index.get(FileContent.FILE_CONTENT, name).getSingle();
            if (null != existNode)
            {
                tx.failure();
                throw new Exception("The file content name already exist!");
            }
            newFileContent.setProperty(FileContent.FILE_CONTENT, name);
            index.add(newFileContent, FileContent.FILE_CONTENT, name);
            tx.success();
            return new FileContent(newFileContent);
        }
        finally
        {
            tx.finish();
        }
    }

    public FileContent createRootFileContent(String name) throws Exception
    {
        return createFileContent(name, new FileContent(folders), CONTENT_ROOT);
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
        byte a = 0;
        byte b = 1;
        System.out.println(a | b);
    }
}
