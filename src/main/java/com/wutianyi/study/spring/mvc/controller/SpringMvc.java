/**
 * 
 */

package com.wutianyi.study.spring.mvc.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * @author wutianyi
 * 
 */
/**
 * @author wutianyi
 * 
 */
@Controller
@RequestMapping("/appointments")
public class SpringMvc {

	private final String	comment;

	@Autowired
	public SpringMvc(String comment) {

		this.comment = comment;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, String> get() {

		return new HashMap<String, String>();
	}

	/**
	 * params 参数可以指定只有单url中有指定的参数对的时候才进行处理
	 * headers 指定只有单请求头中有相应的数据才是进行处理
	 * 
	 * @param ownerId
	 * @return
	 */
	@RequestMapping(value = "/owners/{ownerId}", params = {}, headers = {})
	public String findOwner(@PathVariable String ownerId) {

		return "displayOwner";
	}

	/**
	 * @param request
	 * @param response
	 * @param session
	 *            会是线程不安全的
	 */
	@RequestMapping
	public void handlerMethodParams(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, WebRequest webRequest, Locale locale, @RequestParam String param1,
			@RequestHeader String head1) {

	}

	/**
	 * 直接将返回的信息返回到客户端
	 * @return
	 */
	@RequestMapping(value = "/something", method = RequestMethod.PUT)
	@ResponseBody
	public String helloWorld(@CookieValue(value="jsessionid") String value) {
		return "Hello World";
	}

	public void getWebApplicationContext(HttpServletRequest request) {

		RequestContextUtils.getWebApplicationContext(request);
	}
}
