package ICS.SND.Tests;

import java.io.*;
import com.aliasi.stats.*;
import java.util.Random;
import org.junit.Test;
import ICS.SND.Entities.LDABase;

public class LdaTest{
	@Test
	public void LDADivergenceTest() throws IOException{
		LDABase lbase = new LDABase(UnitTests.DATA_PATH +"ldaSeedInput.txt");
		BufferedReader br = new BufferedReader(new FileReader(UnitTests.DATA_PATH + "ldaDBLPTestFile.txt"));
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
		int i=0;
		double divergence;
		while((line = br.readLine())!=null){
			docTokens = lbase.getDocumentTokens(line);
			if(docTokens.length==0){
				System.out.println("There is no common word in Document " + i);
				i++;
				continue;	
			}
			bayesEstimate = lbase.lda.bayesTopicEstimate(docTokens, lbase.numSamples, lbase.burninEpochs, lbase.sampleLag, new Random());
			for(int j=0;j<lbase.sample.numDocuments();j++){
				divergence = Statistics.klDivergence(seedPaperTopicProb[j], bayesEstimate);
				System.out.println("For Doc " + i + " with seed Paper" + j + " :" + divergence);
			}
			i++;
		}
	}
}
