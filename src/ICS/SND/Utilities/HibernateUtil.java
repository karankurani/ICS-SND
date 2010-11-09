package ICS.SND.Utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import ICS.SND.Entities.DivergenceEntry;
import ICS.SND.Entities.Entry;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            AnnotationConfiguration config = new AnnotationConfiguration().configure("hibernate.cfg.xml");
            config.addPackage("ICS.SND.Entities")
            .addAnnotatedClass(Entry.class)
            .addAnnotatedClass(DivergenceEntry.class);
            return config.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
