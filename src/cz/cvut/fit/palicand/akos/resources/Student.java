package cz.cvut.fit.palicand.akos.resources;

import cz.cvut.fit.palicand.akos.resources.fetchers.CourseFetcher;
import cz.cvut.fit.palicand.akos.resources.fetchers.ParallelFetcher;
import cz.cvut.fit.palicand.akos.resources.fetchers.StudentCoursesFetcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 12/01/13
 * Time: 22:42
 * To change this template use File | Settings | File Templates.
 */
public class Student extends KOSResource {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int grade;
    private String uid;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void fetchCourses(OnResourceProcessedListener listener) {
        StudentCoursesFetcher fetcher = new StudentCoursesFetcher(username, listener);
        new Thread(fetcher).start();
    }

    public void fetchCourses(OnResourceProcessedListener listener, String semester) {
       StudentCoursesFetcher fetcher = new StudentCoursesFetcher(username, semester, listener);
        new Thread(fetcher).start();
    }

    /**
     * Gets all the previous semesters for student
     * @param currentSemester the current semester code
     * @return all the previous semester codes the student was enrolled int
     */
    public Collection<Semester> getAllSemesterCodes(Semester currentSemester) {
        int prevSemesters = 0;
        if(currentSemester.getType() == Semester.SemesterType.WINTER) {
            prevSemesters = 2*(getGrade() - 1);
        } else {
            prevSemesters = 2*getGrade() - 1;
        }

        ArrayList<Semester> semesters = new ArrayList<Semester>(prevSemesters);
        for (int i = 0; i < prevSemesters; ++i) {
            Semester semester = new Semester();
            semester.setCode(currentSemester.getPrevCode());
            semesters.add(semester);
            currentSemester = semester;
        }

        return semesters;
    }

    public void fetchParallels(OnResourceProcessedListener listener, Semester semester) {
        new Thread(new ParallelFetcher(username, semester.getCode(),listener)).start();
    }
}
