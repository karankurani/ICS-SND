package ICS.SND.Utilities.Providers;

import ICS.SND.Entities.Entry;
import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IQuery;
import org.apache.log4j.Logger;

import java.util.List;

@SuppressWarnings({"unchecked"})
public class EntryProvider {
    private static final Logger log = Logger.getLogger(EntryProvider.class);
    IDataProvider provider = new HibernateDataProvider<Entry>();

    public IEntry LoadByTitle(String title) {
        log.debug("loading title " + title);
        IQuery q = new ICS.SND.Entities.Query("from Entry where title=:title");
        q.setParameter("title", title);
        return (IEntry) provider.Load(q);
    }

    public IEntry LoadByIndexNumber(String indexNumber) {
        log.debug("loading index " + indexNumber);
        IQuery q = new ICS.SND.Entities.Query("from Entry where indexNumber=:indexNumber");
        q.setParameter("indexNumber", indexNumber);
        return (IEntry) provider.Load(q);
    }

    public List<IEntry> ListByTitle(String title) {
        log.debug("listing title " + title);
        IQuery q = new ICS.SND.Entities.Query("from Entry where title=:title");
        q.setParameter("title", title);
        return (List<IEntry>) provider.List(q);
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
