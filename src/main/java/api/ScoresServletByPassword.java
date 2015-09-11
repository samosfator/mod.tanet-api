package main.java.api;

import com.google.gson.Gson;
import main.java.api.parser.ModuleokParser;
import main.java.api.parser.pojo.Student;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ScoresServletByPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Connection.Response loginResponse = getLoginResponse(login, password);

        String phpsessid = loginResponse.cookies().get("PHPSESSID");
        String weirdCookie = loginResponse.cookies().get("804c27b4e9d8b5a3183e7ab890c2d8f3");
        boolean success = loginResponse.parse().body().select("#mainmenu li").last().select("a").attr("href").equals("/site/logout");

        ModuleokParser moduleokParser = new ModuleokParser(loginResponse.parse());

        Student student = null;
        Result result = new Result();
        try {
            student = moduleokParser.constructStudent();
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        result.setPhpsessid(phpsessid);
        result.set$804c27b4e9d8b5a3183e7ab890c2d8f3(weirdCookie);
        result.setSuccess(success);
        result.setStudent(student);
        resp.getWriter().println(new Gson().toJson(result));
    }

    private Connection.Response getLoginResponse(String login, String password) throws IOException {
        return Jsoup.connect("http://mod.tanet.edu.te.ua/site/login")
                .userAgent("MODULE.OK")
                .data("LoginForm[login]", login)
                .data("LoginForm[password]", password)
                .data("LoginForm[rememberMe]", "1")
                .data("yt0", "”‚≥ÈÚË")
                .method(Connection.Method.POST)
                .execute();
    }
}
