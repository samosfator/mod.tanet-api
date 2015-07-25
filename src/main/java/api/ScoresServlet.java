package main.java.api;

import com.google.gson.Gson;
import main.java.api.parser.ModuleokParser;
import main.java.api.parser.pojo.Student;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScoresServlet extends HttpServlet {

    private Map<String, String> cookies = new HashMap<>();
    private boolean success = false;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int apiVersion = req.getParameter("v") == null ? 1 : 2;

        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("UTF-8");

        Document mainPageHtml = getMainPageDocument(req);
        ModuleokParser moduleokParser = new ModuleokParser(mainPageHtml);

        Student student;
        try {
            student = moduleokParser.constructStudent();
        } catch (Exception e) {
            success = false;
            if (apiVersion == 2) {
                resp.getWriter().println("{\"student\": {}, \"cookies\": \"\", \"success\": false}");
                return;
            } else {
                throw e;
            }
        }

        Result result = new Result();
        result.setStudent(student);
        result.setCookies(CookiesSerializer.serialize(cookies));
        result.setSuccess(success);

        switch (apiVersion) {
            case 1:
                resp.getWriter().println(new Gson().toJson(student));
                break;
            case 2:
                resp.getWriter().println(new Gson().toJson(result));
                break;
        }
    }

    private Document getMainPageDocument(HttpServletRequest req) throws IOException {
        Connection.Response loginResponse;

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String hash = req.getParameter("hash");

        if (hash == null) {
            loginResponse = getLoginResponse(login, password);
        } else {
            loginResponse = getLoginResponse(hash);
        }

        cookies = loginResponse.cookies();
        success = loginResponse.parse().body().select("#mainmenu li").last().select("a").attr("href").equals("/site/logout");

        return loginResponse.parse();
    }

    private Connection.Response getLoginResponse(String login, String password) throws IOException {
        return getConnection()
                .data("LoginForm[login]", login)
                .data("LoginForm[password]", password)
                .method(Connection.Method.POST)
                .execute();
    }

    private Connection.Response getLoginResponse(String hash) throws IOException {
        return getConnection()
                .cookies(CookiesSerializer.deserialize(hash))
                .method(Connection.Method.GET)
                .execute();
    }

    private Connection getConnection() {
        return Jsoup.connect("http://mod.tanet.edu.te.ua/site/login")
                .userAgent("MODULE.OK")
                .data("LoginForm[rememberMe]", "1")
                .data("yt0", "Увійти");
    }
}
