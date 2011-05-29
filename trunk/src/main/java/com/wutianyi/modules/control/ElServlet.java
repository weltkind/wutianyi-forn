package com.wutianyi.modules.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class ElServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String param = req.getParameter("param");
		if(StringUtils.isNotBlank(param)) {
			req.setAttribute("error", "这是错误的信息");
		}
		
		String errorInformation = "这是错误的信息";
		req.setAttribute("errorInformation", errorInformation);
		req.getRequestDispatcher("elServlet.jsp").forward(req, resp);
	}

}
