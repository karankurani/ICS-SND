package edu.cornell.ics.interfaces;

import edu.cornell.ics.entities.Entry;


public interface IDivergenceEntry {

    public abstract Entry getSeedPaper();

    public abstract void setSeedPaper(Entry seedPaper);

    public abstract Entry getOtherPaper();

    public abstract void setOtherPaper(Entry otherPaper);

    public abstract double getDivergenceValue();

    public abstract void setDivergenceValue(double divergenceValue);

    public abstract void setId(int id);

    public abstract int getId();

}