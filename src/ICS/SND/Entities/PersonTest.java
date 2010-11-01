package ICS.SND.Entities;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;


public class PersonTest extends TestCase 
{
	public void testItRuns()
	{
		Assert.assertTrue(true);
	}
	public void testEntryReader()
	{
		IReader reader = new DBLPEntryReader("testFiles/test.xml");
		List<IEntry> entries = reader.List();
		Assert.assertTrue(entries.size() > 0);
		for (IEntry entry : entries)
		{
			Assert.assertTrue(entry.getTitle() != null);
		}
	}
}
