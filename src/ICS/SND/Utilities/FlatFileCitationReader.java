package ICS.SND.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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
			String line="";
			List<IEntry> list;
			if(filePath=="C:/SND-LDA/DBLPOnlyCitationOct19.txt"){
				input.readLine();
			}
			while((line = input.readLine()) != null)
			{
				if(line.contains("#*")){
					 line = line.replace("#*", "").trim();
					 list = provider.List(line);
					 if(list!=null)
						 currentEntry = list.get(0);
					 else
						 currentEntry=null;
				}
				if(currentEntry!=null){
					
					if(line.contains("#index")){
						currentEntry.setIndexNumber(line.replace("#index", "").trim());
					}
					else if(line.contains("#%")){
						if(currentEntry.getReferenceIndexNumbers()!=null){
	//						currentEntry.setReferenceIndexNumbers(currentEntry.getReferenceIndexNumbers() + "|" + line.replaceAll("#\\%", "").trim());
							currentEntry.setReferenceIndexNumbers(currentEntry.getReferenceIndexNumbers() + "|" + line.replace("#%", "").trim());
						}
						else{
							currentEntry.setReferenceIndexNumbers(line.replace("#%", "").trim());
						}
					}
					else if(line.contains("#!"))
					{
						currentEntry.setAbstractText(line.replace("#!", "").trim());
					}
					else if(line.length()==0 && currentEntry!=null)
					{
						provider.Update(currentEntry);
						System.out.println(currentEntry.toString());
						currentEntry = null;
					}
				}
			}
		}
		catch(IOException e){
				e.printStackTrace();
		}
	}
}
