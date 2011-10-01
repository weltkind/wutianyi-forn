package com.wutianyi.study.mysqlslap.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.wutianyi.study.mysqlslap.configuration.ColumnConfiguration;
import com.wutianyi.study.mysqlslap.metadata.ColumnMetaData;
import com.wutianyi.study.mysqlslap.metadata.TableMetaData;
import com.wutianyi.study.mysqlslap.valuegenerator.ValueGenerator;
import com.wutianyi.study.mysqlslap.valuegenerator.ValueGeneratorFactory;

public class GenerateColumnValue {

	private Map<String, ValueGenerator> valueGenerators;

	public GenerateColumnValue(List<ColumnConfiguration> columnsConfig,
			TableMetaData tableMetaData) {
		valueGenerators = new HashMap<String, ValueGenerator>();

		for (ColumnConfiguration columnConfig : columnsConfig) {
			String columnName = columnConfig.getColumnName();

			ColumnMetaData columnMetaData = tableMetaData
					.getColumnMetaData(columnName);

			valueGenerators.put(columnName, ValueGeneratorFactory
					.getValueGenerator(columnMetaData.getTypeName(),
							columnConfig.getColumnSize(), columnConfig
									.getType()));
		}

	}

	public Object generateColumnValue(String columnName) {

		ValueGenerator valueGenerator = valueGenerators.get(columnName);

		if (null != valueGenerator) {
			return valueGenerator.generateValue();
		}

		return null;
	}
}
