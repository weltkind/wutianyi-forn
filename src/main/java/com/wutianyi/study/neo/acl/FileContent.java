package com.wutianyi.study.neo.acl;

import static com.wutianyi.study.neo.acl.RelTypes.*;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.kernel.Traversal;

public class FileContent
{
    static String FILE_CONTENT = "file_content";
    private final Node underlyingNode;

    public FileContent(Node underlyingNode)
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
        return obj instanceof FileContent && ((FileContent) obj).getUnderlyingNode().equals(underlyingNode);
    }

    public void addToFold(FileContent parent)
    {
        Transaction tx = graphDb().beginTx();
        try
        {
            Relationship rs = getChildRelationShip(parent);
            if (null == rs)
            {
                parent.getUnderlyingNode().createRelationshipTo(underlyingNode, HAS_CHILD_CONTENT);
            }
            tx.success();
        }
        finally
        {
            tx.finish();
        }

    }

    private Relationship getChildRelationShip(FileContent parent)
    {
        Node parentNode = parent.getUnderlyingNode();

        for (Relationship rs : underlyingNode.getRelationships())
        {
            if (rs.getType().equals(HAS_CHILD_CONTENT))
            {
                if (rs.getOtherNode(underlyingNode).equals(parentNode))
                {
                    return rs;
                }
            }
        }
        return null;
    }

    private GraphDatabaseService graphDb()
    {
        return underlyingNode.getGraphDatabase();
    }

    public Iterable<FileContent> getParents()
    {
        TraversalDescription td = Traversal.description().depthFirst()
                .relationships(HAS_CHILD_CONTENT, Direction.INCOMING);
        return new IterableWrapper<FileContent, Path>(td.traverse(underlyingNode))
        {

            @Override
            protected FileContent underlyingObjectToObject(Path path)
            {
                return new FileContent(path.endNode());
            }
        };
    }
}
