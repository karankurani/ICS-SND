package ICS.SND.Entities;

public class DivergencePaper{
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
	
	public String toString(){
		return indexNumber + seperator + divergence + seperator + title;
	}
}