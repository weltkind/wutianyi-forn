/**
 * 
 */

package com.wutianyi.study.ibatis.mapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wutianyi
 * 
 */
public class SpringMain {

	public static void main(String[] args) {

		ApplicationContext context =
				new ClassPathXmlApplicationContext("myibatis/spring-ibatis.xml");
		NodeMgrMapper mapper = (NodeMgrMapper)context.getBean("userMapper");
	}
}
