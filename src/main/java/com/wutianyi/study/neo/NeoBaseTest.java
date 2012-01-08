package com.wutianyi.study.neo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.transaction.TxModule;
import org.neo4j.kernel.impl.transaction.XaDataSourceManager;
import org.neo4j.kernel.impl.transaction.xaframework.XaDataSource;

public class NeoBaseTest {

	private static GraphDatabaseService graphDb;

	private Transaction tx;

	@BeforeClass
	public static void setUp() throws IOException {
		String path = "graphalgorithm";
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			FileUtils.deleteDirectory(file);
		}
		Map<String, String> config = new HashMap<String, String>();
		config.put("neostore.nodestore.db.mapped_memory", "10M");
		config.put("string_block_size", "60");
		config.put("array_block_size", "300");
		config.put("cache_type", "none");//soft, weak, strong
		graphDb = new EmbeddedGraphDatabase(path);
	}
	
	//对日志文件大小的调整
	protected void setLogicalLogSize()
	{
		TxModule txMoudle = ((EmbeddedGraphDatabase)graphDb).getConfig().getTxModule();
		XaDataSourceManager xsDsMgr = txMoudle.getXaDataSourceManager();
		XaDataSource xaDs = xsDsMgr.getXaDataSource("nioneodb");
		
		//turn off log rotation
		xaDs.setAutoRotate(false);
		//or to increase log target size to 100MB(default 10MB)
		xaDs.setLogicalLogTargetSize(100 * 1024 * 1024L);
	}
	

	@AfterClass
	public static void shutdown() {
		graphDb.shutdown();
	}

	@Before
	public void startTx() {
		tx = graphDb.beginTx();
	}

	@After
	public void endTx() {
		tx.finish();
		tx.success();
	}

	protected Node createNoe(String name, String value,
			Map<String, Object> parameters) {
		Node node = graphDb.createNode();
		if (null != parameters) {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				node.setProperty(entry.getKey(), entry.getValue());
			}
		}
		return node;
	}

	protected Relationship createRelationShip(Node a, Node b,
			RelationshipType type, Map<String, Object> parameters) {
		Relationship rs = a.createRelationshipTo(b, type);
		if (null != parameters) {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				rs.setProperty(entry.getKey(), entry.getValue());
			}
		}
		return rs;
	}

}
