package main.java.api.parser;

import main.java.api.parser.pojo.Module;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ModulesParser {

    private Element subjectElement;
    private int lastTdIndex = -1;

    public ModulesParser(Element subjectElement) {
        this.subjectElement = subjectElement.clone();
        this.subjectElement.select("td:first-child").remove();
        this.subjectElement.select("td:first-child").remove();
        this.subjectElement.select("td:last-child").remove();
    }

    public List<Module> parseModules() {
        int modulesCount = getModulesCount(subjectElement);

        List<Module> modules = new ArrayList<>();

        for (int i = 0; i < modulesCount; i++) {
            modules.add(parseModule(i));
        }

        return modules;
    }

    private Module parseModule(int index) {
        ModuleParser moduleParser = new ModuleParser(getModuleElement(index));
        return moduleParser.parseModule();
    }

    private int getModulesCount(Element subjectElement) {
        Elements td = subjectElement.select("td");
        int modulesCount = 0;
        for (Element aTd : td) {
            if (aTd.text().contains(".")) {
                modulesCount++;
            }
        }
        return modulesCount;
    }

    private Element getModuleElement(int index) {

        Element firstTd = subjectElement.select("td").get(0);
        Element secondTd = subjectElement.select("td").get(1);
        Element thirdTd = subjectElement.select("td").get(2);

        return new Element(Tag.valueOf("tr"), "")
                .appendChild(firstTd)
                .appendChild(secondTd)
                .appendChild(thirdTd);
    }
}
