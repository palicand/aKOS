package cz.cvut.fit.palicand.akos.resources;

import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 28/01/13
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class TimetableSlot {
    private int day;
    private Parity parity;
    private String room;
    private int firstHour;
    private int duration;

    public long getFirstOccurence(Date semesterStart) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(semesterStart);
        calendar.set(Calendar.DAY_OF_WEEK, getDay());
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.HOUR, 7);
        calendar.add(Calendar.MINUTE, (firstHour - 1) * 55);
        return calendar.getTimeInMillis();
    }

    private int getDay() {
        if(day == 1) {
            return Calendar.MONDAY;
        } else if(day == 2) {
            return Calendar.TUESDAY;
        } else if (day == 3)
            return Calendar.WEDNESDAY;
        else if (day == 4)
            return Calendar.THURSDAY;
        else if (day == 5)
            return Calendar.FRIDAY;
        else if (day == 6)
            return Calendar.SATURDAY;
        else
            return Calendar.SUNDAY;
    }

    public Parity getParity() {
        return parity;
    }

    public void setParity(Parity parity) {
        this.parity = parity;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setFirstHour(int firstHour) {
        this.firstHour = firstHour;
    }
}
