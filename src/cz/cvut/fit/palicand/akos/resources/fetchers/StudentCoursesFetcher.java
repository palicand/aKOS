package cz.cvut.fit.palicand.akos.resources.fetchers;

import cz.cvut.fit.palicand.akos.downloader.Downloader;
import cz.cvut.fit.palicand.akos.resources.CourseEnrollment;
import cz.cvut.fit.palicand.akos.resources.OnResourceProcessedListener;
import cz.cvut.fit.palicand.akos.resources.ResourceHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 17/01/13
 * Time: 01:08
 * To change this template use File | Settings | File Templates.
 */

class StudentCoursesHandler extends ResourceHandler {
    private static final String NAME = "name";
    private static final String COMPLETED = "completed";
    private static final String EXTERN = "extern";
    private static final String SEMESTER = "semester";
    private static final String COURSE = "course";
    private static final String ENTRY = "entry";
    private CourseEnrollment currentCourse;

    public StudentCoursesHandler(OnResourceProcessedListener listener) {
        super(listener);
        currentCourse = new CourseEnrollment();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(localName.equalsIgnoreCase(COURSE)) {
            String code = attributes.getValue("xlink:href");
            int codeStart = code.indexOf('/');
            currentCourse.setCode(code.substring(codeStart + 1, code.length() - 1));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(localName.equalsIgnoreCase(NAME)) {
            currentCourse.setAuthor(new String(buffer));
        } else if(localName.equalsIgnoreCase(COMPLETED)) {
            boolean completed = Boolean.parseBoolean(new String(buffer));
            currentCourse.setCompleted(completed);
        } else if(localName.equalsIgnoreCase(EXTERN)) {
            boolean extern = Boolean.parseBoolean(new String(buffer));
            currentCourse.setExtern(extern);
        } else if(localName.equalsIgnoreCase(SEMESTER)) {
            currentCourse.setSemester(new String(buffer));
        } else if(localName.equalsIgnoreCase(COURSE)) {
            currentCourse.setName(new String(buffer));
        } else if (localName.equalsIgnoreCase(ENTRY)) {
            listener.onResourceProcessed(currentCourse);
            currentCourse = new CourseEnrollment();
        }
        buffer.setLength(0);
    }
}

public class StudentCoursesFetcher extends ResourceFetcher {
    private final static String BASE_URI = "students/";
    private String username;
    public StudentCoursesFetcher(String username, OnResourceProcessedListener listener) {
        this(username, null, listener);
    }

    public StudentCoursesFetcher(String username, String semesterCode, OnResourceProcessedListener listener) {
        super(listener);
        this.username = username;
        downloader = new Downloader(getUri());
        setSemester(semesterCode);
    }

    @Override
    public void run() {
        InputStream stream = downloader.download();
        parse(stream, new StudentCoursesHandler(listener));
    }

    @Override
    protected String getUri() {
        return BASE_URI + username + "/enrolledCourses";
    }
}
