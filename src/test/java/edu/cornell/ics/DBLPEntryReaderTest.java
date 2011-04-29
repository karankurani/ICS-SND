package edu.cornell.ics;

import edu.cornell.ics.interfaces.IProcessor;
import edu.cornell.ics.interfaces.IReader;
import edu.cornell.ics.utilities.DBLPEntryReader;
import edu.cornell.ics.utilities.Processor;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

public class DBLPEntryReaderTest {
    private static final Logger log = Logger.getLogger(DBLPEntryReaderTest.class);

    @Test
    @Ignore
    public void DBLPEntryReaderReadsXMLAndStoresInDatabase() {
        IProcessor processor = new Processor();
        IReader reader = new DBLPEntryReader(UnitTests.DATA_PATH + "test.xml");
        reader.Process(processor);
        log.info("Woot. Everything in Database.");
    }
}