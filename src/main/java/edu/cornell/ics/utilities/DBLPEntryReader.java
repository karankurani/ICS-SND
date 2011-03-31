package edu.cornell.ics.utilities;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import edu.cornell.ics.interfaces.IProcessor;
import edu.cornell.ics.interfaces.IReader;

public class DBLPEntryReader implements IReader {
    private static final Logger log = Logger.getLogger(DBLPEntryReader.class);
    private String filePath;

    public DBLPEntryReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void Process(IProcessor processor) {
        XMLReader parser;
        try {
            parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(new EntryHandler(processor));
            parser.parse(filePath);
        } catch (SAXException e) {
            log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
    }
}
