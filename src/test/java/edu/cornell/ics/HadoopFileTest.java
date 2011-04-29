package edu.cornell.ics;

import edu.cornell.ics.interfaces.IEntry;
import edu.cornell.ics.utilities.providers.EntryProvider;
import edu.cornell.ics.utilities.providers.HadoopFlatFormProvider;
import junit.framework.TestCase;

import java.io.IOException;
import org.junit.Ignore;
import org.junit.Test;

public class HadoopFileTest {

    @Ignore
    @Test
    public void Write() throws IOException {
        HadoopFlatFormProvider hfp = new HadoopFlatFormProvider("C:/KiyanHadoop/hadoopTestFileWithoutFilter.txt");
//        HadoopFlatFormProvider hfp = new HadoopFlatFormProvider("C:/KiyanHadoop/hadoopTestfileWithFilter.txt");
        hfp.OpenFile();
        Integer i;
        IEntry currentEntry;
        EntryProvider provider = new EntryProvider();
        for (i = 1; i < 1632441; i++) {
            currentEntry = provider.LoadByIndexNumber(i.toString());
            if (currentEntry != null) {
                hfp.Save(currentEntry);
            }
            if (i % 1000 == 0) {
                System.out.println(i);
            }
        }
        hfp.CloseFile();
        System.out.println("Woot. Got the hadoop File.");
    }
}
