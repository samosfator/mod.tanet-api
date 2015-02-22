package main.java.api.parser;

import main.java.api.parser.pojo.Student;
import org.jsoup.nodes.Document;

public class ModuleokParser {

    private NameGroupParser nameGroupParser;
    private SemesterParser firstSemesterParser;
    private SemesterParser secondSemesterParser;

    public ModuleokParser(Document document) {
        nameGroupParser = new NameGroupParser(document.select(".pageTitle").first());
        firstSemesterParser = new SemesterParser(document.select(".items").first());
        secondSemesterParser = new SemesterParser(document.select(".items").last());
    }

    public Student constructStudent() {
        Student student = new Student();
        student.setName(nameGroupParser.parseName());
        student.setGroup(nameGroupParser.parseGroup());
        student.setFirstSemester(firstSemesterParser.parseSemester());
        student.setSecondSemester(secondSemesterParser.parseSemester());
        return student;
    }
}
