package ICS.SND.Interfaces;

import java.util.*;

public interface IDataProvider {

    void Save(IEntry currentEntry);
    void Update(IEntry currentEntry);
    IEntry LoadByTitle(String title);
    IEntry Load(int id);
    List<IEntry> List();
}
