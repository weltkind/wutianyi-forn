package com.wutianyi.study.ibatis.oracle.dao;

import java.util.List;
import java.util.Map;

public interface OracleDao {
	
	public List<Map<String, Object>> listAllCommodity();
	
	public List<Map<String, Object>> listAllAttrDict();
	
	public List<Map<String, Object>> listAllValueDict();
	
	public List<Map<String, Object>> listAllCommodityAttrValueRel();
}
