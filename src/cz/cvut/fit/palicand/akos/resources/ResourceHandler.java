package cz.cvut.fit.palicand.akos.resources;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 17/01/13
 * Time: 18:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class ResourceHandler extends DefaultHandler {
    public final OnResourceProcessedListener listener;
    protected StringBuilder buffer;

    public ResourceHandler(OnResourceProcessedListener listener) {
        this.listener = listener;
        buffer = new StringBuilder();
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //if(buffer == null || length > buffer.length)
        buffer.append(ch, start, length);
    }




}
