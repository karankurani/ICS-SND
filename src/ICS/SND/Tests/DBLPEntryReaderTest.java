package ICS.SND.Tests;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IReader;
import ICS.SND.Utilities.DBLPEntryReader;


public class DBLPEntryReaderTest extends TestCase 
{
	public void testItRuns()
	{
		Assert.assertTrue(true);
	}
	public void testEntryReader()
	{
		IReader reader = new DBLPEntryReader("../ICS-SND/src/ICS/SND/Tests/test.xml");
		List<IEntry> entries = reader.List();
		Assert.assertTrue(entries.size() > 0);
		for (IEntry entry : entries)
		{
			Assert.assertTrue(entry.getTitle() != null);
		}
	}
}
