package ICS.SND.Tests;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ICS.SND.Entities.Author;
import ICS.SND.Entities.Entry;
import ICS.SND.Entities.Query;
import ICS.SND.Interfaces.IQuery;
import ICS.SND.Utilities.Providers.HibernateDataProvider;

public class AuthorTest {
	private static final Logger log = Logger.getLogger(AuthorTest.class);
	private static HibernateDataProvider<Entry> entryProvider;
	private static HibernateDataProvider<Author> authorProvider;

	@BeforeClass
	public static void setup(){
		authorProvider = new HibernateDataProvider<Author>();
		entryProvider = new HibernateDataProvider<Entry>();
	}
	
	@AfterClass
    public static void cleaup() {
		log.debug("starting teardown");
		IQuery qry = new Query("from Author where authorname=:authorname");
		qry.setParameter("authorname", "Jason");
		Author a = authorProvider.Load(qry);
		authorProvider.Delete(a);
		qry.setParameter("authorname", "Karan");
		a = authorProvider.Load(qry);
		authorProvider.Delete(a);
		qry = new Query("from Entry where title=:title");
		qry.setParameter("title", "Some Book");
		Entry e = entryProvider.Load(qry);
		entryProvider.Delete(e);
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
    	e.addAuthor(author1);
    	e.addAuthor(author1);
    	e.addAuthor(author2);
    	entryProvider.Save(e);
    }
}