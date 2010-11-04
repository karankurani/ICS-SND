package ICS.SND.Utilities;

import java.io.*;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;

public class HadoopFlatFormProvider implements IDataProvider {
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
	
	@Override
	public void Save(IEntry currentEntry) {
		
		String[] referenceIDs = currentEntry.getReferenceIndexNumbers().split("\\|");
		IEntry entry=null;
		StringBuilder referenceString = new StringBuilder();
		Session session = HibernateUtil.getSessionFactory().openSession();
		for(String referenceID : referenceIDs)
		{
			Query q = session.createQuery("from Entry where indexNumber like '%" + referenceID + "%'");
//			q.setParameter("referenceIndexNumbers", referenceID);
			entry = (IEntry) q.uniqueResult();
			referenceString.append(entry.getTitle() + " ");
			System.out.println(entry.getIndexNumber());
		}
		session.close();
		
		try {
			if(currentEntry.getAbstractText()!=null){
				br.write(currentEntry.getAuthor()+"~"+ currentEntry.getTitle() + "~" + currentEntry.getAbstractText() + "~" + referenceString.toString() + "\n");
				br.flush();
			}
			else
			{
				br.write(currentEntry.getAuthor()+"~"+ currentEntry.getTitle() + "~ ~" + referenceString.toString() + "\n");
				br.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void Update(IEntry currentEntry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IEntry LoadByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntry Load(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<IEntry> List() {
		// TODO Auto-generated method stub
		return null;
	}

}
