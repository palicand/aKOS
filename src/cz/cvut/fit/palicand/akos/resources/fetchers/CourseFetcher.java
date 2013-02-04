package cz.cvut.fit.palicand.akos.resources.fetchers;

import cz.cvut.fit.palicand.akos.downloader.Downloader;
import cz.cvut.fit.palicand.akos.resources.*;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 25/01/13
 * Time: 00:05
 * To change this template use File | Settings | File Templates.
 */

class CourseHandler extends ResourceHandler {
    private static final String DESCRIPTION = "description";
    private static final String CLASSES_TYPE = "classesType";
    private static final String COMPLETION = "completion";
    private static final String CREDITS = "credits";

    private Course course;
    public CourseHandler(OnResourceProcessedListener listener) {
        super(listener);
        course = new Course();
    }


    @Override
    public void endDocument() throws SAXException {
        listener.onResourceProcessed(course);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(localName.equalsIgnoreCase(DESCRIPTION)) {
            course.setDescription(new String(buffer));
        } else if(localName.equalsIgnoreCase(CLASSES_TYPE)) {
            ClassType type = resolveType(buffer);
            course.addType(type);
        } else if (localName.equalsIgnoreCase(COMPLETION)) {
            CourseCompletion completion = resolveCompletion(buffer);
            course.setCompletion(completion);
        } else if (localName.equalsIgnoreCase(CREDITS)) {
            int credits = Integer.parseInt(new String(buffer));
            course.setCredits(credits);
        }
        buffer.setLength(0);
    }

    private CourseCompletion resolveCompletion(StringBuilder buffer) {
        String completionName = new String(buffer);
        for (CourseCompletion completion : CourseCompletion.values()) {
            if(completion.name().equalsIgnoreCase(completionName)) {
                return completion;
            }
        }

        return CourseCompletion.UNDEFINED;
    }

    private ClassType resolveType(StringBuilder buffer) {
        String typeName = new String(buffer);
        for(ClassType type : ClassType.values()) {
            if(typeName.equalsIgnoreCase(type.name()))
                return type;
        }
        return ClassType.UNDEFINED;
    }
}

public class CourseFetcher extends ResourceFetcher implements Runnable {

    private final String code;
    private static final String RESOURCE_URI = "courses/";
    public CourseFetcher(OnResourceProcessedListener listener) {
        this(null, listener);
    }


    public CourseFetcher(String code, OnResourceProcessedListener listener) {
        super(listener);
        this.code = code;
        downloader = new Downloader(getUri());
    }

    @Override
    protected String getUri() {
        if(code == null) {
            return RESOURCE_URI;
        } else {
            return RESOURCE_URI + code;
        }
    }

    private String getFileName() {
        return "course_" + code;
    }

    public void run() {
        downloader.setParameter("detail", "1");
        try {
            InputStream stream = openFile(getFileName());
            if(stream == null) {
                stream = downloader.download();
                saveFile(stream, getFileName());
                stream = openFile(getFileName());
            }
            parse(stream, new CourseHandler(listener));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
