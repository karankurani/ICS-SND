package edu.cornell.ics;

import java.io.*;
import edu.cornell.ics.interfaces.IEntry;
import edu.cornell.ics.utilities.providers.EntryProvider;
import junit.framework.TestCase;
import org.junit.Test;

public class SeedPaperLocator extends TestCase {
    @Test
    public void findPapers() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("filename"));
        EntryProvider provider = new EntryProvider();
        String line = "";
        while ((line = br.readLine()) != null) {
            IEntry currentEntry = provider.LoadByTitle(line.trim());
            if (currentEntry != null) {
                System.out.println(currentEntry.getIndexNumber() + " Title:" + currentEntry.getTitle());
            } else {
                System.out.println("NULL Title:" + line.trim());
            }
            currentEntry = null;
        }
        provider.LoadByTitle("");
    }
}
