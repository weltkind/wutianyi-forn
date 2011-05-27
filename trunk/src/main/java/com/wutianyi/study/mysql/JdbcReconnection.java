package com.wutianyi.study.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcReconnection {

	public static void main(String[] args) {
		try {
			Connection conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/work?autoReconnect=true&initialTimeout=5",
							"root", "860728");
			while (true) {
				try {
					Statement sm = conn.createStatement();
					ResultSet rs = sm.executeQuery("select * from noble");
					while (rs.next()) {
						System.out.println(rs.getString(1));
					}
					if (conn.isClosed()) {
						System.out.println("Close");
					} else {
						System.out.println("connection");
					}
					Thread.sleep(2000);
					sm.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
