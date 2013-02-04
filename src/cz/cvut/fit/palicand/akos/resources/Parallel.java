package cz.cvut.fit.palicand.akos.resources;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 28/01/13
 * Time: 09:56
 * To change this template use File | Settings | File Templates.
 */
public class Parallel extends KOSResource {
    private String course;
    private int code;
    private ParallelType type;
    private TimetableSlot slot;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ParallelType getType() {
        return type;
    }

    public void setType(ParallelType type) {
        this.type = type;
    }

    public TimetableSlot getSlot() {
        return slot;
    }

    public void setSlot(TimetableSlot slot) {
        this.slot = slot;
    }
}
