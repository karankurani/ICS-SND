package ICS.SND.Tests;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import ICS.SND.Entities.Entry;
import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Utilities.HibernateDataProvider;

public class HibernateTest extends TestCase {
    static IDataProvider provider;

    @BeforeClass
    public static void oneTimeSetUp() {
        System.out.println("One Time Setup");
    }

//    @Test
//    public void testHibernate() {
//        System.out.println("Starting Test");
//        provider = new HibernateDataProvider();
//        IEntry entry = new Entry();
//        entry.setTitle("My Title");
//        provider.Save(entry);
//    }
    
    @Test
    public void testLoadByTitle(){
    	provider = new HibernateDataProvider();
    	IEntry entry = provider.LoadByTitle("My Title");
    	System.out.println(entry.toString());
    }
    
//    @Test
//    public void testUpdateByTitle(){
//    	provider = new HibernateDataProvider();
//    	IEntry entry = provider.LoadByTitle("Active Database Systems.");
//    	entry.setTitle("Jason Amazoner");
//    	provider.Update(entry);
//    	System.out.println(entry.toString());
//    }
    
//    @Test
//    public void testLoadEntry(){
//    	provider = new DataProvider();
//    	for(int i=4;i<11;i++)
//    	{
//    		IEntry entry = provider.Load(i);
//    		System.out.println(entry.getTitle());
//    	}
//    }
}
