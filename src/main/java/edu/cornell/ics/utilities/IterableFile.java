package edu.cornell.ics.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class IterableFile implements Iterable<String> {

	private String filename;

	public IterableFile(String filename) {
		this.filename = filename;
	}

	@Override
	public Iterator<String> iterator() {
		return new FileIterator(filename);
	}
}

class FileIterator implements Iterator<String> {

	private BufferedReader br;
	private String line;

	public FileIterator(String filename) {
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasNext() {
		boolean ret = false;
		try {
			ret = !((line = br.readLine()) == null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public String next() {
		return line;
	}

	@Override
	public void remove() {
		;
	}
	
	protected void finalize() throws Throwable {
		try {
//			System.out.println("closing the file.");
			br.close();
		} finally {
			super.finalize();
		}
	}
}
