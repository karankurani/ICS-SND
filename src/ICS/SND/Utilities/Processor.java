package ICS.SND.Utilities;

import ICS.SND.Interfaces.IDataProvider;
import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IProcessor;

public class Processor implements IProcessor {
    IDataProvider provider;
    
    public Processor() {
        provider = new HibernateDataProvider();
    }

    @Override
    public void Process(IEntry entry) {
        provider.Save(entry);
    }
}
