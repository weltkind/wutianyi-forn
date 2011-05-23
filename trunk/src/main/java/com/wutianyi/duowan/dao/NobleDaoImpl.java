package com.wutianyi.duowan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NobleDaoImpl implements NobleDao{
	
	private List<Connection> dbConns;
	
	private static final int DB_CONNS_NUM = 5;
	private volatile int curIndex = 0;
	
	private String insertSql = "insert into noble values(?)";
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void initDbConns() {
		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String password = "860728";
		dbConns = new ArrayList<Connection>();
		
		for(int i = 0; i < DB_CONNS_NUM; i ++) {
			
			try {
				Connection conn = DriverManager.getConnection(url, user, password);
				dbConns.add(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConnection() {
		Connection conn = dbConns.get(curIndex);
		curIndex = (++ curIndex) % DB_CONNS_NUM;
		return conn;
	}
	
	public void insertNoble(String name) {
		Connection conn = getConnection();
		try {
			PreparedStatement sm = conn.prepareStatement(insertSql);
			sm.setString(1, name);
			boolean result = sm.execute();
			if(!result) {
				//FIXME!
				System.out.println("The username " + name + " can not insert into noble");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkUserExist(String username) {
		// TODO Auto-generated method stub
		return false;
	}

}
