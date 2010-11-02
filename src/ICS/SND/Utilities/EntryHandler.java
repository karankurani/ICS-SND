package ICS.SND.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ICS.SND.Entities.Entry;
import ICS.SND.Interfaces.IEntry;

public class EntryHandler extends DefaultHandler {
	List<IEntry> list;
	boolean title = false;
	boolean url = false;

	public EntryHandler() {
		list = new ArrayList<IEntry>();
	}

	public void startElement(String nsURI, String strippedName, String tagName,	Attributes attributes) throws SAXException {
		if (tagName.equalsIgnoreCase("title"))
			title = true;
	}

	public void characters(char[] ch, int start, int length) {
		if (title) {
			String titleValue = new String(ch, start, length);
			System.out.println(titleValue);
			IEntry e = new Entry();
			e.setTitle(titleValue);
			list.add(e);
			title = false;
		}
	}
}
