package com.wutianyi.study.mysqlslap.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class AutoDowmloadTables {
	static Connection conn = null;
	static MessageFormat insertMessageFormat = null;
	static Map<String, Integer> map = new HashMap<String, Integer>();
	static String selectSql = null;
	static String insertSql = null;
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss.S");
	static NumberFormat numberFormat = NumberFormat.getNumberInstance();
	static PrintWriter pw = null;
	static {
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			conn = DriverManager.getConnection(
//					"jdbc:mysql://10.20.36.26:3306/india", "india", "india");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@10.20.36.20:1521:oindev", "dict",
 "dict");
			try {
				pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File("sql.txt"))));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void getColumns(String tableName) {
		numberFormat.setGroupingUsed(false);
		test();
		try {
			map = new LinkedHashMap<String, Integer>();

			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet rs = metadata.getColumns("oindev", "dict",
					tableName, "%");

			StringBuilder insertBuild = new StringBuilder("select ");
			StringBuilder updateBuild = new StringBuilder("insert into "
					+ tableName + "(");
			while (rs.next()) {
				insertBuild.append(rs.getString("COLUMN_NAME") + ",");
				updateBuild.append(rs.getString("COLUMN_NAME") + ",");
				map.put(rs.getString("COLUMN_NAME"), rs.getInt("DATA_TYPE"));
				System.out.println(rs.getString("COLUMN_NAME") + " : "
						+ rs.getInt("DATA_TYPE"));
			}
			StringBuilder type = new StringBuilder();
			int size = map.size();
			for (int i = 0; i < size; i++) {
				type.append("{" + i + "},");
			}
			if (StringUtils.isBlank(type.toString())) {
				System.out.println("null");
				return;
			}
			String valueType = type.substring(0, type.lastIndexOf(","));
			selectSql = insertBuild.substring(0, insertBuild.lastIndexOf(","))
					+ " from " + tableName + " rownum < 10";

			insertSql = updateBuild.substring(0, updateBuild.lastIndexOf(","))
					+ " ) values" + "( " + valueType + ");";
			insertMessageFormat = new MessageFormat(insertSql);
			System.out.println(selectSql);
			System.out.println(insertSql);
			downloadData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void downloadData() {
		try {
			Statement sm = conn.createStatement();
			ResultSet rs = sm.executeQuery(selectSql);
			while (rs.next()) {
				Set<String> keySet = map.keySet();
				Object[] values = new Object[keySet.size()];
				int i = 0;
				for (String key : keySet) {
					if (map.get(key) == Types.VARCHAR) {
						values[i] = "'" + rs.getString(key) + "'";
					} else if (map.get(key) == Types.INTEGER) {
						values[i] = numberFormat.format(rs.getInt(key));
					} else {
						values[i] = "'" + dateFormat.format(rs.getTime(key))
								+ "'";
					}
					i++;
				}
				pw.println(insertMessageFormat.format(values));
				System.out.println(insertMessageFormat.format(values));
			}
			pw.flush();
			pw.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void test() {
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("select count(*) from attribute_value");
			while (rs.next()) {
				System.out.println(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		getColumns("tl_commodity");
	}
}
