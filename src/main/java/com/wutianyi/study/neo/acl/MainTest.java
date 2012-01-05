package com.wutianyi.study.neo.acl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class MainTest
{
    private GraphDatabaseService graphDb;
    private Index<Node> index;
    private PrincipalRepository repository;

    @BeforeClass
    public void setUp() throws IOException
    {
        File file = new File("acl");
        if (file.exists() && file.isDirectory())
        {
            FileUtils.deleteDirectory(file);
        }
        graphDb = new EmbeddedGraphDatabase("acl");
        index = graphDb.index().forNodes("Node");
        repository = new PrincipalRepository(graphDb, index);
        init();
    }

    private void init()
    {
        Principal allPrincipal = repository.createRootPrincipal("All principals");
    }

}
