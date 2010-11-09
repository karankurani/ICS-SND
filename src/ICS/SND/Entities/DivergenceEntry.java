package ICS.SND.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import ICS.SND.Interfaces.IDivergenceEntry;

@Entity
public class DivergenceEntry implements IDivergenceEntry {
    private int id;
    private Entry seedPaper;
    private Entry otherPaper;
    private double divergenceValue;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    @OneToOne
    public Entry getSeedPaper() {
        return seedPaper;
    }

    @Override
    public void setSeedPaper(Entry seedPaper) {
        this.seedPaper = seedPaper;
    }

    @Override
    @OneToOne
    public Entry getOtherPaper() {
        return otherPaper;
    }

    @Override
    public void setOtherPaper(Entry otherPaper) {
        this.otherPaper = otherPaper;
    }

    @Override
    public double getDivergenceValue() {
        return divergenceValue;
    }

    @Override
    public void setDivergenceValue(double divergenceValue) {
        this.divergenceValue = divergenceValue;
    }
}
