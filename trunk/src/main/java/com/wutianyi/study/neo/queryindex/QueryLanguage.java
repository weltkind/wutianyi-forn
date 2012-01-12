package com.wutianyi.study.neo.queryindex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class QueryLanguage
{

    private static ExecutionEngine engine;
    private static GraphDatabaseService graphDb;

    @BeforeClass
    public static void setUp() throws IOException
    {
        graphDb = new EmbeddedGraphDatabase("worddb");
        engine = new ExecutionEngine(graphDb);
    }

    @AfterClass
    public static void shutdown()
    {
        graphDb.shutdown();
    }

    @Test
    public void testParameters()
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "目光");

        // ExecutionResult result =
        // engine.execute("start n=node(0,1,2) where n.name={name} return n",
        // params);
        ExecutionResult result = engine.execute("START n=node(1,2,3) RETURN n");
        // start r=relationshipe(0) return r
        // start n=node:words()

        System.out.println(result.columns());
        System.out.println(result.toString());
        result = engine.execute("start n=node:words(word=\"目光\") return n");
        System.out.println(result.toString());
        System.out.println(result.toString());

        result = engine.execute("start n=node(3) match (n)--(x) return x");
        System.out.println(result.toString());
        result = engine.execute("start n=node(3) match (n)-->(x) return x");
        System.out.println(result.toString());
        result = engine.execute("start n=node(3) match (n)-[r]->() return r");
        System.out.println(result.toString());
        result = engine.execute("start n=node(3) match (n)-[r:NEXT_WORD]->(x) return r");
        System.out.println(result.toString());

        result = engine.execute("start a=node(3) match (a)-[:NEXT_WORD]->(b)-[:NEXT_WORD]->(c) return a,b,c");
        System.out.println(result.toString());

        result = engine.execute("start a=node(3),x=node(9,5) match p1=a-[*1..3]->x return a,x,length(p1),p1");
        System.out.println(result.toString());

        result = engine.execute("start a=node(3),x=node(9,5) match p1=shortestPath(a-[*..3]->x) return p1");
        System.out.println(result.toString());

        result = engine.execute("start a=node(4),x=node(9,5) match p1=a-[r:NEXT_WORD]->x return a,x,length(p1),p1,r.count");
        System.out.println(result.toString());

        // start n=node(3,1) where (n.age<30 and a.name="") or not(n.name="")
        // return n
        // start n=node(3,1) where n.name=~/Tob.*/ return n
        // start n=node(3) match (n)-[r]->() where type[r]=~/K.*/ return r
        // start n=node(3,1) where n.belt return n
        // start n=node(3,1) where n.belt? ='white' return n
        // start n=node(1),b=node(3,2) match a<-[r?]-b where r is null return b
        // start n=node(1) return n.name
        // start a=node(1) match (a)-->(b) return distinct b
        // start n=node(2) match (n)-->(x) return n, count(*)
        // start n=node(1,2,3,4) return count(n.property?)
        // start n=node(2,3,4) return sum(n.property)
        // start n=node(2,3,4) return avg(n.property)
        // start n=node(2,3,4) return max(n.property)
        // start n=node(2,3,4) return min(n.property)
        // start n=node(1,2,3,4) return n order by n.name skip 3
        // start n=node(1,2,3,4) return n order by n.name skip 1 limit 2
    }
}
