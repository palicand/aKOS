package cz.cvut.fit.palicand.akos.resources.fetchers;

import cz.cvut.fit.palicand.akos.downloader.Downloader;
import cz.cvut.fit.palicand.akos.resources.OnResourceProcessedListener;
import cz.cvut.fit.palicand.akos.resources.ResourceHandler;
import cz.cvut.fit.palicand.akos.resources.Student;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 17/01/13
 * Time: 00:59
 * To change this template use File | Settings | File Templates.
 */

class StudentHandler extends ResourceHandler {
    private Student student;

    private static final String firstName;
    private static final String lastName;
    private static final String grade;
    private static final String username;
    private static final String email;

    static {
        email = "email";
        username = "username";
        grade = "grade";
        lastName = "lastName";
        firstName = "firstName";
    }

    public StudentHandler(OnResourceProcessedListener listener) {
        super(listener);
        student = new Student();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(localName.equalsIgnoreCase(firstName)) {
            student.setFirstName(new String(buffer));
        } else if(localName.equalsIgnoreCase(lastName)) {
            student.setLastName(new String(buffer));
        } else if(localName.equalsIgnoreCase(grade)) {
            student.setGrade(Integer.parseInt(new String(buffer)));
        } else if(localName.equalsIgnoreCase(username)) {
            student.setUsername(new String(buffer));
        } else if(localName.equalsIgnoreCase(email)) {
            student.setEmail(new String(buffer));
        }
        buffer.setLength(0);
    }

    @Override
    public void endDocument() throws SAXException {
        listener.onResourceProcessed(student);
    }
}

public class StudentFetcher extends ResourceFetcher {
    private static final String RESOURCE_URI;
    private final String username;
    static {
        RESOURCE_URI = "students/";
    }

    public StudentFetcher(String username, OnResourceProcessedListener listener) {
        super(listener);
        this.username = username;
        downloader = new Downloader(getUri());
    }

    @Override
    public void run() {
        InputStream stream = downloader.download();
        parse(stream, new StudentHandler(listener));
    }

    @Override
    protected String getUri() {
        return RESOURCE_URI + username;
    }
}
