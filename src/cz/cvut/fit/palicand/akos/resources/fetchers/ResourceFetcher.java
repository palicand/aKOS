package cz.cvut.fit.palicand.akos.resources.fetchers;

import android.content.Context;
import android.util.Log;
import cz.cvut.fit.palicand.akos.AndroidKOS;
import cz.cvut.fit.palicand.akos.downloader.Downloader;
import cz.cvut.fit.palicand.akos.resources.OnResourceProcessedListener;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 17/01/13
 * Time: 00:58
 * To change this template use File | Settings | File Templates.
 */

public abstract class ResourceFetcher implements Runnable {

    protected final OnResourceProcessedListener listener;
    protected Downloader downloader;
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
        downloader.setParameter("fields", fields);
    }

    public ResourceFetcher(OnResourceProcessedListener listener) {
        this.listener = listener;
    }

    protected abstract String getUri();

    protected InputStream openFile(String name)  {
        try {
            return AndroidKOS.getInstance().openFileInput(name);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    protected void saveFile(InputStream inputStream, String name) throws IOException {
        FileOutputStream outputStream = AndroidKOS.getInstance().openFileOutput(name, Context.MODE_PRIVATE);
        byte buffer[] = new byte[512];
        int count = inputStream.read(buffer);
        while (count != -1) {
            outputStream.write(buffer, 0, count);
            count = inputStream.read(buffer);
        }
        outputStream.close();
    }

    public void setSemester(String semester) {
        downloader.setParameter("sem", semester);
    }
}
