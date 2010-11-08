package ICS.SND.Tests;

import java.io.*;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Utilities.Providers.EntryProvider;
import junit.framework.TestCase;

public class SeedPaperLocator extends TestCase{
 public void findPapers() throws IOException{
	 BufferedReader br = new BufferedReader(new FileReader("filename"));
	 EntryProvider provider = new EntryProvider();
	 String line="";
	 while((line = br.readLine())!=null){
		 IEntry currentEntry = provider.LoadByTitle(line.trim());
		 if(currentEntry !=null){
			 System.out.println(currentEntry.getIndexNumber()+ " Title:" + currentEntry.getTitle());
		 }
		 else{
			 System.out.println("NULL Title:" + line.trim());
		 }
		 currentEntry=null;
	 }
	 provider.LoadByTitle("");
 }
}
