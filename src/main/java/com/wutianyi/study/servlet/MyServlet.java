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
     * 
     */
    private static final long serialVersionUID = -7415005951668800430L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("utf-8");
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

}
