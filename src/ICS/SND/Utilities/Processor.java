package ICS.SND.Utilities;

import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Utilities.Providers.EntryProvider;

public class Processor implements IProcessor {
    EntryProvider provider;
    
    public Processor() {
        provider = new EntryProvider();
    }

    @Override
    public void Process(IEntry entry) {
        provider.Save(entry);
    }
}
