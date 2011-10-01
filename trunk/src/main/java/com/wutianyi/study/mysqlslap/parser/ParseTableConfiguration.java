package com.wutianyi.study.mysqlslap.parser;

import java.util.List;

import com.wutianyi.study.mysqlslap.configuration.TableConfiguration;

/**
 * @author hanjiewu
 *解析生成表数据的配置信息
 */
public interface ParseTableConfiguration {

	public List<TableConfiguration> parseTablesConfiguration();

}
