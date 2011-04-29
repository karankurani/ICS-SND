package edu.cornell.ics;

import static org.junit.Assert.*;

import java.util.*;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.cornell.ics.entities.DivergenceEntry;
import edu.cornell.ics.entities.Entry;
import edu.cornell.ics.interfaces.IDivergenceEntry;
import edu.cornell.ics.interfaces.IEntry;
import edu.cornell.ics.utilities.providers.DivergenceEntryProvider;
import edu.cornell.ics.utilities.providers.EntryProvider;
import org.junit.Ignore;

public class DivergenceEntryTest {
    private static final Logger log = Logger.getLogger(DivergenceEntryTest.class);
    private static DivergenceEntryProvider dProvider;
    private static EntryProvider eProvider;
    private static List<Entry> papers;

    // Uncomment this if you want ton run these tests.
	// @BeforeClass
    public static void oneTimeSetUp() {
        log.info("One Time Setup");
        dProvider = new DivergenceEntryProvider();
        eProvider = new EntryProvider();
        papers = new ArrayList<Entry>();
        papers.add(createNewEntry("Jason Marcell", "Paper 1", true));
        papers.add(createNewEntry("Karan Kurani",  "Paper 2", true));
        papers.add(createNewEntry("Jason Marcell", "Paper 3", false));
        papers.add(createNewEntry("Karan Kurani",  "Paper 4", false));
    }

    // Uncomment this if you want ton run these tests.
	// @AfterClass
    public static void tearDown() {
        for(IDivergenceEntry entry : dProvider.ListBySeedTitle(papers.get(0).getTitle())) {
            dProvider.Delete(entry);
        }
        for(IDivergenceEntry entry : dProvider.ListBySeedTitle(papers.get(1).getTitle())) {
            dProvider.Delete(entry);
        }
        for(IEntry paper : papers) {
            eProvider.Delete(paper);
        }
    }

    @Test
    @Ignore
    public void Save() {
        IDivergenceEntry de = new DivergenceEntry();
        de.setSeedPaper(papers.get(0));
        de.setOtherPaper(papers.get(2));
        de.setDivergenceValue(0.5);
        dProvider.Save(de);
    }
    
    @Test
    @Ignore
    public void List() {
        List<IDivergenceEntry> divergenceEntries = dProvider.ListBySeedTitle("Paper 1");
        assertTrue(divergenceEntries != null && divergenceEntries.size() > 0);
        for(IDivergenceEntry divergenceEntry : divergenceEntries) {
            Entry otherPaper = divergenceEntry.getOtherPaper();
            log.debug("I found this paper: " + otherPaper.getTitle());
            log.debug("   with this value: " + divergenceEntry.getDivergenceValue());
        }
    }
    
    private static Entry createNewEntry(String author, String title, boolean isSeed) {
        Entry entry = new Entry();
        entry.setAuthor(author);
        entry.setTitle(title);
        entry.setIsSeed(isSeed);
        eProvider.Save(entry);
        return entry;
    }
}
