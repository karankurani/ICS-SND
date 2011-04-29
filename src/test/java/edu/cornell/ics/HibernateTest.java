package edu.cornell.ics;

import edu.cornell.ics.entities.Entry;
import edu.cornell.ics.interfaces.IEntry;
import edu.cornell.ics.utilities.providers.EntryProvider;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import org.junit.Ignore;

public class HibernateTest {
    private static final Logger log = Logger.getLogger(HibernateTest.class);
    static EntryProvider provider;
    private static final String ORIGINAL_TITLE = "My Title";
    private static final String UPDATE_TITLE = "New Title";

    // Uncomment this if you want ton run these tests.
	// @BeforeClass
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
    @Ignore
    public void Save() {
        IEntry entry = new Entry();
        entry.setTitle(ORIGINAL_TITLE);
        provider.Save(entry);
    }

    @Test
    @Ignore
    public void LoadByTitle() {
        IEntry entry = provider.LoadByTitle(ORIGINAL_TITLE);
        System.out.println(entry.toString());
    }

    @Test
    @Ignore
    public void UpdateByTitle() {
        IEntry entry = provider.LoadByTitle(ORIGINAL_TITLE);
        entry.setTitle(UPDATE_TITLE);
        provider.Update(entry);
        System.out.println(entry.toString());
    }
}