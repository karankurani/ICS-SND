package ICS.SND.Utilities;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ICS.SND.Interfaces.IEntry;

public class EntryHandler extends DefaultHandler {
	boolean title = false;
	boolean url = false;

	public EntryHandler(List<IEntry> retVal) {
		// TODO Auto-generated constructor stub
	}

	public void startElement(String nsURI, String strippedName, String tagName,	Attributes attributes) throws SAXException {
		if (tagName.equalsIgnoreCase("title"))
			title = true;
	}

	public void characters(char[] ch, int start, int length) {
		if (title) {
			System.out.println("Title: " + new String(ch, start, length));
			title = false;
		}
	}
}
