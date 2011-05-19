package com.wutianyi.study.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6810697194143959424L;
	private int i = 0;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter pw = resp.getWriter();
		String type = req.getParameter("type");
		if(i % 2 == 0) {
			pw.print("<html><head></head><body>1</body></html>");
		} else {
			pw.print("<html><head></head><body>2</body></html>");
		}
		i ++;
	}
	
}
