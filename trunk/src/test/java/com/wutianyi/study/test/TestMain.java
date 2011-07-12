package com.wutianyi.study.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TestMain {
	public static void main(String[] args) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/work", "root", "860728");
			
			String insert_sql = "insert into tl_commodity_attr_value_rel(id,commodity_id,attribute_id,value_ids, value_ids_1,value_ids_2,value_ids_3,gmt_create,gmt_modified)values(?,?,?,?,?,?,?,now(),now())";
			PreparedStatement ps = conn.prepareStatement(insert_sql);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("tl_commodity_attr_value_rel.txt"),"utf8"));
			String line = reader.readLine();
			int i = 0;
			int count = 0;
			while(null != line) {
				i ++;
				count ++;
				if(i == 3) {
					ps.setInt(2, Integer.valueOf(line));
				}
				if(i == 1){
					ps.setString(4, line);
				}
				if(i == 2) {
					ps.setInt(3, Integer.valueOf(line));
				}
				if(i == 4) {
					ps.setInt(1, Integer.valueOf(line));
				}
				if(i == 5) {
					ps.setString(7, line);
				}
				if(i == 6) {
					ps.setString(6, line);
					
				}
				if(i == 7) {
					ps.setString(5, line);
				}
				if(i == 8) {
					ps.addBatch();
					i = 0;
				}
				if(count == 500) {
					ps.executeBatch();
					count = 0;
				}
				
				line = reader.readLine();
			}
			ps.executeBatch();
		} catch (SQLException e) {
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
