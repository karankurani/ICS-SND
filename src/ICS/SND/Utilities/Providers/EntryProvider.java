package ICS.SND.Utilities.Providers;

import ICS.SND.Entities.Entry;
import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IQuery;

import java.util.List;

@SuppressWarnings({"unchecked"})
public class EntryProvider {
    IDataProvider provider = new HibernateDataProvider<Entry>();

    public IEntry LoadByTitle(String title) {
        IQuery q = new ICS.SND.Entities.Query("from Entry where title=:title");
        q.setParameter("title", title);
        return (IEntry) provider.Load(q);
    }

    public IEntry LoadByIndexNumber(String indexNumber) {
        IQuery q = new ICS.SND.Entities.Query("from Entry where indexNumber=:indexNumber");
        q.setParameter("indexNumber", indexNumber);
        return (IEntry) provider.Load(q);
    }

    public List<IEntry> ListByTitle(String title) {
        IQuery q = new ICS.SND.Entities.Query("from Entry where title=:title");
        q.setParameter("title", title);
        return (List<IEntry>) provider.List(q);
    }

    public void Save(IEntry entry) {
        provider.Save(entry);
    }

    public void Update(IEntry entry) {
        provider.Update(entry);
    }

    public void Delete(IEntry entry) {
        provider.Delete(entry);
    }
}
