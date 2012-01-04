package com.wutianyi.study.neo.socnet;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;

public class StatusUpdate
{
    private final Node underlyingNode;
    static final String TEXT = "TEXT";
    static final String DATE = "DATE";

    public StatusUpdate(Node underlyingNode)
    {
        this.underlyingNode = underlyingNode;
    }

    public Node getUnderlyingNode()
    {
        return underlyingNode;
    }

    public Person getPerson()
    {
        return new Person(getPersonNode());
    }
    
    private Node getPersonNode()
    {
        TraversalDescription traversalDescription = Traversal.description().depthFirst()
                .relationships(RelTypes.NEXT, Direction.INCOMING).relationships(RelTypes.STATUS, Direction.INCOMING)
                .filter(Traversal.returnWhereLastRelationshipTypeIs(RelTypes.STATUS));
        Traverser traversal = traversalDescription.traverse(underlyingNode);

        return IteratorUtil.singleOrNull(traversal.iterator()).endNode();
    }

    public String getText()
    {
        return (String) underlyingNode.getProperty(TEXT);
    }

    public String getDate()
    {
        return (String) underlyingNode.getProperty(DATE);
    }

}
