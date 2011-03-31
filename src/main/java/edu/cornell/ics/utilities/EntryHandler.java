package edu.cornell.ics.utilities;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.cornell.ics.entities.Entry;
import edu.cornell.ics.interfaces.IEntry;
import edu.cornell.ics.interfaces.IProcessor;

public class EntryHandler extends DefaultHandler {
    public EntryHandler(IProcessor processor) {
        this.processor = processor;
    }

    IEntry currentEntry;
    boolean title = false;
    boolean author = false;
    boolean publisher = false;
    boolean year = false;
    boolean isbn = false;
    boolean booktitle = false;
    boolean url = false;
    boolean pages = false;
    private IProcessor processor;

    public void startElement(String nsURI, String strippedName, String tagName,
                             Attributes attributes) throws SAXException {
        if (tagName.equalsIgnoreCase("incollection")
                || tagName.equalsIgnoreCase("book")
                || tagName.equalsIgnoreCase("proceedings")
                || tagName.equalsIgnoreCase("inproceedings")) {
            currentEntry = new Entry();
            currentEntry.setAuthor("");
            currentEntry.setType(tagName);
        }
        if (tagName.equalsIgnoreCase("title"))
            title = true;
        if (tagName.equalsIgnoreCase("author"))
            author = true;
        if (tagName.equalsIgnoreCase("publisher"))
            publisher = true;
        if (tagName.equalsIgnoreCase("year"))
            year = true;
        if (tagName.equalsIgnoreCase("isbn"))
            isbn = true;
        if (tagName.equalsIgnoreCase("booktitle"))
            booktitle = true;
        if (tagName.equalsIgnoreCase("url"))
            url = true;
        if (tagName.equalsIgnoreCase("pages"))
            pages = true;
    }

    public void endElement(String uri, String strippedName, String tagName)
            throws SAXException {
        if (tagName.equalsIgnoreCase("incollection")
                || tagName.equalsIgnoreCase("book")
                || tagName.equalsIgnoreCase("proceedings")
                || tagName.equalsIgnoreCase("inproceedings"))
            processor.Process(currentEntry);
    }

    public void characters(char[] ch, int start, int length) {
        if (title) {
            currentEntry.setTitle(new String(ch, start, length));
            title = false;
        }
        if (author) {
            String currentAuthor = currentEntry.getAuthor();
            if (currentAuthor.equals("")) {
                currentEntry.setAuthor(new String(ch, start, length));
            } else {
                currentEntry.setAuthor(currentAuthor + "|" + new String(ch, start, length));
            }
            author = false;
        }
        if (publisher) {
            currentEntry.setPublisher(new String(ch, start, length));
            publisher = false;
        }
        if (year) {
            currentEntry.setYear(new String(ch, start, length));
            year = false;
        }
        if (isbn) {
            currentEntry.setIsbn(new String(ch, start, length));
            isbn = false;
        }
        if (booktitle) {
            currentEntry.setBooktitle(new String(ch, start, length));
            booktitle = false;
        }
        if (url) {
            currentEntry.setUrl(new String(ch, start, length));
            url = false;
        }
        if (pages) {
            currentEntry.setPages(new String(ch, start, length));
            pages = false;
        }
    }
}