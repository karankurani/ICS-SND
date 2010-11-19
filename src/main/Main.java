package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import ICS.SND.Entities.Author;
import ICS.SND.Entities.DivergencePaper;
import ICS.SND.Entities.Entry;
import ICS.SND.Entities.LDABase;
import ICS.SND.Entities.Query;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IQuery;
import ICS.SND.Tests.AuthorTest;
import ICS.SND.Tests.UnitTests;
import ICS.SND.Utilities.DivergencePaperComparator;
import ICS.SND.Utilities.Providers.EntryProvider;
import ICS.SND.Utilities.Providers.HibernateDataProvider;

import com.aliasi.stats.Statistics;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    listByAuthors();
//	    getUnsortedList();
//	    sortList();
	}	
	private static void listByAuthors() {
	    FileWriter fw;
        try {
            fw = new FileWriter("AuthorOutput.txt");
            BufferedWriter writer = new BufferedWriter(fw);
            
            HibernateDataProvider<Author> authorProvider 
                = new HibernateDataProvider<Author>();
            EntryProvider entryProvider = new EntryProvider();
            
            IQuery qry = new Query("from Author");
            List<Author> authors = authorProvider.List(qry);
            
            for(Author author : authors) {
                log.debug(MessageFormat.format("author: {0}", author.getAuthorName()));
                List<IEntry> entries = entryProvider.ListByAuthor(author);
                writer.write(MessageFormat.format("{0}<break>", 
                        author.getAuthorId()));
                for(IEntry entry : entries) {
                    log.debug(MessageFormat.format("\t* paper: {0}", 
                            entry.getTitle()));
                    writer.write(MessageFormat.format("{0},", 
                            entry.getIndexNumber()));
                }
                writer.write("\n");
                writer.flush();
            }
            writer.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void sortList() throws IOException{
        // TODO Auto-generated method stub
	    BufferedReader br = new BufferedReader(new FileReader("C:/KiyanHadoop/KLOutputFiles/DivergenceRun5.txt"));
	    FileWriter fw = new FileWriter("C:/KiyanHadoop/KLOutputFiles/SortedDivergenceRun5.txt");
        BufferedWriter writer = new BufferedWriter(fw);
        String line;
        String[] lines = new String[700000];
        int count =0;
        while((line=br.readLine())!=null){
            lines[count]= line;
            count++;
        }
        String[] splitS;
        
        DivergencePaper dp = new DivergencePaper("index~abc~title~blah");
        int dpListLength = 10000;
        DivergencePaper[] dpList = new DivergencePaper[dpListLength];
        for(int k=0;k<dpListLength;k++){
            dpList[k] = new DivergencePaper("index~abc~title~blah");
            dpList[k].setDivergence(10000);
        }
        int dpCount = 0;
        for(int k=0;k<count;k++){
            splitS = lines[k].split("\\t");
            if(Double.parseDouble(splitS[1])<1){
                dp = new DivergencePaper("index~abc~title~blah");
                dp.setDivergence(Double.parseDouble(splitS[1]));
                dp.setTitle(splitS[2]);
                dp.setIndexNumber(splitS[0]);
                dpList[dpCount]=dp;
                dpCount++;
                if(dpCount == dpListLength){
                    break;
                }
            }
        }
        Arrays.sort(dpList);
        
        for(int k=0;k<dpCount;k++){
            writer.write(dpList[k].toString() + "\n");
        }
        writer.flush();
    }
    private static void getUnsortedList() throws IOException{
	    
	    LDABase lbase = new LDABase(UnitTests.DATA_PATH +"ldaSeedInput.txt");

        PrintStream ps = new PrintStream(new File("C:/KiyanHadoop/ldaKLOutput.txt"));
        System.setOut(ps);

        lbase.startEpochs();
        String line = "";
        double[][] seedPaperTopicProb = new double[lbase.sample.numDocuments()][lbase.sample.numTopics()];
        
        
        for(int i=0; i<lbase.sample.numDocuments(); i++){
            for(int j=0;j< lbase.sample.numTopics();j++){
                seedPaperTopicProb[i][j] = lbase.sample.documentTopicProb(i, j);
            }
        }

        String[] lines = new String[700000];
        BufferedReader br = new BufferedReader(new FileReader("C:/KiyanHadoop/KLDivergenceTest.txt"));
//      BufferedReader br = new BufferedReader(new FileReader(UnitTests.DATA_PATH +"ldaSeedInput.txt"));
        int count=0;
        while((line=br.readLine())!=null){
            lines[count]= line;
            count++;
        }
        int[] docTokens;
        double[] bayesEstimate;
        DivergencePaper dp = new DivergencePaper(lines[0]);
        double currDivergence = 10000;
        String[] splitS;
        
        FileWriter fw = new FileWriter("C:/KiyanHadoop/KLOutputFiles/DivergenceRun5.txt");
        BufferedWriter writer = new BufferedWriter(fw);    
        for(int k=0;k<count;k++){
            dp.setDivergence(10000);
            for(int i=0;i<lbase.sample.numDocuments();i++){
                docTokens = lbase.getDocumentTokens(lines[k]);
                splitS = lines[k].split("\\~");
                dp.setTitle(splitS[2]);
                dp.setIndexNumber(splitS[0]);
                
                if(docTokens.length==0){   
                    continue;
                }
                
                bayesEstimate = lbase.lda.bayesTopicEstimate(docTokens, 100, lbase.burninEpochs, lbase.sampleLag, new Random());
                for(int j=0;j<bayesEstimate.length;j++){
                    if(bayesEstimate[j]==0){
                        bayesEstimate[j]=0.0000000001;
                    }
                }
                
                line = lines[k];
                currDivergence = Statistics.symmetrizedKlDivergence(seedPaperTopicProb[i], bayesEstimate);
                if(currDivergence < dp.getDivergence()){
                    dp.setDivergence(currDivergence);
                }
            }
//          System.out.println(dp.toString());
            writer.write(dp.toString()+"\n");
        }
        writer.flush();
	}
}
