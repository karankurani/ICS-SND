package ICS.SND.Utilities;

import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;
import ICS.SND.Utilities.Providers.EntryProvider;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FlatFileCitationReader implements IReader {
    private static final Logger log = Logger.getLogger(FlatFileCitationReader.class);
    private String filePath;
    private EntryProvider provider;

    public FlatFileCitationReader(String filePath) {
        this.filePath = filePath;
        this.provider = new EntryProvider();
    }

    @Override
    public void Process(IProcessor processor) {
        BufferedReader input;
        IEntry currentEntry = null;
        try {
            input = new BufferedReader(new FileReader(this.filePath));
            String line;
            while ((line = input.readLine()) != null) {
                if (line.length() == 0 && currentEntry != null) {
                    log.debug("updating " + currentEntry.getTitle());
                    provider.Update(currentEntry);
                    currentEntry = null;
                } else if (isField(line, "*")) {
                    line = getField(line, "*");
                    log.debug("loading " + line);
                    currentEntry = provider.LoadByTitle(line);
                    if(currentEntry == null) {
                        log.error(String.format("I could not find entry with title [%s]", line));
                    }
                } else if (currentEntry != null) {
                    log.debug("setting some properties");
                    setEntryProperties(currentEntry, line);
                }
            }
        }
        catch (IOException e) {
            log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
    }

    private void setEntryProperties(IEntry entry, String line) {
        if (isField(line, "index")) {
            log.debug(" * found the index");
            entry.setIndexNumber(getField(line, "index"));
        } else if (isField(line, "%")) {
            log.debug(" * found the references");
            String referenceIndexNumbers = entry.getReferenceIndexNumbers();
            if (referenceIndexNumbers != null) {
                entry.setReferenceIndexNumbers(referenceIndexNumbers + "|" + getField(line, "%"));
            } else {
                entry.setReferenceIndexNumbers(getField(line, "%"));
            }
        } else if (isField(line, "!")) {
            log.debug(" * found the abstract");
            entry.setAbstractText(getField(line, "!"));
        }
    }

    private boolean isField(String line, String f) {
        return line.startsWith("#" + f);
    }

    private String getField(String line, String f) {
        return line.replace("#" + f, "").trim();
    }
}
