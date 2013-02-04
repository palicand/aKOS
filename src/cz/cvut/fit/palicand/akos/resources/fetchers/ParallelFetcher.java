package cz.cvut.fit.palicand.akos.resources.fetchers;

import android.util.Log;
import cz.cvut.fit.palicand.akos.downloader.Downloader;
import cz.cvut.fit.palicand.akos.resources.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;
import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 28/01/13
 * Time: 09:36
 * To change this template use File | Settings | File Templates.
 */

class ParallelHandler extends ResourceHandler {
    private static final String COURSE = "course";
    private static final String PARALLEL_TYPE = "parallelType";
    private static final String PARITY = "parity";
    private static final String DAY = "day";
    private static final String FIRST_HOUR = "firstHour";
    private static final String DURATION = "duration";
    private Parallel parallel;
    private TimetableSlot slot;
    public ParallelHandler(OnResourceProcessedListener listener) {
        super(listener);
        parallel = new Parallel();
        slot = new TimetableSlot();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(localName.equalsIgnoreCase(COURSE)) {
            parallel.setCourse(buffer.toString());
        } else if (localName.equalsIgnoreCase(PARALLEL_TYPE)) {
            parallel.setType(resolveType(buffer.toString()));
        } else if (localName.equalsIgnoreCase(PARITY)) {
            slot.setParity(resolveParity(buffer.toString()));
        } else if(localName.equalsIgnoreCase(DAY)) {
            slot.setDay(Integer.parseInt(buffer.toString()));
        } else if (localName.equalsIgnoreCase(FIRST_HOUR)) {
            slot.setFirstHour(Integer.parseInt(buffer.toString()));
        } else if (localName.equalsIgnoreCase(DURATION)) {
            slot.setDuration(Integer.parseInt(buffer.toString()));
        } else if (localName.equalsIgnoreCase("entry")) {
            parallel.setSlot(slot);
            listener.onResourceProcessed(parallel);
            parallel = new Parallel();
            slot = new TimetableSlot();
        }
        buffer.setLength(0);
    }

    private Parity resolveParity(String parity) {
        int c = 0;
        for(Parity type : Parity.values()) {
            ++c;
            if(type.name().equalsIgnoreCase(parity)) return type;
        }
        return null;
    }

    private ParallelType resolveType(String name) {
        for(ParallelType type : ParallelType.values()) {
            if(name.equalsIgnoreCase(type.name())) return type;
        }
        return ParallelType.UNDEFINED;
    }
}

public class ParallelFetcher extends ResourceFetcher {
    private final static String BASE_URI = "students/";
    private String username;
    public ParallelFetcher(String username, String semester, OnResourceProcessedListener listener) {
        super(listener);
        this.username = username;
        downloader = new Downloader(getUri());
        setSemester(semester);
    }

    @Override
    protected String getUri() {
        return BASE_URI + username + "/parallels";
    }

    @Override
    public void run() {
        InputStream stream = downloader.download();
        parse(stream, new ParallelHandler(listener));
    }
}
