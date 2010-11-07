package ICS.SND.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import ICS.SND.Entities.Entry;
import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;

public class HibernateDataProvider implements IDataProvider {

    @Override
    public void Save(IEntry currentEntry) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(currentEntry);
        session.flush();
        tx.commit();
        session.close();
    }

    @Override
    public IEntry Load(int id) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	IEntry entry = new Entry();
    	session.load(entry, id);
    	session.flush();
    	session.close();
        return entry;
    }

    @SuppressWarnings("unchecked")
	@Override
    public java.util.List<IEntry> List(String title) {
    	List<IEntry> list = new ArrayList<IEntry>();
    	Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("from Entry where title=:title");
		q.setParameter("title", title);
		list = (java.util.List<IEntry>) q.list();
		session.close();
		if(list.size() > 0){
			return list;
		}
		else{
			return null;
		}
    }

	@Override
	public IEntry LoadByTitle(String title) {
		IEntry entry=null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("from Entry where title=:title");
		q.setParameter("title", title);
		entry = (IEntry) q.uniqueResult();
		session.close();
		return entry;
	}

	@Override
	public void Update(IEntry currentEntry) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(currentEntry);
        session.flush();
        tx.commit();
        session.close();
	}

	@Override
	public IEntry LoadByIndexNumber(String indexNumber) {
		IEntry entry=null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("from Entry where indexNumber='" + indexNumber + "'");
		entry = (IEntry) q.uniqueResult();
		session.close();
		return entry;
	}
}
