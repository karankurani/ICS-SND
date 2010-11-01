package ICS.SND.Entities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jdom.*;
import org.jdom.input.SAXBuilder;

public class DBLPEntryReader implements IReader {

	public DBLPEntryReader(String filePath){
			try {
				SAXBuilder builder = new SAXBuilder();
			    Document document = builder.build(new FileReader(filePath));
			    Element root = document.getRootElement();
			    root = root;
			} 
			catch (FileNotFoundException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JDOMException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public java.util.List<IEntry> List() {
		// TODO Auto-generated method stub
		return null;
	}

}
