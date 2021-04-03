package com.nannan.webapps.dictionary;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 86187
 * Date: 2021 -03 -31
 * Time: 17:59
 */
import com.nannan.standard.ServletException;
import com.nannan.standard.http.HttpServletRequest;
import com.nannan.standard.http.HttpServletResponse;
import com.nannan.standard.http.HttpSession;
import com.nannan.standard.http.HttpServlet;

import java.io.IOException;

public class ProfileActionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 说明当前用户没登陆，重定向到登录界面登录
            resp.sendRedirect("login.html");
        } else {
            // 登陆成功
            resp.setContentType("text/plain");
            resp.getWriter().println(user.toString());
        }
    }
}
