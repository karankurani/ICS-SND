package ICS.SND.Tests;

import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;
import ICS.SND.Utilities.DBLPEntryReader;
import ICS.SND.Utilities.Processor;
import org.apache.log4j.Logger;
import org.junit.Test;

public class DBLPEntryReaderTest {
    private static final Logger log = Logger.getLogger(DBLPEntryReaderTest.class);

    @Test
    public void DBLPEntryReaderReadsXMLAndStoresInDatabase() {
        IProcessor processor = new Processor();
        IReader reader = new DBLPEntryReader(UnitTests.DATA_PATH + "test.xml");
        reader.Process(processor);
        log.info("Woot. Everything in Database.");
    }
}