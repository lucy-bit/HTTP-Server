package com.nannan.webapps.dictionary;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 86187
 * Date: 2021 -03 -31
 * Time: 17:55
 */
import com.nannan.standard.ServletException;
import com.nannan.standard.http.HttpServletRequest;
import com.nannan.standard.http.HttpServletResponse;
import com.nannan.standard.http.HttpSession;
import com.nannan.standard.http.HttpServlet;

import java.io.IOException;

public class LoginActionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username+password);
        if (username.equals("nannan") && password.equals("123456")) {
            User user = new User(username, password);
            HttpSession session = req.getSession();


            session.setAttribute("user", user);



            resp.sendRedirect("profile-action");
        } else {
            resp.sendRedirect("login.html");
        }
    }
}
