package com.wutianyi.study.mysqlslap.parser.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wutianyi.study.mysqlslap.configuration.ColumnConfiguration;
import com.wutianyi.study.mysqlslap.configuration.TableConfiguration;
import com.wutianyi.study.mysqlslap.parser.ParseTableConfiguration;

/**
 * ����spring������ʵ������table����Ϣ
 * @author hanjie.wuhj
 *
 */
@SuppressWarnings("unchecked")
public class SpringXmlParseTableConfigurationImpl implements
		ParseTableConfiguration {

	private final static String[] tableConfigurations = new String[] { "classpath:/table/*_table.xml" };

	private static List<TableConfiguration> tablesConfiguration;

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				tableConfigurations);
		Map<String, TableConfiguration> map = context
				.getBeansOfType(TableConfiguration.class);

		if (null != map) {
			Collection<TableConfiguration> tempConfigurations = map.values();
			tablesConfiguration = new ArrayList<TableConfiguration>();
			for (TableConfiguration tableConfiguration : tempConfigurations) {
				tablesConfiguration.add(tableConfiguration);
			}

			tablesConfiguration = Collections
					.unmodifiableList(tablesConfiguration);
		}
	}

	public List<TableConfiguration> parseTablesConfiguration() {
		if (null != tablesConfiguration) {
			return tablesConfiguration;
		}

		return Collections.EMPTY_LIST;
	}
	
	public static void main(String[] args) {
		SpringXmlParseTableConfigurationImpl parse = new SpringXmlParseTableConfigurationImpl();
		List<TableConfiguration> configurations = parse.parseTablesConfiguration();
		for(TableConfiguration table : configurations) {
			System.out.println(table.getDatabaseName());
			System.out.println(table.getTableName());
			System.out.println(table.getSqlNums());
			List<ColumnConfiguration> columns = table.getColumnsConfig();
			for(ColumnConfiguration c : columns) {
				System.out.println(c.getColumnName());
				System.out.println(c.getColumnSize());
				System.out.println(c.getType());
			}
		}
	}

}
