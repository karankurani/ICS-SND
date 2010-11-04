package ICS.SND.Tests;

import junit.framework.TestCase;

import org.junit.BeforeClass;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;
import ICS.SND.Utilities.FlatFileCitationReader;
import ICS.SND.Utilities.HibernateDataProvider;
import ICS.SND.Utilities.Processor;

public class CitationFlatFileParserTest extends TestCase {
    static IDataProvider provider;

    @BeforeClass
    public static void oneTimeSetUp() {
        provider = new HibernateDataProvider();
    }

    public void testEntryReader() {
        IProcessor processor = new Processor();
//        IReader reader = new FlatFileCitationReader("../ICS.SND/src/ICS/SND/Tests/citationTestFile.txt");
        IReader reader = new FlatFileCitationReader("C:/SND-LDA/DBLPOnlyCitationOct19.txt");
        reader.Process(processor);
        System.out.println("Woot! Succesfully Read the citation file.");
    }
}