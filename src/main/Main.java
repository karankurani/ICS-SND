package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ICS.SND.Entities.DivergencePaper;
import ICS.SND.Entities.LDABase;
import ICS.SND.Tests.UnitTests;
import ICS.SND.Utilities.DivergencePaperComparator;

import com.aliasi.stats.Statistics;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
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
//		BufferedReader br = new BufferedReader(new FileReader(UnitTests.DATA_PATH +"ldaSeedInput.txt"));
		int count=0;
		while((line=br.readLine())!=null){
			lines[count]= line;
			count++;
		}
		int[] docTokens;
		double[] bayesEstimate;
		DivergencePaper dp = new DivergencePaper(lines[0]);
		String[] splitS;
//		for(int i=0;i<lbase.sample.numDocuments();i++){
		for(int i=0;i<2;i++){
				FileWriter fw = new FileWriter("C:/KiyanHadoop/KLOutputFiles/" + i + "Divergence.txt");
				BufferedWriter writer = new BufferedWriter(fw);
				
				for(int k=0;k<count;k++){
					docTokens = lbase.getDocumentTokens(lines[k]);
					splitS = lines[k].split("\\~");
					dp.setTitle(splitS[2]);
					dp.setIndexNumber(splitS[0]);
					
					if(docTokens.length==0){
						dp.setDivergence(10000);
						writer.write(dp.toString()+"\n");
						continue;	
					}
					
					bayesEstimate = lbase.lda.bayesTopicEstimate(docTokens, 2000, lbase.burninEpochs, lbase.sampleLag, new Random());
					for(int j=0;j<bayesEstimate.length;j++){
						if(bayesEstimate[j]==0){
							bayesEstimate[j]=0.0000000001;
						}
					}
					
					line = lines[k];
					dp.setDivergence(Statistics.symmetrizedKlDivergence(seedPaperTopicProb[i], bayesEstimate));
					writer.write(dp.toString()+"\n");
					writer.flush();
				}
				System.out.println("Processed document :" + i);
		}
//		Arrays.sort(dpList);
//		System.out.println("For Seed Paper :" + i);
//		System.out.println("----------------------");
//		for(int k=0;k<count;k++){
//			System.out.println(dpList.get(k).toString());
//			if(dpList[k].getDivergence()!=10000){
//				dpList[k]=null;
//			}
//		}
		
		
//		System.out.println();
	}	
}
