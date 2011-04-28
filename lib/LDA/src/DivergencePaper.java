import java.util.LinkedHashMap;


public class DivergencePaper implements Comparable<DivergencePaper>{
	String title;
	String id;
	double divergence;
	String separator = "\t";
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		this.id = Integer.toString(indexNumber);
	}
	
	public DivergencePaper(String line) {
		String[] splitS = line.split("\\~"); 
		this.title = splitS[2].trim();
		this.id = splitS[0];
	}
	public DivergencePaper(LinkedHashMap<String, String> entry) {
		this.id = String.valueOf(entry.get(":id"));
		this.title = entry.get(":title");
	}
	public String toString(){
		return id + separator + divergence + separator + title;
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