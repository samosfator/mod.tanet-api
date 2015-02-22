package main.java.api.parser;

import main.java.api.parser.pojo.Semester;
import main.java.api.parser.pojo.Subject;
import org.jsoup.nodes.Element;

import java.util.Collections;

public class SemesterParser {

    private SubjectsParser subjectsParser;

    public SemesterParser(Element semesterElement) {
        subjectsParser = new SubjectsParser(semesterElement);
    }

    public Semester parseSemester() {
        Semester semester = new Semester();
        if (subjectsParser.isEmptySemester()) {
            semester.setSubjects(Collections.<Subject>emptyList());
        } else {
            semester.setSubjects(subjectsParser.parseSubjects());
        }
        return semester;
    }
}
