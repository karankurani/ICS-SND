package ICS.SND.Entities;

import ICS.SND.Interfaces.IEntry;

public class Entry implements IEntry {
	private String title;

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String val) {
		this.title = val;
	}
}
