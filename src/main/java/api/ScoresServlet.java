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

public class ScoresServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Document mainPageHtml = getMainPageDocument(req);
//        Document mainPageHtml = Jsoup.connect("http://moduleok.hol.es/mock-empty.html").get();

        ModuleokParser moduleokParser = new ModuleokParser(mainPageHtml);

        Student student = moduleokParser.constructStudent();
        String studentJson = new Gson().toJson(student);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(studentJson);
    }

    private Document getMainPageDocument(HttpServletRequest req) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String hash = req.getParameter("hash");

        Connection.Response loginResponse;
        if (hash == null) {
            loginResponse = Jsoup.connect("http://mod.tanet.edu.te.ua/site/login")
                    .userAgent("MODULE.OK_v." + Math.random())
                    .data("LoginForm[login]", login)
                    .data("LoginForm[password]", password)
                    .data("LoginForm[rememberMe]", "1")
                    .data("yt0", "Увійти")
                    .method(Connection.Method.POST)
                    .execute();
        } else {
            loginResponse = Jsoup.connect("http://mod.tanet.edu.te.ua/ratings/index")
                    .userAgent("MODULE.OK_v." + Math.random())
                    .cookies(CookiesSerializer.deserialize(hash))
                    .method(Connection.Method.GET)
                    .execute();
        }
        return loginResponse.parse();
    }
}
