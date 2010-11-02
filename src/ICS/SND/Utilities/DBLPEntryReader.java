package ICS.SND.Utilities;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IReader;

public class DBLPEntryReader implements IReader {
	private String filePath;

	public DBLPEntryReader(String filePath){
		this.filePath = filePath;
	}

	@Override
	public java.util.List<IEntry> List() {
		List<IEntry> retVal = null;
		XMLReader parser = null;
		try {
			parser = XMLReaderFactory.createXMLReader();
			parser.setContentHandler(new EntryHandler());
			parser.parse(filePath);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retVal;
	}
}
