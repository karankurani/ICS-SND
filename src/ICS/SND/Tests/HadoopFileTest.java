package ICS.SND.Tests;

import ICS.SND.Interfaces.IEntry;
import ICS.SND.Utilities.Providers.EntryProvider;
import ICS.SND.Utilities.Providers.HadoopFlatFormProvider;
import junit.framework.TestCase;

import java.io.IOException;

public class HadoopFileTest extends TestCase {
    public void testWrite() throws IOException {
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
