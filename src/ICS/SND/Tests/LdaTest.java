package ICS.SND.Tests;

import junit.framework.TestCase;
import ICS.SND.Entities.LDABase;

public class LdaTest extends TestCase{
	public void testLDATest(){
//		LDABase lbase = new LDABase("../ICS-SND/src/ICS/SND/Tests/ldaTestFile.txt");
		LDABase lbase = new LDABase("C:/hadoopTestFileWithoutFilter.txt");
		lbase.startEpochs();
	}
}
