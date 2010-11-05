package ICS.SND.Utilities;

import java.io.*;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import ICS.SND.Interfaces.IEntry;

public class HadoopFlatFormProvider {
	BufferedWriter br;
	String filename;
	
	public HadoopFlatFormProvider (String filename)	{
		this.filename = filename;
	}
	
	public void OpenFile() throws IOException{
		br = new BufferedWriter(new FileWriter(this.filename));
	}
	public void CloseFile() throws IOException{
		br.flush();
		br.close();
	}
	
	public void Save(IEntry currentEntry) {
		
		String referenceIDString =currentEntry.getReferenceIndexNumbers();
		String[] referenceIDs = null;
		StringBuilder referenceStringBuilder = new StringBuilder();
		String referenceString=null;
		if(referenceIDString!=null)
		{
			referenceIDs = referenceIDString.split("\\|");
			IEntry entry=null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			for(String referenceID : referenceIDs)
			{
				Query q = session.createQuery("from Entry where indexNumber='" + referenceID + "'");
				entry = (IEntry) q.uniqueResult();
				if(entry!=null){
					referenceStringBuilder.append(entry.getTitle() + " ");	
				}
			}
			session.close();
		}

		if(referenceStringBuilder.length()!=0){
			referenceString = referenceStringBuilder.toString();
		}
			
		try {
				br.write(currentEntry.getAuthor()+"~"+ currentEntry.getTitle() + "~" + currentEntry.getAbstractText() + "~" + referenceString + "\n");
				br.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public IEntry LoadWithBookTitleFilter(String indexNumber) {
		IEntry entry=null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("from Entry where indexNumber='" + indexNumber + "' and bookTitle is null");
		entry = (IEntry) q.uniqueResult();
		session.close();
		return entry;
	}
}
