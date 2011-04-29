package edu.cornell.ics.utilities.providers;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import edu.cornell.ics.entities.Entry;
import edu.cornell.ics.interfaces.IDataProvider;
import edu.cornell.ics.interfaces.IQuery;
import edu.cornell.ics.utilities.HibernateUtil;

@SuppressWarnings({"unchecked"})
public class HibernateDataProvider<T> implements IDataProvider<T> {

    @Override
    public void Save(T item) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(item);
        session.flush();
        tx.commit();
        session.close();
    }

    @Override
    public void Update(T item) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(item);
        session.flush();
        tx.commit();
        session.close();
    }

    @Override
    public T Load(IQuery q) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query qry = convertToHibernateQuery(q, session);
        T entry = (T) qry.uniqueResult();
        session.close();
        return entry;
    }

    @Override
    public List<T> List(IQuery q) {
        List<T> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query qry = convertToHibernateQuery(q, session);
        list = (List<T>) qry.list();
        session.close();
        return list;
    }

    @Override
    public List<T> ListBySQL(String sql){
        List<T> list;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createSQLQuery(sql).addEntity(Entry.class);
        list = (List<T>) q.list();
        session.close();
        return list;
    }
    
    @Override
    public void Delete(T item) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(item);
        session.flush();
        tx.commit();
        session.close();
    }

    private Query convertToHibernateQuery(IQuery q, Session session) {
        Query retVal = session.createQuery(q.getQueryString());
        Hashtable<String, Object> parameters = q.getParameters();
        Enumeration<String> keys = parameters.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            Object value = parameters.get(key);
            retVal.setParameter(key, value);
        }
        return retVal;
    }
}