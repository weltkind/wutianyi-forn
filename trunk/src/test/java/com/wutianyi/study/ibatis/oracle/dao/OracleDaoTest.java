package com.wutianyi.study.ibatis.oracle.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/wutianyi/study/ibatis/spring/oracle-spring.xml" })
public class OracleDaoTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private OracleDao oracleDao;

	private final String CN_NAME = "cn_name";

	private final String DESCRIPTION = "description";
	
	private final String SPLIT = "------------------------------------------------";

	@Test
	public void testListAllCommodity() throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(
				"tl_commodity.txt"));
		List<Map<String, Object>> commoditys = oracleDao.listAllCommodity();
		Map<String, Object> header = commoditys.get(0);
		for (Entry<String, Object> entry : header.entrySet()) {
			pw.print(StringUtils.rightPad(entry.getKey(), 50));
		}
		pw.println(SPLIT);
		for (Map<String, Object> commodity : commoditys) {
			for (Entry<String, Object> c : commodity.entrySet()) {
				String value = StringUtils.EMPTY;
				if (null != c.getValue()) {
					if (c.getKey().equalsIgnoreCase(CN_NAME)
							|| c.getKey().equalsIgnoreCase(DESCRIPTION)) {
						value = new String(c.getValue().toString().getBytes(
								"ISO-8859-1"), "utf-8");
					} else {
						value = c.getValue().toString();
					}
				}

				pw.println(value);
			}
			pw.println(SPLIT);
		}
		Assert.assertEquals(4143, commoditys.size());
		pw.close();
	}

	@Test
	public void testListAllAttrDict() throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(
				"tl_attr_dict.txt"));
		List<Map<String, Object>> commoditys = oracleDao.listAllAttrDict();
		Map<String, Object> header = commoditys.get(0);
		for (Entry<String, Object> entry : header.entrySet()) {
			pw.print(StringUtils.rightPad(entry.getKey(), 50));
		}
		pw.println(SPLIT);
		for (Map<String, Object> commodity : commoditys) {
			for (Entry<String, Object> c : commodity.entrySet()) {
				String value = StringUtils.EMPTY;
				if (null != c.getValue()) {
					if (c.getKey().equalsIgnoreCase(CN_NAME)
							|| c.getKey().equalsIgnoreCase(DESCRIPTION)) {
						value = new String(c.getValue().toString().getBytes(
								"ISO-8859-1"), "utf-8");
					} else {
						value = c.getValue().toString();
					}
				}

				pw.println(value);
			}
			pw.println(SPLIT);
		}
		pw.close();
	}

	@Test
	public void testListAllValueDict() throws FileNotFoundException,
			UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(
				"tl_value_dict.txt"));
		List<Map<String, Object>> commoditys = oracleDao.listAllValueDict();
		Map<String, Object> header = commoditys.get(0);
		for (Entry<String, Object> entry : header.entrySet()) {
			pw.print(StringUtils.rightPad(entry.getKey(), 50));
		}
		pw.println(SPLIT);
		for (Map<String, Object> commodity : commoditys) {
			for (Entry<String, Object> c : commodity.entrySet()) {
				String value = StringUtils.EMPTY;
				if (null != c.getValue()) {
					if (c.getKey().equalsIgnoreCase(CN_NAME)
							|| c.getKey().equalsIgnoreCase(DESCRIPTION)) {
						value = new String(c.getValue().toString().getBytes(
								"ISO-8859-1"), "utf-8");
					} else {
						value = c.getValue().toString();
					}
				}

				pw.println(value);
			}
			pw.println(SPLIT);
		}
		pw.close();
	}

	@Test
	public void testListAllCommodityAttrValueRel()
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(
				"tl_commodity_attr_value_rel.txt"));
		List<Map<String, Object>> commoditys = oracleDao
				.listAllCommodityAttrValueRel();
		Map<String, Object> header = commoditys.get(0);
		for (Entry<String, Object> entry : header.entrySet()) {
			pw.print(StringUtils.rightPad(entry.getKey(), 50));
		}
		pw.println(SPLIT);
		for (Map<String, Object> commodity : commoditys) {
			for (Entry<String, Object> c : commodity.entrySet()) {
				String value = StringUtils.EMPTY;
				if (null != c.getValue()) {
					if (c.getKey().equalsIgnoreCase(CN_NAME)
							|| c.getKey().equalsIgnoreCase(DESCRIPTION)) {
						value = new String(c.getValue().toString().getBytes(
								"ISO-8859-1"), "utf-8");
					} else {
						value = c.getValue().toString();
					}
				}

				pw.println(value);
			}
			pw.println(SPLIT);
		}
		pw.close();
	}
}
