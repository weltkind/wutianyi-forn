package com.wutianyi.study.neo.queryindex;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.WildcardQuery;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.index.impl.lucene.LuceneIndex;
import org.neo4j.index.lucene.QueryContext;
import org.neo4j.index.lucene.ValueContext;

import com.wutianyi.study.neo.analyzer.WordRelationShipRepository;

public class QueryMain extends QueryBase
{
    
    //query 的格式 type:ACTS_IN AND name:Neo
    @Test
    public void testGet()
    {
        Index<Node> indexNode = graphDb.index().forNodes("words");
        Index<Relationship> indexRelationShip = graphDb.index().forRelationships("relationship");
        
        //设置cache
        ((LuceneIndex<Node>)indexNode).setCacheCapacity("name", 30000);
        
        IndexHits<Node> nodes = indexNode.query(WordRelationShipRepository.NAME, "*目光*");
        for(Node node : nodes)
        {
            System.out.println(node.getProperty(WordRelationShipRepository.NAME) + " current score: " + nodes.currentScore());
        }
        
        //indexNode.add(null, null, new ValueContext(1999).indexNumeric());
        indexNode.query(QueryContext.numericRange(null, 1, 2).sort("").sortByScore());
        indexNode.query(new WildcardQuery(new Term("", "")));
    }
    
}
