package com.wutianyi.study.collaborative;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class Recommendation {

	private static Map<String, Map<String, Float>> critics = new HashMap<String, Map<String, Float>>();

	static {
		critics = getCritics();
	}

	private static Map<String, Map<String, Float>> getCritics() {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, HashMap<String, Float>>> t = new TypeReference<HashMap<String, HashMap<String, Float>>>() {
		};
		Map<String, Map<String, Float>> rs = null;
		try {
			rs = mapper.readValue(new File("dictionary"), t);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 欧几里得距离评价
	 * 越接近1表示越相似
	 * @return
	 */
	public static float euclideanDistanceScore(String person1, String person2) {
		Map<String, Float> map1 = critics.get(person1);
		Map<String, Float> map2 = critics.get(person2);
		float sum = 0.0f;
		for (Entry<String, Float> entry : map1.entrySet()) {
			if (null != map2.get(entry.getKey())) {
				sum += Math.abs(entry.getValue() - map2.get(entry.getKey()));
			}
		}
		sum = (float) Math.sqrt(sum);
		return 1.0f / (1.0f + sum);
	}

	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException {
		System.out.println(euclideanDistanceScore("Lisa Rose", "Toby"));
	}
}
