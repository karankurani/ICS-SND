package edu.cornell.ics.entities;

public class DivergencePaper implements Comparable<DivergencePaper>{
	String title;
	String indexNumber;
	double divergence;
	String seperator = "\t";
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIndexNumber() {
		return indexNumber;
	}
	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
	}
	public double getDivergence() {
		return divergence;
	}
	public void setDivergence(double divergence) {
		this.divergence = divergence;
	} 
	public DivergencePaper(String line, int indexNumber){
		String[] splitS = line.split("\\~"); 
		this.title = splitS[1].trim();
		this.indexNumber = Integer.toString(indexNumber);
	}
	
	public DivergencePaper(String line) {
		String[] splitS = line.split("\\~"); 
		this.title = splitS[2].trim();
		this.indexNumber = splitS[0];
	}
	public String toString(){
		return indexNumber + seperator + divergence + seperator + title;
	}
    @Override
    public int compareTo(DivergencePaper o) {
        if(this.getDivergence() > o.getDivergence()){
            return 1;
        }
        else if(this.getDivergence() < o.getDivergence()){
            return -1;
        }
        return 0;
    }
}