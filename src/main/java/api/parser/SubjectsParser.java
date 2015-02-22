package main.java.api.parser;

import main.java.api.parser.pojo.Subject;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class SubjectsParser {

    private Element semesterElement;

    public SubjectsParser(Element semesterElement) {
        this.semesterElement = semesterElement;
    }

    public List<Subject> parseSubjects() {
        List<Subject> subjects = new ArrayList<>();
        for (int i = 0; i < getSubjectCount(); i++) {
            SubjectParser subjectParser = new SubjectParser(getSubjectElement(i));
            subjects.add(subjectParser.parseSubject(getSubjectElement(i)));
        }
        return subjects;
    }

    public boolean isEmptySemester() {
        return semesterElement.html().contains("No results found.") || semesterElement.select("tr").size() < 2;
    }

    private int getSubjectCount() {
        return semesterElement.select("tr").size() - 1;
    }

    private Element getSubjectElement(int index) {
        return semesterElement.select("tr").eq(index + 1).first();
    }
}
