package ICS.SND.Tests;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ICS.SND.Entities.Author;
import ICS.SND.Entities.Entry;
import ICS.SND.Entities.Query;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IQuery;
import ICS.SND.Utilities.Providers.EntryProvider;
import ICS.SND.Utilities.Providers.HibernateDataProvider;

public class AuthorTest {
	private static final Logger log = Logger.getLogger(AuthorTest.class);
	private static EntryProvider entryProvider;
	private static HibernateDataProvider<Author> authorProvider;

	@BeforeClass
	public static void setup(){
		authorProvider = new HibernateDataProvider<Author>();
		entryProvider = new EntryProvider();
	}
	
	@AfterClass
    public static void cleaup() {
		log.debug("starting teardown");
		Entry e = (Entry) entryProvider.LoadByTitle("Some Book");
        entryProvider.Delete(e);
        Entry e2 = (Entry) entryProvider.LoadByTitle("Some Book 2");
        entryProvider.Delete(e2);
		IQuery qry = new Query("from Author where authorname=:authorname");
		qry.setParameter("authorname", "Jason");
		Author a = authorProvider.Load(qry);
		authorProvider.Delete(a);
		qry.setParameter("authorname", "Karan");
		a = authorProvider.Load(qry);
		authorProvider.Delete(a);
		log.debug("ending teardown");
	}
	
    @Test
    public void createAuthor(){
    	Author author1 = new Author();
    	author1.setAuthorName("Jason");
    	authorProvider.Save(author1);
    	Author author2 = new Author();
    	author2.setAuthorName("Karan");
    	authorProvider.Save(author2);    	
    	Entry e = new Entry();
    	e.setTitle("Some Book");
    	Entry e2 = new Entry();
        e2.setTitle("Some Book 2");
    	e.addAuthor(author1);
    	e.addAuthor(author1);
    	e.addAuthor(author2);
    	e2.addAuthor(author1);
    	entryProvider.Save(e);
    	entryProvider.Save(e2);
    }
    
    @Test
    public void loadbyAuthor(){
        IQuery qry = new Query("from Author where authorname=:authorname");
        qry.setParameter("authorname", "Jason");
        Author author = authorProvider.Load(qry);
        Assert.assertNotNull("author should not be null", author);
        Assert.assertEquals("the names should be what we expect", "Jason", author.getAuthorName());
        List<IEntry> E = entryProvider.ListByAuthor(author);
        Assert.assertNotNull("E should not be null", E);
        Assert.assertTrue("E should have stuff in it", (E.size() > 0));
        for(IEntry entry : E){
            log.debug(entry.getTitle());
        }
    }
}