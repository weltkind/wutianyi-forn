package com.wutianyi.study.neo.acl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class MainTest
{
    private static GraphDatabaseService graphDb;
    private static Index<Node> index;
    private static PrincipalRepository repository;
    private static Principal allPrincipal;

    @BeforeClass
    public static void setUp() throws Exception
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

    private static void init() throws Exception
    {
        allPrincipal = repository.createRootPrincipal("All principals");

        Principal root = repository.createPrincipal("root");
        root.addNumberShip(allPrincipal);

        Principal regularUsers = repository.createPrincipal("Regular users");
        regularUsers.addNumberShip(allPrincipal);

        Principal user1 = repository.createPrincipal("user1");
        user1.addNumberShip(regularUsers);

        Principal user2 = repository.createPrincipal("user2");
        user2.addNumberShip(regularUsers);

        FileContent fileRoot = repository.createRootFileContent("Root folder");
        FileContent temp = repository.createFileContent("Temp", fileRoot);
        FileContent home = repository.createFileContent("Home", fileRoot);
        FileContent user1Home = repository.createFileContent("user1 Home", home);
        repository.createFileContent("My File.pdf", user1Home);

        repository.addSecurity(allPrincipal, fileRoot, (byte) 1);
        repository.addSecurity(allPrincipal, temp, (byte) 2);
        repository.addSecurity(root, fileRoot, (byte) 2);
        repository.addSecurity(regularUsers, user1Home, (byte) 0);
        repository.addSecurity(user1, user1Home, (byte) 3);
    }

    @Test
    public void testUser1()
    {
        Principal user1 = repository.getPrincipalByName("user1");
        assertNotNull(user1);
        Iterable<Principal> groups = repository.getNumberGroups(user1);
        for (Principal group : groups)
        {
            System.out.println("depth: " + group.getUnderlyingNode().getProperty(Principal.NAME));
        }
        System.out.println("=================");
    }

    @Test
    public void testUser2()
    {
        Principal user2 = repository.getPrincipalByName("user2");
        assertNotNull(user2);
        Iterable<Principal> groups = repository.getNumberGroups(user2);
        for (Principal group : groups)
        {
            System.out.println("depth: " + group.getUnderlyingNode().getProperty(Principal.NAME));
        }
        System.out.println("=================");
    }

    @Test
    public void testRoot()
    {
        Principal root = repository.getPrincipalByName("root");
        assertNotNull(root);
        Iterable<Principal> groups = repository.getNumberGroups(root);
        for (Principal group : groups)
        {
            System.out.println("depth: " + group.getUnderlyingNode().getProperty(Principal.NAME));
        }
        System.out.println("=================");
    }
    
    @Test
    public void testFileParent()
    {
        FileContent fileContent = repository.getFileContentByName("My File.pdf");
        Iterable<FileContent> parents = fileContent.getParents();
        for(FileContent f : parents)
        {
            System.out.println(f.getUnderlyingNode().getProperty(FileContent.FILE_CONTENT));
        }
    }

    @Test
    public void testUser2Security()
    {
        Principal user2 = repository.getPrincipalByName("user2");
        FileContent fileContent = repository.getFileContentByName("My File.pdf");
        byte flag = repository.getSecurityFlags(user2, fileContent);
        assertEquals(1, flag);
    }
    
    @AfterClass
    public static void clear() throws IOException
    {
        graphDb.shutdown();
        File file = new File("acl");
        if (file.exists() && file.isDirectory())
        {
            FileUtils.deleteDirectory(file);
        }
    }

    public static void main(String[] args)
    {
        System.out.println(Byte.parseByte("011", 10));
    }
}
