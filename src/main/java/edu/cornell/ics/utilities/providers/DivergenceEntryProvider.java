package edu.cornell.ics.utilities.providers;

import edu.cornell.ics.entities.Query;
import java.util.List;

import org.apache.log4j.Logger;

import edu.cornell.ics.interfaces.IDataProvider;
import edu.cornell.ics.interfaces.IDivergenceEntry;
import edu.cornell.ics.interfaces.IQuery;

public class DivergenceEntryProvider {
    private static final Logger log = Logger.getLogger(DivergenceEntryProvider.class);
    IDataProvider<IDivergenceEntry> provider = new HibernateDataProvider<IDivergenceEntry>();
   
    public List<IDivergenceEntry> ListBySeedTitle(String title) {
        log.debug("listing title " + title);
        IQuery q = new Query("from DivergenceEntry de where de.seedPaper.title=:title");
        q.setParameter("title", title);
        return (List<IDivergenceEntry>) provider.List(q);
    }

    public void Save(IDivergenceEntry entry) {
        log.debug("saving " + entry.getId());
        provider.Save(entry);
    }

    public void Update(IDivergenceEntry entry) {
        log.debug("updating " + entry.getId());
        provider.Update(entry);
    }

    public void Delete(IDivergenceEntry entry) {
        log.debug("deleting " + entry.getId());
        provider.Delete(entry);
    }
}
