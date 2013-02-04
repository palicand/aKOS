package cz.cvut.fit.palicand.akos.resources;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 26/01/13
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class Semester extends KOSResource {
    public SemesterType getType() {
        if(code.charAt(code.length() - 1) == '1')
            return SemesterType.WINTER;
        else
            return SemesterType.SUMMER;
    }

    public enum SemesterType {
        WINTER,
        SUMMER
    }
    private String code;
    private String name;
    private Date startDate;
    private Date endDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void fetchNext(OnResourceProcessedListener listener) {
        String code = getNextCode();
    }

    public String getPrevCode() {
        int numCode = Integer.parseInt(code.substring(1));
        if(numCode % 2 == 0) {
            numCode -= 1;
        } else {
            numCode -= 9;
        }

        if(numCode < 0) {
            numCode += 1000;
            return String.format("A%03d", numCode);
        } else {
            return String.format("B%03d", numCode);
        }
    }

    public void fetchPrev(OnResourceProcessedListener listener) {
        String code = getPrevCode();
    }

    public String getNextCode() {
        int numCode = Integer.parseInt(code.substring(1));
        if(numCode % 2 == 0) {
            numCode += 9;
        } else {
            numCode += 1;
        }

        if(numCode > 100) {
            numCode -= 1000;
            return String.format("A%03d", numCode);
        } else {
            return String.format("B%03d", numCode);
        }
    }

    @Override
    public String toString() {
        return code;
    }
}
