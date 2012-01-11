package com.wutianyi.study.neo.queryindex;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;

public class QueryMain extends QueryBase
{

    public void testGet()
    {
        Index<Node> indexNode = graphDb.index().forNodes("words");
        Index<Relationship> indexRelationShip = graphDb.index().forRelationships("relationship");
        
    }
    
}
