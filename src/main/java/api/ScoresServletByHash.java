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

public class ScoresServletByHash extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("UTF-8");

        String hash = req.getParameter("hash");
        String weirdCookie = req.getParameter("weirdCookie");

        if (weirdCookie == null) {
            weirdCookie = "";
        }

        Connection.Response loginResponse = getLoginResponse(hash, weirdCookie);

        String phpsessid = loginResponse.cookies().get("PHPSESSID");
        boolean success = loginResponse.parse().body().select("#mainmenu li").last().select("a").attr("href").equals("/site/logout");

        ModuleokParser moduleokParser = new ModuleokParser(loginResponse.parse());

        Student student = null;
        Result result = new Result();
        try {
            student = moduleokParser.constructStudent();
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }
        result.setPhpsessid(phpsessid);
        result.setSuccess(success);
        result.setStudent(student);
        resp.getWriter().println(new Gson().toJson(result));
    }

    private Connection.Response getLoginResponse(String hash, String weirdCookie) throws IOException {
        return Jsoup.connect("http://mod.tanet.edu.te.ua/ratings/index")
                .userAgent("MODULE.OK")
                .cookie("PHPSESSID", hash)
                .cookie("804c27b4e9d8b5a3183e7ab890c2d8f3", weirdCookie)
                .method(Connection.Method.GET)
                .execute();
    }
}
