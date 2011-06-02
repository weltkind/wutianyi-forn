package com.wutianyi.duowan;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wutianyi.duowan.dao.BookIssueDao;
import com.wutianyi.duowan.dataobject.CodeActivityDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/com/wutianyi/duowan/spring/duowan-context.xml" })
public class CodeActivityDaoTest extends AbstractJUnit4SpringContextTests {

	private BookIssueDao bookIssueDao;

	@Autowired
	public void setBookIssueDao(BookIssueDao bookIssueDao) {
		this.bookIssueDao = bookIssueDao;
	}

	@Test
	public void testNotNull() {
		Assert.assertNotNull(bookIssueDao);
	}

	@Test
	public void testGetGameName() {
		System.err.println(bookIssueDao.getGameName(36));
	}
	
	@Test
	public void testGetActivityInformation() {
		CodeActivityDO codeActivity = bookIssueDao.fetchCodeActivity(36);
		System.err.println(codeActivity.getGameId());
		System.err.println(codeActivity.getGotCount());
		System.err.println(codeActivity.getHadBook());
		System.err.println(codeActivity.getOneSentence());
		System.err.println(codeActivity.getTaoCount());
		System.err.println(codeActivity.getActState());
	}
}
