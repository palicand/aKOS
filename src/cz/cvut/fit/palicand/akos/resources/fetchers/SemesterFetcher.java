package cz.cvut.fit.palicand.akos.resources.fetchers;

import cz.cvut.fit.palicand.akos.downloader.Downloader;
import cz.cvut.fit.palicand.akos.resources.OnResourceProcessedListener;
import cz.cvut.fit.palicand.akos.resources.ResourceHandler;
import cz.cvut.fit.palicand.akos.resources.Semester;
import org.xml.sax.SAXException;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 27/01/13
 * Time: 00:16
 * To change this template use File | Settings | File Templates.
 */

class SemesterHandler extends ResourceHandler {
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String CODE = "code";
    private static final String NAME = "name";
    private Semester semester;
    public SemesterHandler(OnResourceProcessedListener listener) {
        super(listener);
        semester = new Semester();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            if(localName.equalsIgnoreCase(START_DATE)) {
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(buffer.toString());
                semester.setStartDate(d);
            } else if(localName.equalsIgnoreCase(END_DATE)) {
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(buffer.toString());
                semester.setEndDate(d);
            } else if(localName.equalsIgnoreCase(CODE)) {
                semester.setCode(buffer.toString());
            } else if(localName.equalsIgnoreCase(NAME)) {
                semester.setName(buffer.toString());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        buffer.setLength(0);
    }

    @Override
    public void endDocument() throws SAXException {
        listener.onResourceProcessed(semester);
    }
}

public class SemesterFetcher extends ResourceFetcher {
    private final String BASE_URI  = "semesters";
    private String code;
    public SemesterFetcher(OnResourceProcessedListener listener, String code) {
        super(listener);
        this.code = code;
        downloader = new Downloader(getUri());
    }

    public SemesterFetcher(OnResourceProcessedListener listener) {
        this(listener, null);
    }

    @Override
    protected String getUri() {
        if(code == null) {
            return BASE_URI + "/current";
        } else {
            return BASE_URI + "/" + code;
        }
    }

    @Override
    public void run() {
        InputStream stream = downloader.download();
        parse(stream, new SemesterHandler(listener));
    }
}
