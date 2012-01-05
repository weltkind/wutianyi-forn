package com.wutianyi.study.neo.acl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class MainTest {
	private GraphDatabaseService graphDb;
	private Index<Node> index;
	private PrincipalRepository repository;
	private Principal allPrincipal;

	@Before
	public void setUp() throws Exception {
		File file = new File("acl");
		if (file.exists() && file.isDirectory()) {
			FileUtils.deleteDirectory(file);
		}
		graphDb = new EmbeddedGraphDatabase("acl");
		index = graphDb.index().forNodes("Node");
		repository = new PrincipalRepository(graphDb, index);
		init();
	}

	private void init() throws Exception {
		allPrincipal = repository.createRootPrincipal("All principals");
		repository.createPrincipal("root").addNumberShip(allPrincipal);
		Principal regularUsers = repository.createPrincipal("Regular users");
		regularUsers.addNumberShip(allPrincipal);
		repository.createPrincipal("user1").addNumberShip(regularUsers);
		repository.createPrincipal("user2").addNumberShip(regularUsers);
	}
	@Test
	public void testUser1() {
		Principal user1 = repository.getPrincipalByName("user1");
		assertNotNull(user1);
		Iterable<Principal> groups = repository.getNumberGroups(user1);
		for(Principal group : groups)
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
		for(Principal group : groups)
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
		for(Principal group : groups)
		{
			System.out.println("depth: " + group.getUnderlyingNode().getProperty(Principal.NAME));
		}
	}
	@After
	public void clear() throws IOException
	{
		graphDb.shutdown();
		File file = new File("acl");
		if (file.exists() && file.isDirectory()) {
			FileUtils.deleteDirectory(file);
		}
	}
}
