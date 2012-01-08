package com.wutianyi.study.neo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.EstimateEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.kernel.Traversal;

public class GraphAlgorithmMain extends NeoBaseTest {

	@Test
	public void testAstar() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("x", 0d);
		parameters.put("y", 0d);
		Node nodeA = createNoe("name", "A", parameters);

		parameters.clear();
		parameters.put("x", 7d);
		parameters.put("y", 0d);
		Node nodeB = createNoe("name", "B", parameters);

		parameters.clear();
		parameters.put("x", 2d);
		parameters.put("y", 1d);
		Node nodeC = createNoe("name", "C", parameters);

		parameters.clear();
		parameters.put("length", 10d);
		Relationship relAB = createRelationShip(nodeA, nodeB, RelTypes.MY_TYPE,
				parameters);
		parameters.clear();
		parameters.put("length", 3d);
		Relationship relBC = createRelationShip(nodeC, nodeB, RelTypes.MY_TYPE,
				parameters);
		parameters.clear();
		parameters.put("length", 2d);
		Relationship relAC = createRelationShip(nodeA, nodeC, RelTypes.MY_TYPE,
				parameters);

		EstimateEvaluator<Double> estimateEvaluator = new EstimateEvaluator<Double>() {

			@Override
			public Double getCost(Node node, Node goal) {
				double dx = (Double) node.getProperty("x")
						- (Double) goal.getProperty("y");
				double dy = (Double) node.getProperty("y")
						- (Double) goal.getProperty("y");
				return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			}
		};
		PathFinder<WeightedPath> astar = GraphAlgoFactory.aStar(Traversal
				.expanderForAllTypes(), CommonEvaluators
				.doubleCostEvaluator("length"), estimateEvaluator);
		WeightedPath path = astar.findSinglePath(nodeA, nodeB);
		System.out.println(path.length());
		System.out.println(path.weight());
	}
	

	public enum RelTypes implements RelationshipType {
		MY_TYPE
	}
}
