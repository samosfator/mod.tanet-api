package main.java.api.parser.pojo;

public class Student {

    private String name;
    private String group;

    private Semester firstSemester;
    private Semester secondSemester;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Semester getFirstSemester() {
        return firstSemester;
    }

    public void setFirstSemester(Semester firstSemester) {
        this.firstSemester = firstSemester;
    }

    public Semester getSecondSemester() {
        return secondSemester;
    }

    public void setSecondSemester(Semester secondSemester) {
        this.secondSemester = secondSemester;
    }
}
