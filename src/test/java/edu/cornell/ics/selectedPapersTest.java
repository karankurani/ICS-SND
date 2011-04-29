package edu.cornell.ics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import edu.cornell.ics.entities.*;
import edu.cornell.ics.interfaces.*;
import edu.cornell.ics.utilities.providers.HibernateDataProvider;
import org.junit.Ignore;
import org.junit.Test;

public class selectedPapersTest {
    @Test
    @Ignore
	public void getSelectedPaperDetails() throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(UnitTests.DATA_PATH + "/selectedPapers.txt"));
		String line;
		String[] splitS;
		
		FileWriter fw = new FileWriter(UnitTests.DATA_PATH + "/OutputFiles/selectedPapersDetails.txt");
		BufferedWriter writer = new BufferedWriter(fw);
		
		IDataProvider<IEntry> provider = new HibernateDataProvider<IEntry>();
		IEntry currentEntry; 
		while((line=br.readLine())!=null){
			splitS = line.split("\t"); 
			IQuery q = new Query("from Entry where indexNumber='" + splitS[0]+"'");
			currentEntry = provider.Load(q);
			
			System.out.println(currentEntry.getTitle() + ", " 
					+ currentEntry.getAuthor() + " ,"
					+ currentEntry.getPublisher() + "(" + currentEntry.getYear() + ")");
			
			writer.write(currentEntry.getTitle() + ", " 
					+ currentEntry.getAuthor() + " ,"
					+ currentEntry.getPublisher() + "(" + currentEntry.getYear() + ")" 
					+ "\n");
		}
		writer.flush();
	}
}
