package cz.cvut.fit.palicand.akos.resources;

import android.util.Log;
import cz.cvut.fit.palicand.akos.downloader.Downloader;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 17/01/13
 * Time: 00:58
 * To change this template use File | Settings | File Templates.
 */

public abstract class ResourceFetcher implements Runnable {

    protected final OnResourceProcessedListener listener;
    protected String fields;
    protected void parse(InputStream stream, DefaultHandler handler) {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(stream, handler);
        } catch (ParserConfigurationException e) {
            Log.e("KOS", "exception", e);
        } catch (SAXException e) {
            Log.e("KOS", "exception", e);
        } catch (IOException e) {
            Log.e("KOS", "exception", e);
        }
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public ResourceFetcher(OnResourceProcessedListener listener) {
        this.listener = listener;
    }
}
