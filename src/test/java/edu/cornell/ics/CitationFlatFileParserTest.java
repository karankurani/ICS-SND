package edu.cornell.ics;

import edu.cornell.ics.interfaces.IProcessor;
import edu.cornell.ics.interfaces.IReader;
import edu.cornell.ics.utilities.FlatFileCitationReader;
import edu.cornell.ics.utilities.Processor;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

public class CitationFlatFileParserTest {
    private static final Logger log = Logger.getLogger(CitationFlatFileParserTest.class);

    @Test
    @Ignore
    public void FlatFileCitationReaderReadsFileAndStoresInDB() {
        IProcessor processor = new Processor();
        IReader reader = new FlatFileCitationReader(UnitTests.DATA_PATH + "citationTestFile.txt");
        reader.Process(processor);
        log.info("Woot! Successfully read the citation file.");
    }
}