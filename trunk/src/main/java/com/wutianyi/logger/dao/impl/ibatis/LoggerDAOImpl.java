package com.wutianyi.logger.dao.impl.ibatis;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.wutianyi.logger.dao.LoggerDAO;
import com.wutianyi.logger.dataobject.LoggerDO;
import com.wutianyi.logger.query.LoggerQuery;

public class LoggerDAOImpl extends SqlMapClientDaoSupport implements LoggerDAO {

	private static Logger log4jLogger = Logger.getLogger(LoggerDAOImpl.class);
	
	public LoggerDO findLoggerById(int id) {
		return (LoggerDO) this.getSqlMapClientTemplate().queryForObject("logger.findLoggerById", id);
	}

	public void recordLogger(LoggerDO logger) {
		if(null == logger) {
			log4jLogger.error("[The Logger Object is NULL!]");
			return;
		}
		this.getSqlMapClientTemplate().insert("logger.insert_logger", logger);
	}

	/* (non-Javadoc)
	 * @see com.wutianyi.logger.dao.LoggerDAO#listLoggerByLoggerQuery(com.wutianyi.logger.query.LoggerQuery)
	 */
	@SuppressWarnings("unchecked")
	public List<LoggerDO> listLoggerByLoggerQuery(LoggerQuery loggerQuery) {
		if(null == loggerQuery) {
			log4jLogger.error("[The LoggerQuery is NULL!]");
			return null;
		}
		
		return this.getSqlMapClientTemplate().queryForList("logger.listLoggerByLoggerQuery", loggerQuery);
	}

}
