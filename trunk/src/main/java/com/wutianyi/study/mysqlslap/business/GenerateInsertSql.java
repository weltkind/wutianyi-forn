package com.wutianyi.study.mysqlslap.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.wutianyi.study.mysqlslap.configuration.ColumnConfiguration;
import com.wutianyi.study.mysqlslap.configuration.ColumnsType;
import com.wutianyi.study.mysqlslap.configuration.TableConfiguration;
import com.wutianyi.study.mysqlslap.metadata.ColumnMetaData;
import com.wutianyi.study.mysqlslap.metadata.TableMetaData;
import com.wutianyi.study.mysqlslap.parser.ParseTableConfiguration;
import com.wutianyi.study.mysqlslap.parser.impl.SpringXmlParseTableConfigurationImpl;
import com.wutianyi.study.mysqlslap.utils.FetchMetaDataUtils;
import com.wutianyi.study.mysqlslap.utils.SqlTemplateUtils;

/**
 * @author hanjie.wuhj
 * 
 */
public class GenerateInsertSql {

	/**
	 * ���ⲿ�ṩ����4��ȡtable����Ϣ,���������õ���Ϣί�и������
	 */
	private ParseTableConfiguration parserTableConfiguration;
	private static Logger logger = Logger.getLogger(GenerateInsertSql.class);
	private List<TableConfiguration> tableConfigurations = null;

	/**
	 * �ⲿ�ӿڣ����insert sql
	 */
	public List<String> process() {
		List<TableConfiguration> tablesConfiguration = parseTablesConfiguration();

		for (TableConfiguration tableConfiguration : tablesConfiguration) {
			// ��֤������Ϣ�Ƿ���ȷ��ͬʱ��������û�����õ��е�Ĭ����Ϣ
			if (validatorTablesConfiguration(tableConfiguration)) {
				return generateSqls(tableConfiguration);
			}

		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * �������õı����Ϣ
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<TableConfiguration> parseTablesConfiguration() {
		if (null == parserTableConfiguration) {
			if (null != tableConfigurations) {
				return tableConfigurations;
			}
			return Collections.EMPTY_LIST;
		}
		return parserTableConfiguration.parseTablesConfiguration();
	}

	/**
	 * ��ȡ���ԭʼ��Ϣ
	 * 
	 * @return
	 */
	private TableMetaData fetchTableMetaData(
			TableConfiguration tableConfiguration) {
		if (null == tableConfiguration) {
			return null;
		}

		return FetchMetaDataUtils.fetchTableMetaData(tableConfiguration
				.getDatabaseName(), tableConfiguration.getTableName());
	}

	/**
	 * ��֤������Ϣ�Ƿ����
	 * 
	 * @param tableConfiguration
	 * @param tableMetaData
	 * @return
	 */
	private boolean validatorTablesConfiguration(
			TableConfiguration tableConfiguration) {
		// ��ȡ���ԭʼ�����Ϣ
		TableMetaData tableMetaData = fetchTableMetaData(tableConfiguration);

		boolean result = true;

		result &= validatorColumns(tableConfiguration, tableMetaData);
		result &= validatorNull(tableConfiguration, tableMetaData);
		result &= validatorDuplicate(tableConfiguration, tableMetaData);
		result &= validatorSize(tableConfiguration, tableMetaData);

		fillAllColumnsConfiguration(tableConfiguration, tableMetaData);
		return true;
	}

	/**
	 * У�����õ����Ƿ���ȷ
	 * 
	 * @param tableConfiguration
	 * @param tableMetaData
	 * @return
	 */
	private boolean validatorColumns(TableConfiguration tableConfiguration,
			TableMetaData tableMetaData) {
		List<String> columnsName = new ArrayList<String>();
		List<ColumnConfiguration> columns = tableConfiguration
				.getColumnsConfig();

		for (ColumnConfiguration column : columns) {
			columnsName.add(column.getColumnName());
		}
		if (!tableMetaData.containAllColumnNames(columnsName)) {
			return false;
		}

		return true;
	}

	/**
	 * ��֤�����Ƿ��ں���ķ�Χ��
	 * 
	 * @param tableConfiguration
	 * @param tableMetaData
	 * @return
	 */
	private boolean validatorSize(TableConfiguration tableConfiguration,
			TableMetaData tableMetaData) {

		List<ColumnConfiguration> columns = tableConfiguration
				.getColumnsConfig();

		for (ColumnConfiguration column : columns) {
			if (!tableMetaData.lengthValidator(column.getColumnName(), column
					.getColumnSize())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * ��֤�Ƿ�������Ϊ�ظ���
	 * 
	 * @param tableConfiguration
	 * @param tableMetaData
	 * @return
	 */
	private boolean validatorDuplicate(TableConfiguration tableConfiguration,
			TableMetaData tableMetaData) {
		List<ColumnConfiguration> columns = tableConfiguration
				.getDuplicateColumns();

		List<String> columnsName = new ArrayList<String>();

		for (ColumnConfiguration column : columns) {
			columnsName.add(column.getColumnName());
		}

		return tableMetaData.isRepeatable(columnsName);
	}

	/**
	 * У���Ƿ����Ϊ��
	 * 
	 * @param tableConfiguration
	 * @param tableMetaData
	 */
	private boolean validatorNull(TableConfiguration tableConfiguration,
			TableMetaData tableMetaData) {
		List<ColumnConfiguration> columns = tableConfiguration.getNullColumns();
		for (ColumnConfiguration column : columns) {
			if (!tableMetaData.isNullable(column.getColumnName())) {
				logger.error("[" + column.getColumnName()
						+ ": can not set null!]");
				return false;
			}
		}

		return true;
	}

	/**
	 * ��û�����õ������ֶε���Ϣ ����Ĭ�ϵķ�ʽ4����������е���ɲ���
	 * 
	 * @param tableConfiguration
	 * @param tableMetaData
	 */
	private void fillAllColumnsConfiguration(
			TableConfiguration tableConfiguration, TableMetaData tableMetaData) {
		List<String> columnsName = new ArrayList<String>();
		List<ColumnConfiguration> columns = tableConfiguration
				.getColumnsConfig();

		for (ColumnConfiguration column : columns) {
			columnsName.add(column.getColumnName());
		}

		List<ColumnMetaData> columnsMetaData = tableMetaData
				.intersectColumnsMetaData(columnsName);
		for (ColumnMetaData columnMetaData : columnsMetaData) {
			tableConfiguration.addColumnConfiguration(new ColumnConfiguration(
					columnMetaData.getName(), columnMetaData.getColumnSize(),
					ColumnsType.UNIQUE.getType()));
		}

	}

	/**
	 * ����sql��䣬���Ҳ��뵽��ݿ�
	 * 
	 * @param tableConfiguration
	 */
	private List<String> generateSqls(TableConfiguration tableConfiguration) {

		String insertSqlTemplate = generateInsertSqlTemplate(tableConfiguration);
		List<String> insertSqls = new ArrayList<String>();
		initSqls(insertSqlTemplate, insertSqls, tableConfiguration);
		processInsertSqls(insertSqls);
		return insertSqls;
	}

	/**
	 * ����ɵ�sql���д���
	 * 
	 * @param insertSqls
	 */
	private void processInsertSqls(List<String> insertSqls) {
		for (String insertSql : insertSqls) {
			System.out.println(insertSql);
		}
	}

	/**
	 * ��������Ĳ���sql
	 * 
	 * @param insertSqlTemplate
	 * @param insertSqls
	 * @param tableConfiguration
	 */
	private void initSqls(String insertSqlTemplate, List<String> insertSqls,
			TableConfiguration tableConfiguration) {

		MessageFormat messageFormat = new MessageFormat(insertSqlTemplate);
		int sqlNums = tableConfiguration.getSqlNums();
		List<ColumnConfiguration> columnsConfig = tableConfiguration
				.getColumnsConfig();
		GenerateColumnValue generatorColumnsValue = new GenerateColumnValue(
				columnsConfig, fetchTableMetaData(tableConfiguration));

		for (int i = 0; i < sqlNums; i++) {

			String[] values = new String[columnsConfig.size()];
			for (int j = 0; j < values.length; j++) {
				values[j] = generatorColumnsValue.generateColumnValue(
						columnsConfig.get(j).getColumnName()).toString();
			}

			insertSqls.add(messageFormat.format(values));
		}
	}

	/**
	 * ����Ҫ��ɵ�sql ��ģ��
	 * 
	 * @param tableConfiguration
	 * @return
	 */
	private String generateInsertSqlTemplate(
			TableConfiguration tableConfiguration) {

		List<String> columnsName = tableConfiguration.getColumnsName();
		return SqlTemplateUtils.getInsertSqlTemplate(tableConfiguration
				.getTableName(), columnsName);

	}

	public void setParserTableConfiguration(
			ParseTableConfiguration parserTableConfiguration) {
		this.parserTableConfiguration = parserTableConfiguration;
	}

	public void setTableConfigurations(
			List<TableConfiguration> tableConfigurations) {
		this.tableConfigurations = tableConfigurations;
	}

	public static void main(String[] args) throws InterruptedException {
		GenerateInsertSql generateSql = new GenerateInsertSql();
		ParseTableConfiguration parse = new SpringXmlParseTableConfigurationImpl();
		generateSql.setParserTableConfiguration(parse);

		generateSql.process();
		Thread.sleep(10000);
		System.exit(0);
	}
}
