package com.wutianyi.study.ibatis;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public class SampleTest extends SqlMapClientDaoSupport implements
		SampleTestInterface {

	public void testUpdate() {

		// this.getSqlMapClientTemplate().update("testUpdateName");
		// this.getSqlMapClientTemplate().update("testUpdateId");
		this.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback<Integer>() {

					public Integer doInSqlMapClient(SqlMapExecutor execute)
							throws SQLException {
						execute.startBatch();
						int i = execute.update("testUpdateName");

						System.err.println("ok");
						execute.update("testUpdateId");
						System.err.println("finish");
						execute.executeBatch();
						return null;
					}
				});
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"ibatis/test-context.xml");
		SampleTestInterface test = (SampleTestInterface) context
				.getBean("simpleTest");
		test.testUpdate();
	}
}
