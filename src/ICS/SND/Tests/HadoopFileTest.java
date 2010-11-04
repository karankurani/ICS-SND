package ICS.SND.Tests;

import java.io.IOException;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Utilities.HadoopFlatFormProvider;
import ICS.SND.Utilities.HibernateDataProvider;
import junit.framework.TestCase;

public class HadoopFileTest extends TestCase {
	public void testWrite() throws IOException{
		HadoopFlatFormProvider hfp = new HadoopFlatFormProvider("../ICS-SND/src/ICS/SND/Tests/hadoopTestfile.txt");
		hfp.OpenFile();
		IDataProvider provider = new HibernateDataProvider();
    	IEntry currentEntry = provider.LoadByTitle("My Title"); 
		hfp.Save(currentEntry);
		System.out.println(currentEntry.toString());
		hfp.CloseFile();
	}
}
