package ICS.SND.Utilities;

import ICS.SND.Interfaces.IEntry;
import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;
import ICS.SND.Utilities.Providers.EntryProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FlatFileCitationReader implements IReader {
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
                    System.out.println(currentEntry.toString());
                    currentEntry = null;
                } else if (isField(line, "*")) {
                    line = getField(line, "*");
                    currentEntry = provider.LoadByIndexNumber(line);
                } else if (currentEntry != null) {
                    setEntryProperties(currentEntry, line);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
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
