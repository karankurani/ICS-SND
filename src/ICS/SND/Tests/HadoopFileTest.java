package ICS.SND.Tests;

import java.io.IOException;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Utilities.HadoopFlatFormProvider;
import ICS.SND.Utilities.HibernateDataProvider;
import junit.framework.TestCase;

public class HadoopFileTest extends TestCase {
	public void testWrite() throws IOException{
		HadoopFlatFormProvider hfp = new HadoopFlatFormProvider("C:/KiyanHadoop/hadoopTestFileWithoutFilter.txt");
//		HadoopFlatFormProvider hfp = new HadoopFlatFormProvider("C:/KiyanHadoop/hadoopTestfileWithFilter.txt");
		hfp.OpenFile();
		Integer i=0;
		IEntry currentEntry;
		IDataProvider provider = new HibernateDataProvider();
		for(i=1;i<1632441;i++)
		{
//			currentEntry = hfp.LoadWithBookTitleFilter(i.toString());
			currentEntry = provider.LoadByIndexNumber(i.toString());
			if(currentEntry != null)
			{
				hfp.Save(currentEntry);
			}
			if(i%1000==0){
				System.out.println(i);
			}
		}
		hfp.CloseFile();
		System.out.println("Woot. Got the hadoop File.");
	}
}
