package ICS.SND.Interfaces;

import java.util.List;

import ICS.SND.Entities.Entry;

public interface IEntry {
    public void setId(int id);

    public int getId();

    public String getTitle();

    public void setTitle(String val);

    public String getAuthor();

    public void setAuthor(String val);

    public String getBooktitle();

    public void setBooktitle(String val);

    public String getEntry();

    public void setEntry(String val);

    public String getIsbn();

    public void setIsbn(String val);

    public String getPages();

    public void setPages(String val);

    public String getPublisher();

    public void setPublisher(String val);

    public String getUrl();

    public void setUrl(String val);

    public String getYear();

    public void setYear(String val);

	public String getAbstractText();

	public void setAbstractText(String abstractText);

	public String getIndex();

	public void setIndex(String index);

	public List<Entry> getReferences();

	public void setReferences(List<Entry> references);

}
