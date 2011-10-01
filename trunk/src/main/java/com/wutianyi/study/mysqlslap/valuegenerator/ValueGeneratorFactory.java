package com.wutianyi.study.mysqlslap.valuegenerator;

import com.wutianyi.study.mysqlslap.configuration.ColumnsType;
import com.wutianyi.study.mysqlslap.constants.DataBaseColumnType;
import com.wutianyi.study.mysqlslap.valuegenerator.impl.DateTimeGenerator;
import com.wutianyi.study.mysqlslap.valuegenerator.impl.DefaultValueGenerator;
import com.wutianyi.study.mysqlslap.valuegenerator.impl.IntGenerator;
import com.wutianyi.study.mysqlslap.valuegenerator.impl.VarcharGenerator;

public class ValueGeneratorFactory {

	public static ValueGenerator getValueGenerator(String columnType, int length,
			ColumnsType type) {
		if (DataBaseColumnType.INT.equal(columnType)) {
			return new IntGenerator(type, length);
		} else if (DataBaseColumnType.VARCHAR.equal(columnType)) {
			return new VarcharGenerator(type, length);
		} else if (DataBaseColumnType.DATETIME.equal(columnType)) {
			return new DateTimeGenerator();
		}

		return new DefaultValueGenerator();

	}

}
