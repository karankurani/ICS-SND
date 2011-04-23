import java.io.*;
import java.util.*;

import org.yaml.snakeyaml.Yaml;

import com.aliasi.stats.Statistics;


public class ExecuteLDA {

	/**
	 * @param args	 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		//args[0] = seed paper file. args[1] = set of paper P which we want to estimate the divergence. args[2] = outputfile.
		//LDA(System.getProperty("user.dir") + args[0],System.getProperty("user.dir") + args[1] , System.getProperty("user.dir") + args[2]);
		LDA(System.getProperty("user.dir") + "/data/input/input-for-lda.yml",System.getProperty("user.dir") + "/data/input/input-for-lda.yml", System.getProperty("user.dir") + "/data/output/output.yml");		
	}
	private static void LDA(String seedInputFile,String papersToScoreFile, String outputFile) throws IOException{
	    LDABase lbase = new LDABase(seedInputFile);
	    
        PrintStream ps = new PrintStream(new File(System.getProperty("user.dir")+"/data/output/consoleOutput.txt"));
        System.setOut(ps);

        lbase.startEpochs();
        double[][] seedPaperTopicProb = new double[lbase.sample.numDocuments()][lbase.sample.numTopics()];
        
        
        for(int i=0; i<lbase.sample.numDocuments(); i++){
            for(int j=0;j< lbase.sample.numTopics();j++){
                seedPaperTopicProb[i][j] = lbase.sample.documentTopicProb(i, j);
            }
        }

        Yaml yaml = new Yaml();
        InputStream io = new FileInputStream(papersToScoreFile);
        Iterable<Object> corpus = yaml.loadAll(io);
        List<LinkedHashMap<String,String>> entryList = (List<LinkedHashMap<String,String>>) corpus.iterator().next();
             
        int[] docTokens;
        double[] bayesEstimate;
        DivergencePaper dp = null;
        double currDivergence = 10000;
                
        FileWriter fw = new FileWriter(outputFile);
        BufferedWriter writer = new BufferedWriter(fw);    
        
        for(LinkedHashMap<String,String> entry : entryList){        	
        	dp = new DivergencePaper(entry);            
            dp.setDivergence(10000);
            for(int i=0;i<lbase.sample.numDocuments();i++){
                docTokens = lbase.getDocumentTokens(entry.get(":abstractText") + " " + entry.get(":title") + " " + entry.get(":booktitle"));                
                
                if(docTokens.length==0){   
                    continue;
                }
                
                bayesEstimate = lbase.lda.bayesTopicEstimate(docTokens, 100, lbase.burninEpochs, lbase.sampleLag, new Random());
                for(int j=0;j<bayesEstimate.length;j++){
                    if(bayesEstimate[j]==0){
                        bayesEstimate[j]=0.0000000001;
                    }
                }
                
                currDivergence = Statistics.symmetrizedKlDivergence(seedPaperTopicProb[i], bayesEstimate);
                if(currDivergence < dp.getDivergence()){
                    dp.setDivergence(currDivergence);
                }
            }
//          System.out.println(dp.toString());
            writer.write(yaml.dump(dp));
        }
        writer.flush();		
	}	    	
}