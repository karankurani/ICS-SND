package ICS.SND.Utilities;

import org.hibernate.classic.Session;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;

public class DataProvider implements IDataProvider {

    @Override
    public void Save(IEntry currentEntry) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.save(currentEntry);
        session.flush();
        session.close();
    }

    @Override
    public IEntry Load(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public java.util.List<IEntry> List() {
        // TODO Auto-generated method stub
        return null;
    }

}
