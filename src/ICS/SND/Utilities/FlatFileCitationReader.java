package ICS.SND.Utilities;

import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;
import ICS.SND.Tests.DBLPEntryReaderTest;
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
                    provider.Update(currentEntry);
                    log.debug(currentEntry.toString());
                    currentEntry = null;
                } else if (isField(line, "*")) {
                    line = getField(line, "*");
                    currentEntry = provider.LoadByTitle(line);
                } else if (currentEntry != null) {
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
            entry.setIndexNumber(getField(line, "index"));
        } else if (isField(line, "%")) {
            String referenceIndexNumbers = entry.getReferenceIndexNumbers();
            if (referenceIndexNumbers != null) {
                entry.setReferenceIndexNumbers(referenceIndexNumbers + "|" + getField(line, "%"));
            } else {
                entry.setReferenceIndexNumbers(getField(line, "%"));
            }
        } else if (isField(line, "!")) {
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
