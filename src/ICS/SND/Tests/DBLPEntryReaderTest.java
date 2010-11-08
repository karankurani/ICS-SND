package ICS.SND.Tests;

import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;
import ICS.SND.Utilities.DBLPEntryReader;
import ICS.SND.Utilities.Processor;
import junit.framework.TestCase;

public class DBLPEntryReaderTest extends TestCase {
    public void testEntryReader() {
        IProcessor processor = new Processor();
        IReader reader = new DBLPEntryReader("../ICS-SND/src/ICS/SND/Tests/TestData/test.xml");
        reader.Process(processor);
        System.out.println("Woot. Everything in Database.");
    }
}