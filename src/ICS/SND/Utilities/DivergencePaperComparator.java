package ICS.SND.Utilities;

import java.util.Comparator;

import ICS.SND.Entities.DivergencePaper;

public class DivergencePaperComparator implements Comparator<DivergencePaper>
{
	@Override
	public int compare(DivergencePaper u1, DivergencePaper u2) {
		double rank1 = u1.getDivergence();
		double rank2 = u2.getDivergence();
		
		if(rank1 > rank2)
		{
			return 1;
		}
		else if (rank2 < rank1)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
	

}