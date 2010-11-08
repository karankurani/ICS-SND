package ICS.SND.Tests;

import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;
import ICS.SND.Utilities.FlatFileCitationReader;
import ICS.SND.Utilities.Processor;
import org.apache.log4j.Logger;
import org.junit.Test;

public class CitationFlatFileParserTest {
    private static final Logger log = Logger.getLogger(DBLPEntryReaderTest.class);

    @Test
    public void FlatFileCitationReaderReadsFileAndStoresInDB() {
        IProcessor processor = new Processor();
        IReader reader = new FlatFileCitationReader(UnitTests.DATA_PATH + "citationTestFile.txt");
        reader.Process(processor);
        log.info("Woot! Successfully read the citation file.");
    }
}