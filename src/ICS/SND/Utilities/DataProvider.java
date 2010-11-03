package ICS.SND.Utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;

public class DataProvider implements IDataProvider {

	@Override
	public void Save(IEntry currentEntry) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.save(currentEntry);
		session.flush();
		session.close();
	}

}
