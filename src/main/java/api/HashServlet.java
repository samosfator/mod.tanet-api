package main.java.api;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;
import java.util.Map;

public class HashServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Connection.Response loginResponse = Jsoup.connect("http://mod.tanet.edu.te.ua/site/login")
                .userAgent("MODULE.OK")
                .data("LoginForm[login]", login)
                .data("LoginForm[password]", password)
                .data("LoginForm[rememberMe]", "1")
                .data("yt0", "”‚≥ÈÚË")
                .method(Connection.Method.POST)
                .execute();

        Map<String, String> cookies = loginResponse.cookies();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println("[\"" + CookiesSerializer.serialize(cookies) + "\"]");
    }
}
