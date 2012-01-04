package com.wutianyi.study.neo.socnet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;

import com.wutianyi.study.neo.socnet.RelTypes;

public class Person
{
    static final String NAME = "name";

    private final Node underlyingNode;

    public Person(Node personNode)
    {
        this.underlyingNode = personNode;
    }

    public Node getUnderlyingNode()
    {
        return underlyingNode;
    }

    public String getName()
    {
        return (String) underlyingNode.getProperty(NAME);
    }

    @Override
    public int hashCode()
    {
        return underlyingNode.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Person && underlyingNode.equals(((Person) o).getUnderlyingNode());
    }

    @Override
    public String toString()
    {
        return "Person[" + getName() + "]";
    }

    public void addFriend(Person otherPerson)
    {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        try
        {
            if (!this.equals(otherPerson))
            {
                Relationship friendRel = getFriendRelationshipTo(otherPerson);
                if (null == friendRel)
                {
                    underlyingNode.createRelationshipTo(otherPerson.getUnderlyingNode(), RelTypes.FRIEND);
                }
                tx.success();
            }
        }
        finally
        {
            tx.finish();
        }
    }

    public int getNrOfFriends()
    {
        return IteratorUtil.count(getFriends());
    }

    public Iterable<Person> getFriends()
    {
        return getFriendsByDepth(1);
    }

    public void removeFriend(Person otherPerson)
    {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        try
        {
            if (!this.equals(otherPerson))
            {
                Relationship friendRel = getFriendRelationshipTo(otherPerson);
                if (friendRel != null)
                {
                    friendRel.delete();
                }
                tx.success();
            }
        }
        finally
        {
            tx.finish();
        }
    }

    public Iterable<Person> getFriendsOfFriends()
    {
        return getFriendsByDepth(2);
    }

    public Iterable<Person> getShortestPathTo(Person otherPerson, int maxDepth)
    {
        PathFinder<Path> finder = GraphAlgoFactory.shortestPath(
                Traversal.expanderForTypes(RelTypes.FRIEND, Direction.BOTH), maxDepth);
        Path path = finder.findSinglePath(underlyingNode, otherPerson.getUnderlyingNode());
        return createPersonsFromNodes(path);
    }

    public Iterable<Person> getFriendRecommendation(int numberOfFriendsToReturn)
    {
        HashSet<Person> friends = new HashSet<Person>();
        IteratorUtil.addToCollection(getFriends(), friends);

        HashSet<Person> friendsOfFriends = new HashSet<Person>();
        IteratorUtil.addToCollection(getFriendsOfFriends(), friendsOfFriends);
        friendsOfFriends.removeAll(friends);

        ArrayList<RankedPerson> rankedFriends = new ArrayList<RankedPerson>();
        for (Person friend : friendsOfFriends)
        {
            int rank = getNumberOfPathsToPerson(friend);
            rankedFriends.add(new RankedPerson(friend, rank));
        }
        Collections.sort(rankedFriends, new RankedComparer());
        trimTo(rankedFriends, numberOfFriendsToReturn);
        return onlyFriend(rankedFriends);
    }

    public Iterator<StatusUpdate> friendStatuses()
    {
        return new FriendsStatusUpdateIterator(this);
    }

    public Iterable<StatusUpdate> getStatus()
    {
        Relationship firstStatus = underlyingNode.getSingleRelationship(RelTypes.STATUS, Direction.OUTGOING);
        if (firstStatus == null)
        {
            return Collections.emptyList();
        }
        TraversalDescription traversal = Traversal.description().depthFirst().relationships(RelTypes.NEXT)
                .filter(Traversal.returnAll());
        return new IterableWrapper<StatusUpdate, Path>(traversal.traverse(firstStatus.getEndNode()))
        {

            @Override
            protected StatusUpdate underlyingObjectToObject(Path object)
            {
                return new StatusUpdate(object.endNode());
            }
        };
    }

    public void addStatus(String text)
    {
        Transaction tx = graphDb().beginTx();
        try
        {
            StatusUpdate oldStatus;
            if (getStatus().iterator().hasNext())
            {
                oldStatus = getStatus().iterator().next();
            }
            else
            {
                oldStatus = null;
            }
            Node newStatus = createNewStatusNode(text);
            if (oldStatus != null)
            {
                underlyingNode.getSingleRelationship(RelTypes.STATUS, Direction.OUTGOING).delete();
                newStatus.createRelationshipTo(oldStatus.getUnderlyingNode(), RelTypes.NEXT);
            }
            underlyingNode.createRelationshipTo(newStatus, RelTypes.STATUS);
            tx.success();
        }
        finally
        {
            tx.finish();
        }

    }

    private Node createNewStatusNode(String text)
    {
        Node newStatus = graphDb().createNode();
        newStatus.setProperty(StatusUpdate.TEXT, text);
        newStatus.setProperty(StatusUpdate.DATE, new Date().getTime());
        return newStatus;
    }

    private GraphDatabaseService graphDb()
    {
        return underlyingNode.getGraphDatabase();
    }

    private Iterable<Person> onlyFriend(Iterable<RankedPerson> rankedFriends)
    {
        ArrayList<Person> retVal = new ArrayList<Person>();
        for (RankedPerson person : rankedFriends)
        {
            retVal.add(person.getPerson());
        }
        return retVal;
    }

    private void trimTo(ArrayList<RankedPerson> rankedFriends, int numberOfFriendsToReturn)
    {
        while (rankedFriends.size() > numberOfFriendsToReturn)
        {
            rankedFriends.remove(rankedFriends.size() - 1);
        }
    }

    private Relationship getFriendRelationshipTo(Person otherPerson)
    {
        Node otherNode = otherPerson.getUnderlyingNode();
        for (Relationship rel : underlyingNode.getRelationships())
        {
            if (rel.getOtherNode(underlyingNode).equals(otherNode))
            {
                return rel;
            }
        }
        return null;
    }

    private Iterable<Person> getFriendsByDepth(int depth)
    {
        TraversalDescription travDesc = Traversal.description().breadthFirst().relationships(RelTypes.FRIEND)
                .uniqueness(Uniqueness.NODE_GLOBAL).prune(Traversal.pruneAfterDepth(depth))
                .filter(Traversal.returnAllButStartNode());
        return createPersonsFromPath(travDesc.traverse(underlyingNode));
    }

    private IterableWrapper<Person, Path> createPersonsFromPath(Traverser iterableToWrap)
    {
        return new IterableWrapper<Person, Path>(iterableToWrap)
        {

            @Override
            protected Person underlyingObjectToObject(Path object)
            {
                return new Person(object.endNode());
            }
        };
    }

    private Iterable<Person> createPersonsFromNodes(final Path path)
    {
        return new IterableWrapper<Person, Node>(path.nodes())
        {

            @Override
            protected Person underlyingObjectToObject(Node object)
            {
                return new Person(object);
            }
        };
    }

    private final class RankedPerson
    {
        final Person person;
        final int rank;

        private RankedPerson(Person person, int rank)
        {
            this.person = person;
            this.rank = rank;
        }

        public Person getPerson()
        {
            return person;
        }

        public int getRank()
        {
            return rank;
        }
    }

    private class RankedComparer implements Comparator<RankedPerson>
    {

        @Override
        public int compare(RankedPerson a, RankedPerson b)
        {
            return b.getRank() - a.getRank();
        }

    }

    private int getNumberOfPathsToPerson(Person otherPerson)
    {
        PathFinder<Path> finder = GraphAlgoFactory.allPaths(
                Traversal.expanderForTypes(RelTypes.FRIEND, Direction.BOTH), 2);
        Iterable<Path> paths = finder.findAllPaths(underlyingNode, otherPerson.getUnderlyingNode());
        return IteratorUtil.count(paths);
    }
}
