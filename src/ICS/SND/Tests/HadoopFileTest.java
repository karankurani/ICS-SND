package ICS.SND.Tests;

import java.io.IOException;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Utilities.HadoopFlatFormProvider;
import ICS.SND.Utilities.HibernateDataProvider;
import junit.framework.TestCase;

public class HadoopFileTest extends TestCase {
	public void testWrite() throws IOException{
//		HadoopFlatFormProvider hfp = new HadoopFlatFormProvider("../ICS-SND/src/ICS/SND/Tests/hadoopTestfile.txt");
		HadoopFlatFormProvider hfp = new HadoopFlatFormProvider("C:/KiyanHadoop/hadoopTestfileWithFilter.txt");
		hfp.OpenFile();
		Integer i=0;
		IEntry currentEntry;
		IDataProvider provider = new HibernateDataProvider();
		for(i=1;i<1632441;i++)
		{
			currentEntry = hfp.LoadWithBookTitleFilter(i.toString());
//			currentEntry = provider.LoadByIndexNumber(i.toString());
			if(currentEntry != null)
			{
				hfp.Save(currentEntry);
				System.out.println(currentEntry.toString());
			}
		}
		hfp.CloseFile();
	}
}
