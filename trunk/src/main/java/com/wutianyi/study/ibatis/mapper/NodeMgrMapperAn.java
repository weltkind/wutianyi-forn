/**
 * 
 */
package com.wutianyi.study.ibatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


/**
 * @author wutianyi
 *
 */
public interface NodeMgrMapperAn {
	@Select("select id, type, host, port, domain, weight from nodemgr")
	public List<NodeMgrDO> listAllNodeMgr();
}
