package main.java.api.parser;

import main.java.api.parser.pojo.Module;
import org.jsoup.nodes.Element;

public class ModuleParser {

    private Element moduleElement;

    public ModuleParser(Element moduleElement) {
        this.moduleElement = moduleElement;
    }

    public Module parseModule() {
        Module module = new Module();
        module.setDate(parseDate());
        module.setScore(parseScore());
        module.setWeight(parseWeight());
        return module;
    }

    private int parseWeight() {
        return Integer.parseInt(moduleElement.select("td").eq(0).text());
    }

    private String parseDate() {
        return moduleElement.select("td").eq(1).text();
    }

    private int parseScore() {
        try {
            return Integer.parseInt(moduleElement.select("td").eq(2).text());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
