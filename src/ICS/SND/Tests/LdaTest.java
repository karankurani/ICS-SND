package ICS.SND.Tests;

import java.io.*;

import com.aliasi.stats.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import ICS.SND.Entities.LDABase;
import ICS.SND.Entities.DivergencePaper;
import ICS.SND.Utilities.DivergencePaperComparator;

public class LdaTest{
	@SuppressWarnings("unchecked")
	@Test
	public void LDADivergenceTest() throws IOException{
		LDABase lbase = new LDABase(UnitTests.DATA_PATH +"ldaSeedInput.txt");
		BufferedReader br = new BufferedReader(new FileReader(UnitTests.DATA_PATH + "ldaSeedInput.txt"));

		PrintStream ps = new PrintStream(new File(UnitTests.DATA_PATH + "ldaKLOutput.txt"));
		System.setOut(ps);

		lbase.startEpochs();
		
		String line = "";
		int[] docTokens;
		double[] bayesEstimate;
		double[][] seedPaperTopicProb = new double[lbase.sample.numDocuments()][lbase.sample.numTopics()];
		
		for(int i=0; i<lbase.sample.numDocuments(); i++){
			for(int j=0;j< lbase.sample.numTopics();j++){
				seedPaperTopicProb[i][j] = lbase.sample.documentTopicProb(i, j);
			}
		}

		DivergencePaper dp;
		List<DivergencePaper>[] dpLists = (ArrayList<DivergencePaper>[])new ArrayList[lbase.sample.numDocuments()];
		for(int j=0;j<lbase.sample.numDocuments();j++){
			dpLists[j] = new ArrayList<DivergencePaper>();	
		}
		
		int i=0;
		
		while((line = br.readLine())!=null){
	
			docTokens = lbase.getDocumentTokens(line);
			if(docTokens.length==0){
				for(int j=0;j<lbase.sample.numDocuments();j++){
					dp = new DivergencePaper(line,i);
					dp.setDivergence(10000);
					dpLists[j].add(dp);
				}
				i++;
				continue;	
			}
			
			
			bayesEstimate = lbase.lda.bayesTopicEstimate(docTokens, lbase.numSamples, lbase.burninEpochs, lbase.sampleLag, new Random());
			for(int j=0;j<bayesEstimate.length;j++){
				if(bayesEstimate[j]==0){
					bayesEstimate[j]=0.0000000001;
				}
			}
			for(int j=0;j<lbase.sample.numDocuments();j++){
				dp = new DivergencePaper(line,i);
				dp.setDivergence(Statistics.symmetrizedKlDivergence(seedPaperTopicProb[j], bayesEstimate));
				dpLists[j].add(dp);
			}
			i++;
		}
		
		DivergencePaperComparator comparator = new DivergencePaperComparator();
		for(int j=0;j<lbase.sample.numDocuments();j++){
			Collections.sort(dpLists[j],comparator);
		}
		FileWriter fw = new FileWriter(UnitTests.DATA_PATH + "/OutputFiles/" + 0 + "Divergence.txt");
		BufferedWriter writer = new BufferedWriter(fw);
		for(int j=0;j<lbase.sample.numDocuments();j++){
			fw = new FileWriter(UnitTests.DATA_PATH + "/OutputFiles/" + j + "Divergence.txt");
			writer = new BufferedWriter(fw);
			System.out.println("For Seed Paper :" + j);
			System.out.println("----------------------");
			for(int k=0;k<dpLists[j].size();k++){
				System.out.println(dpLists[j].get(k).toString());
				writer.write(dpLists[j].get(k).toString()+"\n");
			}
			writer.flush();
			System.out.println();
		}
	}
}
