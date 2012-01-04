package com.wutianyi.study.neo.socnet;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

public class PersonRepository
{
    private final GraphDatabaseService graphDb;
    private final Index<Node> index;
    private final Node personRefNode;

    public PersonRepository(GraphDatabaseService graphDb, Index<Node> index)
    {
        this.graphDb = graphDb;
        this.index = index;
        personRefNode = getPersonsRootNode(graphDb);
    }

    public Person createPerson(String name) throws Exception
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            Node newPersonNode = graphDb.createNode();
            personRefNode.createRelationshipTo(newPersonNode, RelTypes.A_PERSON);
            Node alreadyExist = index.get(Person.NAME, name).getSingle();
            if (alreadyExist != null)
            {
                tx.failure();
                throw new Exception("Person with this name already exists ");
            }
            newPersonNode.setProperty(Person.NAME, name);
            index.add(newPersonNode, Person.NAME, name);
            tx.success();
            return new Person(newPersonNode);
        }
        finally
        {
            tx.finish();
        }
    }

    public Person getPersonByName(String name)
    {
        Node personNode = index.get(Person.NAME, name).getSingle();
        if (null == personNode)
        {
            throw new IllegalArgumentException("Person[" + name + "] not found");
        }
        return new Person(personNode);
    }

    public void deletePerson(Person person)
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            Node personNode = person.getUnderlyingNode();
            index.remove(personNode, Person.NAME, person.getName());
            for(Person friend : person.getFriends())
            {
                person.removeFriend(friend);
            }
            personNode.getSingleRelationship(RelTypes.A_PERSON, Direction.INCOMING).delete();
            for(StatusUpdate status : person.getStatus())
            {
                Node statusNode = status.getUnderlyingNode();
                for(Relationship r : statusNode.getRelationships())
                {
                    r.delete();
                }
                statusNode.delete();
            }
            personNode.delete();
            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }
    
    private Node getPersonsRootNode(GraphDatabaseService graphDb)
    {
        Relationship rel = graphDb.getReferenceNode().getSingleRelationship(RelTypes.PEF_PERSONS, Direction.OUTGOING);
        if (rel != null)
        {
            return rel.getEndNode();
        }
        else
        {
            Transaction tx = this.graphDb.beginTx();
            try
            {
                Node refNode = this.graphDb.createNode();
                this.graphDb.getReferenceNode().createRelationshipTo(refNode, RelTypes.PEF_PERSONS);
                tx.success();
                return refNode;
            }
            finally
            {
                tx.success();
            }
        }
    }
}
