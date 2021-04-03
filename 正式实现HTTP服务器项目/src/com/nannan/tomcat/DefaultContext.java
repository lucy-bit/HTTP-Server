package com.nannan.tomcat;

import com.nannan.standard.Servlet;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 86187
 * Date: 2021 -03 -31
 * Time: 17:08
 */
public class DefaultContext extends Context {
    public DefaultContext(ConfigReader reader) {
        super(reader, "/");
    }

    //无论拿着什么样的servlet路径来找，返回的都是notFoundServlet——404
    @Override
    public Servlet get(String servletPath) {
        return HttpServer.notFoundServlet;
    }
}
