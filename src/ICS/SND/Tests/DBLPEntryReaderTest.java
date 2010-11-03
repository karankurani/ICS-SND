package ICS.SND.Tests;

import junit.framework.TestCase;

import org.junit.BeforeClass;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;
import ICS.SND.Utilities.DBLPEntryReader;
import ICS.SND.Utilities.DataProvider;
import ICS.SND.Utilities.Processor;

public class DBLPEntryReaderTest extends TestCase 
{
	static IDataProvider provider;
	
	@BeforeClass
    public static void oneTimeSetUp() {
		provider = new DataProvider();
    }

	public void testEntryReader()
	{
		IProcessor processor = new Processor();
		IReader reader = new DBLPEntryReader("../ICS-SND/src/ICS/SND/Tests/test.xml");		
		reader.Process(processor);
	}
}