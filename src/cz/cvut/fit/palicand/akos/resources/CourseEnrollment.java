package cz.cvut.fit.palicand.akos.resources;


import cz.cvut.fit.palicand.akos.resources.fetchers.CourseFetcher;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 16/01/13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
public class CourseEnrollment extends KOSResource {
    private boolean completed;
    private boolean extern;
    private String semester;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String author;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isExtern() {
        return extern;
    }

    public void setExtern(boolean extern) {
        this.extern = extern;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void fetchCourse(OnResourceProcessedListener listener) {
        CourseFetcher fetcher = new CourseFetcher(code, listener);
        fetcher.fields = "content(description,completion,credits,classesType)";
        new Thread(fetcher).start();
    }
}
