package ICS.SND.Interfaces;

import java.util.*;

public interface IDataProvider {

    void Save(IEntry currentEntry);
    IEntry Load(int id);
    List<IEntry> List();
}
