package com.wutianyi.study.mysqlslap.parser;

import java.util.List;

import com.wutianyi.study.mysqlslap.configuration.TableConfiguration;

public interface ParseTableConfiguration {

	public List<TableConfiguration> parseTablesConfiguration();

}
