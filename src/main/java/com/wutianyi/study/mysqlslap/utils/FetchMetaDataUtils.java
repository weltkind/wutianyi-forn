package com.wutianyi.study.mysqlslap.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wutianyi.study.mysqlslap.metadata.ColumnMetaData;
import com.wutianyi.study.mysqlslap.metadata.TableMetaData;
import com.wutianyi.study.mysqlslap.pool.PoolObject;
import com.wutianyi.study.mysqlslap.pool.PoolsFactory;

public class FetchMetaDataUtils {

	private final static String COLUMN_NAME = "COLUMN_NAME";
	private final static String COLUMN_SIZE = "COLUMN_SIZE";
	private final static String NULLABLE = "NULLABLE";
	private final static String TYPE_NAME = "TYPE_NAME";
	private final static int NULLABLE_VALUE = 0;
	private final static Map<String, TableMetaData> tablesMetaData = new HashMap<String, TableMetaData>();

	public static List<String> fetchAllTableMetaData(String dataBase) {
		PoolObject<Connection> connection = PoolsFactory.getConnection();
		List<String>tables = new ArrayList<String>();	
		try {
			DatabaseMetaData dataBaseMetaData = connection.getPoolObject().getMetaData();
			ResultSet rs = dataBaseMetaData.getTables(dataBase, dataBase, "%", new String[]{"TABLE"});
			while(rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tables;
	}

	/**
	 * @param dataBase
	 * @param table
	 * @return
	 * 根据数据和表名称获取表的原始信息
	 */
	@SuppressWarnings("unchecked")
	public static TableMetaData fetchTableMetaData(String dataBase, String table) {

		PoolObject<Connection> connection = PoolsFactory.getConnection();
		TableMetaData tableMetaData = tablesMetaData
				.get(dataBase + "_" + table);
		if (null != tableMetaData) {
			return tableMetaData;
		}
		try {
			tableMetaData = new TableMetaData();
			//数据库的原始信息
			DatabaseMetaData metaData = connection.getPoolObject()
					.getMetaData();
			//获取主键的信息
			ResultSet rs = metaData.getPrimaryKeys(dataBase, dataBase, table);

			fillPrimaryInfo(tableMetaData, rs);
			//获取表的全部的列的元数据信息
			rs = metaData.getColumns(dataBase, dataBase, table, "%");
			fillColumnInfo(tableMetaData, rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		tablesMetaData.put(dataBase + "_" + table, tableMetaData);
		return tableMetaData;
	}

	/**
	 * @param tableMetaData
	 * @param rs
	 * @throws SQLException
	 * 填充列的元信息
	 */
	private static void fillColumnInfo(TableMetaData tableMetaData, ResultSet rs)
			throws SQLException {
		while (rs.next()) {
			System.out.println(rs.getString(COLUMN_NAME));
			ColumnMetaData columnMetaData = new ColumnMetaData(rs
					.getString(COLUMN_NAME), rs.getInt(COLUMN_SIZE), (rs
					.getInt(NULLABLE) == NULLABLE_VALUE) ? false : true, rs
					.getString(TYPE_NAME));
			tableMetaData.addColumnMetaData(columnMetaData);
		}
	}

	/**
	 * @param tableMetaData
	 * @param rs
	 * @throws SQLException
	 * 填充主键的信息
	 */
	private static void fillPrimaryInfo(TableMetaData tableMetaData,
			ResultSet rs) throws SQLException {
		while (rs.next()) {
			tableMetaData.addPrimaryKey(rs.getString(COLUMN_NAME));
		}
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		
		TableMetaData tableMetaData = FetchMetaDataUtils.fetchTableMetaData(
				"guangyuan", "product");
		List<String> list = tableMetaData.getPrimaryKeys();
		for (String key : list) {
			System.out.println(key);
		}

		Collection<ColumnMetaData> columns = tableMetaData.getColumnsName();
		for (ColumnMetaData column : columns) {
			System.out.println(column.getName());
			System.out.println(column.isNullable());
			System.out.println(column.getColumnSize());
			System.out.println(column.getTypeName());
		}
		
		System.out.println("-------------------------");
		System.out.println(FetchMetaDataUtils.fetchAllTableMetaData("guangyuan"));
		
		System.exit(0);
		// Class.forName("com.mysql.jdbc.Driver");
		// Connection conn = DriverManager.getConnection(
		// "jdbc:mysql://localhost:3306/guangyuan", "root", "860728");
		// Statement sm = conn.createStatement();
		// ResultSet rs_1 = sm.executeQuery("select * from test where b='2'");
		// String value = null;
		// while (rs_1.next()) {
		//
		// System.out.println(rs_1.getString(1));
		// System.out.println(rs_1.getString(2));
		// System.out.println(rs_1.getString(3));
		// value = rs_1.getString(3);
		// }
		//
		// // sm.executeUpdate("insert into test values(2,2,'" + value + "')");
		//
		// DatabaseMetaData metaData = conn.getMetaData();
		// ResultSet rs = metaData.getPrimaryKeys("guangyuan", null, "test");
		// ResultSetMetaData rsMD = rs.getMetaData();
		// int i = rsMD.getColumnCount();
		// System.out.println(i);
		// while (rs.next()) {
		// for (int j = 1; j <= i; j++) {
		// System.out.print(rsMD.getColumnName(j) + " : ");
		// System.out.println(rs.getString(j));
		//
		// }
		// }

	}
}
