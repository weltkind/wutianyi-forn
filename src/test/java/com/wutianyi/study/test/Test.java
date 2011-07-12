package com.wutianyi.study.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Test {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("tl_commodity_attr_value_rel.txt"),"utf8"));
			String line = reader.readLine();
			int i = 0;
			int count = 0;
			while (null != line) {
				i ++;
				count ++;
				if(i == 8 ) {
					if(!line.equals("------------------------------------------------")) {
						
						System.out.println(count);
						break;
					} else {
						i = 0;
					}
				}
				line = reader.readLine();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
