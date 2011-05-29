package com.wutianyi.modules.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class UploadServlet extends HttpServlet {

	private static String path = "";
	/**
	 * 
	 */
	private static final long serialVersionUID = 4317409421738191528L;
	private static String prefix = "prefix-";
	private final String USER_NAME = "wutianyi";
	private final String PASSWORD = "tgyycom";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
			return;
		}
		if (!USER_NAME.equals(username) || (!PASSWORD.equals(password))) {
			req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
			return;
		}
		HttpSession session = req.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		req.getRequestDispatcher("/WEB-INF/html/uploadfile.html").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		String imageindex = req.getParameter("imageindex");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload up = new ServletFileUpload(factory);

		try {
			List<FileItem> is = up.parseRequest(req);
			for (FileItem fileItem : is) {
				if (!fileItem.isFormField()) {

					File mkr = new File(path, prefix + imageindex + ".jpg");
					if (mkr.exists()) {
						mkr.delete();
					}
					if (mkr.createNewFile()) {
						fileItem.write(mkr);
						pw.write('1');
					}
				}
			}
		} catch (FileUploadException e) {
		} catch (Exception e) {
		}
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(arg0, arg1);
	}

	@Override
	public void init() throws ServletException {
		path = this.getServletContext().getRealPath("/uploads");
	}

}
