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
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.ReadableIndex;
import org.neo4j.kernel.Config;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.transaction.TxModule;
import org.neo4j.kernel.impl.transaction.XaDataSourceManager;
import org.neo4j.kernel.impl.transaction.xaframework.XaDataSource;

public class NeoBaseTest {

	private static GraphDatabaseService graphDb;

	private Transaction tx;

	private Index<Node> index;
	
	@BeforeClass
	public static void setUp() throws IOException {
		String path = "graphalgorithm";
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			FileUtils.deleteDirectory(file);
		}
		Map<String, String> config = new HashMap<String, String>();
		config.put(Constants.USE_MEMORY_MAPPED_BUFFERS, "true");
		config.put(Constants.NEOSTORE_NODESTORE_DB_MAPPED_MEMORY, "10M");
		config.put(Constants.NEOSTORE_RELATIONSHIPSTORE_DB_MAPPED_MEMORY, "20M");
		config.put(Constants.NEOSTORE_PROPERTYSTORE_DB_MAPPED_MEMORY, "10M");
		config.put(Constants.NEOSTORE_PROPERTYSTORE_DB_INDEX_KEYS_MAPPED_MEMORY, "5M");
		config.put(Constants.NEOSTORE_PROPERTYSTORE_DB_INDEX_MAPPED_MEMORY, "5M");
		config.put(Constants.NEOSTORE_PROPERTYSTORE_DB_STRINGS_MAPPED_MEMORY, "30M");
		config.put(Constants.DUMP_CONFIGURATION, "true");
		graphDb = new EmbeddedGraphDatabase(path, config);
		
		//配置index的参数
		Map<String, String> indexConfig = new HashMap<String, String>();
		indexConfig.put(IndexManager.PROVIDER, "lucene");
		indexConfig.put("type", "fulltext");//exact or fulltext
		indexConfig.put("to_lower_case", "true");//false or true
		//indexConfig.put("analyzer","${className}");
		indexConfig.put(Config.NODE_KEYS_INDEXABLE, "name,count");
		indexConfig.put(Config.NODE_AUTO_INDEXING, "true");
		ReadableIndex<Node> index = graphDb.index().getNodeAutoIndexer().getAutoIndex();
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
