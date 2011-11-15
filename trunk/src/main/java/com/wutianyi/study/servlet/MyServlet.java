package com.wutianyi.study.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet
{
    /**
     * servletContext 临时文件的属性名称，容器必须为每个servletContext创建一个临时文件目录
     */
    private static final String tempDir = "javax.servlet.context.tempdir";

    /**
     * 
     */
    private static final long serialVersionUID = -7415005951668800430L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // The Request，post form data when parameter are available
        req.getParameter("");
        req.getParameterNames();
        req.getParameterValues("");
        req.getParameterMap();
        // attribute 中名字以java.或javax.开头的是为本协议保留的，以com.sun，sun.开头的是位sun公司保留的
        req.getAttribute("");
        req.getAttributeNames();
        req.setAttribute("", "");
        req.removeAttribute("");

        req.getLocale();

        // contextpath 如果是root则是empty，否则返回用户的应用名称
        // servletpath 匹配到url-perrtern中的定义的
        // pathInfo剩下的
        // requestURI = contextPath + servletPath + pathInfo

        // response 返回的内容，包括http 头和body

        // 当response关闭之后，container应该将buffer中的内容tersffer to
        // client，导致其关闭的主要有，service方法，setcontentlenght，sendRedirect，sendError
        // buffer 注意使用的先后顺序，一旦content commit了，则不能调用相应的buffer方法
        // 会终止对内容输出到client
        resp.sendRedirect("");
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);

        req.getPathTranslated();

        req.setCharacterEncoding("utf-8");

        // session 跟踪的方式cookie，url 重写
        // session认为新的情况：client不知道该session的存在，client没有选择加入该session

        // 获取ip地址
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = req.getRemoteAddr();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

}
