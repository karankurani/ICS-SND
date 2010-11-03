package ICS.SND.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ICS.SND.Interfaces.IProcessor;
import ICS.SND.Interfaces.IReader;

public class FlatFileCitationReader implements IReader {
	private String filePath;

    public FlatFileCitationReader(String filePath) {
        this.filePath = filePath;
    }
    
	@Override
	public void Process(IProcessor processor) {
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(this.filePath));
			StringBuilder docBuf = new StringBuilder();
			String line="";
			
			while((line = input.readLine()) != null)
			{
				if(line.length()==0 && docBuf.length()>0)
				{
					docBuf = new StringBuilder();
	            }
				else
				{
					docBuf.append(line);
				}
			}
		}
		catch(IOException e){
				e.printStackTrace();
		}
	}
}
