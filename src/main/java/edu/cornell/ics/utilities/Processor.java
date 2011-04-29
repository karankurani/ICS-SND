package edu.cornell.ics.utilities;

import edu.cornell.ics.interfaces.IEntry;
import edu.cornell.ics.interfaces.IProcessor;
import edu.cornell.ics.utilities.providers.EntryProvider;
import org.apache.log4j.Logger;

public class Processor implements IProcessor {
    private static final Logger log = Logger.getLogger(Processor.class);
    EntryProvider provider;
    
    public Processor() {
        provider = new EntryProvider();
    }

    @Override
    public void Process(IEntry entry) {
        if(provider.ListByTitle(entry.getTitle()) != null) {
            log.error(String.format("I already had a title [%s]", entry.getTitle()));
        }
        else {
            log.debug(String.format("saving [%s]", entry.getTitle()));
            provider.Save(entry);
        }
    }
}
