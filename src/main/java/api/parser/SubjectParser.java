package main.java.api.parser;

import main.java.api.parser.pojo.Subject;
import org.jsoup.nodes.Element;

public class SubjectParser {

    private ModulesParser modulesParser;

    public SubjectParser(Element subjectElement) {
        this.modulesParser = new ModulesParser(subjectElement);
    }

    public Subject parseSubject(Element subjectElement) {
        Subject subject = new Subject();
        subject.setName(parseName(subjectElement));
        subject.setControlType(parseControlType(subjectElement));
        subject.setModules(modulesParser.parseModules());
        return subject;
    }

    private String parseName(Element subjectElement) {
        return subjectElement.select("td").eq(0).text();
    }
    private String parseControlType(Element subjectElement) {
        return subjectElement.select("td").eq(1).text();
    }
}
