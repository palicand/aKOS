package cz.cvut.fit.palicand.akos.resources;

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

    }

}
