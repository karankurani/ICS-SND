package ICS.SND.Entities;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class EntryHandler extends DefaultHandler {
	boolean title = false;
	boolean url = false;
	
	public EntryHandler() {
		super();
	}

	public void startElement(String nsURI, String strippedName, String tagName,
			Attributes attributes) throws SAXException {
		if (tagName.equalsIgnoreCase("title"))
			title = true;
		if (tagName.equalsIgnoreCase("url"))
			url = true;
	}

	public void characters(char[] ch, int start, int length) {
		if (title) {
			System.out.println("Title: " + new String(ch, start, length));
			title = false;
		} else if (url) {
			System.out.println("Url: " + new String(ch, start, length));
			url = false;
		}
	}
}
