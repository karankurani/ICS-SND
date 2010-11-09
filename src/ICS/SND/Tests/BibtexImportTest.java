package ICS.SND.Tests;

import java.io.FileReader;
import java.util.List;

import junit.framework.TestCase;
import ICS.SND.Entities.Entry;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Utilities.Providers.HadoopFlatFormProvider;
import bibtex.dom.BibtexEntry;
import bibtex.dom.BibtexFile;
import bibtex.dom.BibtexNode;
import bibtex.parser.BibtexParser;

public class BibtexImportTest extends TestCase {
	public void testBibtexImport() throws Exception {
		BibtexParser btp = new BibtexParser(true);
		FileReader isr = new FileReader(UnitTests.DATA_PATH+"test.bib");
		BibtexFile btf = new BibtexFile();
		btp.parse(btf, isr);
		List<BibtexNode> bibNodes = btf.getEntries();
		//EntryProvider provider = new EntryProvider();

		HadoopFlatFormProvider hfp = new HadoopFlatFormProvider(UnitTests.DATA_PATH+"test.bib.lda.txt");
		hfp.OpenFile();
		for (BibtexNode node : bibNodes) {
			if (node instanceof BibtexEntry) {
				System.out.println(((BibtexEntry) node).getEntryType());
				BibtexEntry bentry = (BibtexEntry) node;
				IEntry entry = new Entry();
		        
		        String s = bentry.getFields().get("title").toString();
		        s = s.replaceAll("\\{", "");
		        s = s.replaceAll("\\}", "");
		        entry.setTitle(s);
		        
		        s = bentry.getFields().get("abstract").toString();
		        s = s.replaceAll("\\{", "");
		        s = s.replaceAll("\\}", "");
		        entry.setAbstractText(s);

		        Object o = bentry.getFields().get("author");
		        System.out.println(o.getClass().toString());
		        
		        s = bentry.getFields().get("author").toString();
		        s = s.replaceAll("\\{", "");
		        s = s.replaceAll("\\}", "");
		        entry.setAuthor(s);

		        hfp.Save(entry);
//				System.out.println(bentry.getFields());
			}
		}
		hfp.CloseFile();
	}
}
