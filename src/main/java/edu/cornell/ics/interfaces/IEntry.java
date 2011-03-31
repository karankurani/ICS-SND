package edu.cornell.ics.interfaces;

import java.util.Set;

import edu.cornell.ics.entities.Author;

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

	public String getIndexNumber();

	public void setIndexNumber(String index);

	public String getReferenceIndexNumbers();

	public void setReferenceIndexNumbers(String references);

    public String getType();

    public void setType(String type);
    
    public boolean getIsSeed();
    
    public void setIsSeed(boolean val);
    
    public Set<Author> getAuthors();
    
    public void setAuthors(Set<Author> authors);
}
