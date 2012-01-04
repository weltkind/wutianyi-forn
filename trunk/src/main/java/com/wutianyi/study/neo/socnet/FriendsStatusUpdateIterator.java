package com.wutianyi.study.neo.socnet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.neo4j.helpers.collection.PositionedIterator;

public class FriendsStatusUpdateIterator implements Iterator<StatusUpdate>
{

    private ArrayList<PositionedIterator<StatusUpdate>> statuses = new ArrayList<PositionedIterator<StatusUpdate>>();
    private StatusUpdateComparator comparator = new StatusUpdateComparator();

    public FriendsStatusUpdateIterator(Person person)
    {
        for (Person friend : person.getFriends())
        {
            Iterator<StatusUpdate> iterator = friend.getStatus().iterator();
            if (iterator.hasNext())
            {
                statuses.add(new PositionedIterator<StatusUpdate>(iterator));
            }
        }
        sort();
    }

    @Override
    public boolean hasNext()
    {
        return statuses.size() > 0;
    }

    @Override
    public StatusUpdate next()
    {
        if (statuses.size() == 0)
        {
            throw new NoSuchElementException();
        }
        PositionedIterator<StatusUpdate> first = statuses.get(0);
        StatusUpdate returnVal = first.current();
        if (!first.hasNext())
        {
            statuses.remove(0);
        }
        else
        {
            first.next();
            sort();
        }
        return returnVal;
    }

    private void sort()
    {
        Collections.sort(statuses, comparator);
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Don't know how to do that...");

    }

    private class StatusUpdateComparator implements Comparator<PositionedIterator<StatusUpdate>>
    {

        @Override
        public int compare(PositionedIterator<StatusUpdate> a, PositionedIterator<StatusUpdate> b)
        {
            return a.current().getDate().compareTo(b.current().getDate());
        }

    }
}
