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
        //attribute 中名字以java.或javax.开头的是为本协议保留的，以com.sun，sun.开头的是位sun公司保留的
        req.getAttribute("");
        req.getAttributeNames();
        req.setAttribute("", "");
        req.removeAttribute("");
        
        //contextpath 如果是root则是empty，否则返回用户的应用名称
        //servletpath 匹配到url-perrtern中的定义的
        //pathInfo剩下的
        //requestURI = contextPath + servletPath + pathInfo
        
        req.getPathTranslated();
        
        req.setCharacterEncoding("utf-8");
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

}
