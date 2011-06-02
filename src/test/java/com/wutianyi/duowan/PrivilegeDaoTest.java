package com.wutianyi.duowan;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wutianyi.duowan.dao.PrivilegeDao;
import com.wutianyi.duowan.dataobject.FloatLayerDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/com/wutianyi/duowan/spring/duowan-context.xml"})
public class PrivilegeDaoTest extends AbstractJUnit4SpringContextTests{
	private PrivilegeDao privilegeDao;
	
	@Autowired
	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}
	
	@Test
	public void testNotNull() {
		Assert.assertNotNull(privilegeDao);
	}
	
	@Test
	public void testGetPrivilegeGame() {
		FloatLayerDTO floatLayer = privilegeDao.getPriGameNAIByGameId(43);
		System.out.println(floatLayer.getGameName());
		System.out.println(floatLayer.getIntroduction());
		System.out.println(floatLayer.getCountByStatus());
		System.out.println(floatLayer.getStatusType());
	}
	
	@Test
	public void testListPrivilegeGame() {
		List<Integer> gameIds = new ArrayList<Integer>();
		gameIds.add(43);
		gameIds.add(44);
		gameIds.add(45);
		
		List<FloatLayerDTO> floatLayers = privilegeDao.listPriGameNAIByGameIds(gameIds);
		for(FloatLayerDTO floatLayer : floatLayers) {
			System.out.println(floatLayer.getGameName());
			System.out.println(floatLayer.getIntroduction());
			System.out.println(floatLayer.getCountByStatus());
			System.out.println(floatLayer.getStatusType());
		}
	}
}
