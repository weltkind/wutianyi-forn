package com.wutianyi.modules.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 获取文件夹中所有的图片
 * 
 * @author hanjie.wuhj
 * 
 */
public class ShowImage extends HttpServlet {

	private static String imagePath;
	private static final String IMAGE_PATH = "imagePath";
	private static String path;
	private static final String REFRESH = "refresh";
	private static final String REFRESH_TRUE = "true";
	private List<String> imagePaths;

	@Override
	public void init(ServletConfig config) throws ServletException {
		imagePath = config.getInitParameter(IMAGE_PATH);
		path = imagePath.substring(imagePath.lastIndexOf(File.separator) + 1,
				imagePath.length());
		imagePaths = listAllImages();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3773261103501596034L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//判断是否需要进行相应的图片更新
		String refresh = req.getParameter(REFRESH);
		if (StringUtils.isNotBlank(refresh)
				&& StringUtils.equals(REFRESH_TRUE, refresh)) {
			imagePaths = listAllImages();
		}
		req.setAttribute(IMAGE_PATH, imagePaths);
		req.getRequestDispatcher("showImage.jsp").forward(req, resp);
	}

	/**
	 * 修改为每次只需要检查是否有新的文件添加或文件修改！
	 * 
	 * @return
	 */
	private List<String> listAllImages() {
		List<String> images = new ArrayList<String>();
		File file = new File(imagePath);
		if (file.isDirectory()) {
			File[] imgs = file.listFiles();

			for (File img : imgs) {
				if (img.isFile()) {
					images.add(path + "/" + img.getName());
				}
			}
		}
		return images;
	}
}
