package edu.cornell.ics.utilities.providers;

import edu.cornell.ics.entities.Author;
import edu.cornell.ics.entities.Query;
import edu.cornell.ics.interfaces.IDataProvider;
import edu.cornell.ics.interfaces.IEntry;
import edu.cornell.ics.interfaces.IQuery;
import org.apache.log4j.Logger;

import java.util.List;

public class EntryProvider {
    private static final Logger log = Logger.getLogger(EntryProvider.class);
    IDataProvider<IEntry> provider = new HibernateDataProvider<IEntry>();

    public IEntry LoadByTitle(String title) {
        log.debug("loading title " + title);
        IQuery q = new Query("from Entry where title=:title");
        q.setParameter("title", title);
        return (IEntry) provider.Load(q);
    }

    public IEntry LoadByIndexNumber(String indexNumber) {
        log.debug("loading index " + indexNumber);
        IQuery q = new Query("from Entry where indexNumber=:indexNumber");
        q.setParameter("indexNumber", indexNumber);
        return (IEntry) provider.Load(q);
    }

    public List<IEntry> ListByTitle(String title) {
        log.debug("listing title " + title);
        IQuery q = new Query("from Entry where title=:title");
        q.setParameter("title", title);
        return (List<IEntry>) provider.List(q);
    }
    
    public List<IEntry> ListByAuthor(Author author) {
    	log.debug("listing by author " + author.getAuthorName());
        return (List<IEntry>) provider.ListBySQL("select * from Entry e left outer join entry_author ea on ea.Entry_id = e.id" 
                                                + " left outer join author a on a.authorId = ea.authors_authorId"
                                                + " where a.authorId =" + author.getAuthorId());
    }

    public void Save(IEntry entry) {
        log.debug("saving " + entry.getTitle());
        provider.Save(entry);
    }

    public void Update(IEntry entry) {
        log.debug("updating " + entry.getTitle());
        provider.Update(entry);
    }

    public void Delete(IEntry entry) {
        log.debug("deleting " + entry.getTitle());
        provider.Delete(entry);
    }
}
