package com.nannan.tomcat.servlets;

import com.nannan.standard.ServletException;
import com.nannan.standard.http.HttpServletRequest;
import com.nannan.standard.http.HttpServletResponse;
import com.nannan.standard.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 86187
 * Date: 2021 -03 -31
 * Time: 16:52
 */
public class NotFoundServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(404);
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("<h1>该资源不存在</h1>");
    }
}
