package ICS.SND.Tests;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import ICS.SND.Entities.Entry;
import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Utilities.DataProvider;

public class HibernateTest extends TestCase 
{
	static IDataProvider provider;
	
	@BeforeClass
    public static void oneTimeSetUp() {
		System.out.println("One Time Setup");
    }

	@Test
	public void testEntryReader()
	{
		System.out.println("Starting Test");
		provider = new DataProvider();
		IEntry entry = new Entry();
		entry.setTitle("My Title");
		provider.Save(entry);
	}
}
