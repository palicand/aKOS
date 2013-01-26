package cz.cvut.fit.palicand.akos.resources;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 23/01/13
 * Time: 14:55
 * To change this template use File | Settings | File Templates.
 */
public class Course extends KOSResource {
    public Course() {
        this.type = EnumSet.noneOf(ClassType.class);
    }
    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseCompletion getCompletion() {
        return completion;
    }

    public void setCompletion(CourseCompletion completion) {
        this.completion = completion;
    }

    public EnumSet<ClassType> getType() {
        return type;
    }

    public Course addType(ClassType type) {
        this.type.add(type);
        return this;
    }

    private int credits;
    private String name;
    private String code;
    private String description;
    private CourseCompletion completion;
    private EnumSet<ClassType> type;

}
