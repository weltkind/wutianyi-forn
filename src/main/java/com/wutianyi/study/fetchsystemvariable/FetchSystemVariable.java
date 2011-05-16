package com.wutianyi.study.fetchsystemvariable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 在java中使用windows操作系统命令时要在前加cmd/c 否则 java会报错
 * 
 * @author hanjie.wuhj
 * 
 */
public class FetchSystemVariable {

	public static Map<String, String> getEnv() {
		Map<String, String> map = new HashMap<String, String>();
		String os = System.getProperty("os.name").toLowerCase();

		Process p = null;

		if (os.indexOf("windows") > -1) {
			try {
				p = Runtime.getRuntime().exec("cmd /c set");
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				String line;
				
				while((line = br.readLine()) != null) {
					String[] str = line.split("=");
					map.put(str[0], str[1]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return map;
	}
	public static void main(String[] args) {
		Map<String, String> map = getEnv();
		for(Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}
}
