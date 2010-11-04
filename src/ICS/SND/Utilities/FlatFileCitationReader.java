package ICS.SND.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;

public class FlatFileCitationReader implements IReader {
	private String filePath;
	private IDataProvider provider;

    public FlatFileCitationReader(String filePath) {
        this.filePath = filePath;
        this.provider = new HibernateDataProvider();
    }
    
	@Override
	public void Process(IProcessor processor) {
		BufferedReader input;
		IEntry currentEntry=null;
		try {
			input = new BufferedReader(new FileReader(this.filePath));
			StringBuilder docBuf = new StringBuilder();
			String line="";
			
			while((line = input.readLine()) != null)
			{
				if(line.contains("#*"))
				{
					 currentEntry = provider.LoadByTitle(line.replaceAll("#*", "").trim());
				}
				else if(line.contains("#%"))
				{
					currentEntry.setReferenceIndexNumbers(currentEntry.getReferenceIndexNumbers() + "|" + line.replaceAll("#%", "").trim());
				}
				else if(line.contains("#!"))
				{
					currentEntry.setAbstractText(line.replaceAll("#!", ""));
				}
				else if(line.length()==0 && currentEntry!=null)
				{
					provider.Update(currentEntry);
				}
			}
		}
		catch(IOException e){
				e.printStackTrace();
		}
	}
}
