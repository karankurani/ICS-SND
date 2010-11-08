package ICS.SND.Tests;

import ICS.SND.Entities.Entry;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Utilities.Providers.EntryProvider;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class HibernateTest {
    private static final Logger log = Logger.getLogger(HibernateTest.class);
    static EntryProvider provider;
    private static final String ORIGINAL_TITLE = "My Title";
    private static final String UPDATE_TITLE = "New Title";

    @BeforeClass
    public static void oneTimeSetUp() {
        log.info("One Time Setup");
        provider = new EntryProvider();
        List<IEntry> entries = provider.ListByTitle(ORIGINAL_TITLE);
        for (IEntry e : entries) {
            provider.Delete(e);
        }
        entries = provider.ListByTitle(UPDATE_TITLE);
        for (IEntry e : entries) {
            provider.Delete(e);
        }
    }

    @Test
    public void testHibernate() {
        IEntry entry = new Entry();
        entry.setTitle(ORIGINAL_TITLE);
        provider.Save(entry);
    }

    @Test
    public void testLoadByTitle() {
        IEntry entry = provider.LoadByTitle(ORIGINAL_TITLE);
        System.out.println(entry.toString());
    }

    @Test
    public void testUpdateByTitle() {
        IEntry entry = provider.LoadByTitle(ORIGINAL_TITLE);
        entry.setTitle(UPDATE_TITLE);
        provider.Update(entry);
        System.out.println(entry.toString());
    }
}
