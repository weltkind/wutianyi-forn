/**
 * 
 */

package com.wutianyi.study.ibatis.mapper;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ibatis.common.resources.Resources;

/**
 * @author ashen
 * 
 */
public class Main {

	public static void main(String[] args) throws IOException {
	    //classpath加载
		String resource = "mybatis/mybatis-config.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(reader);
		
		SqlSession session = factory.openSession();
		List<NodeMgrDO> nodeMgrs = session.selectList("com.wutianyi.ibatis.mapper.NodeMgrMapper.listAllNodeMgr");
		NodeMgrMapper mapper = session.getMapper(NodeMgrMapper.class);
		NodeMgrMapperAn an = session.getMapper(NodeMgrMapperAn.class);
		
		System.out.println(nodeMgrs);
		nodeMgrs = mapper.listAllNodeMgr();
		System.out.println(nodeMgrs);
		nodeMgrs = an.listAllNodeMgr();
		System.out.println(nodeMgrs);
	}
}
