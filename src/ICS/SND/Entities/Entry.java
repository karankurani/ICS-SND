package ICS.SND.Entities;

import javax.persistence.*;
import ICS.SND.Interfaces.IEntry;

@Entity
public class Entry implements IEntry {
	private int id;
	private String author;
	private String booktitle;
	private String entry;
	private String isbn;
	private String pages;
	private String publisher;
	private String title;
	private String url;
	private String year;
	private String abstractText;
	private String indexNumber;
	private String referenceIndexNumbers;

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getAbstractText() {
		return abstractText;
	}

	@Override
	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	@Override
	public String getIndexNumber() {
		return indexNumber;
	}

	@Override
	public void setIndexNumber(String index) {
		this.indexNumber = index;
	}

	@Override
	public String getReferenceIndexNumbers() {
		return referenceIndexNumbers;
	}

	@Override
	public void setReferenceIndexNumbers(String references) {
		this.referenceIndexNumbers = references;
	}

	@Override
	@Column(length = 1024)
	public String getAuthor() {
		return author;
	}

	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String getBooktitle() {
		return booktitle;
	}

	@Override
	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	@Override
	public String getEntry() {
		return entry;
	}

	@Override
	public void setEntry(String entry) {
		this.entry = entry;
	}

	@Override
	public String getIsbn() {
		return isbn;
	}

	@Override
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public String getPages() {
		return pages;
	}

	@Override
	public void setPages(String pages) {
		this.pages = pages;
	}

	@Override
	public String getPublisher() {
		return publisher;
	}

	@Override
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getYear() {
		return year;
	}

	@Override
	public void setYear(String year) {
		this.year = year;
	}

	@Override
	@Column(length = 1024)
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String val) {
		this.title = val;
	}

	@Override
	public String toString() {
		String s="";
		s = this.title + " " + this.author;
		if(this.abstractText!= null)	{
			s = s + this.abstractText;
		}
		return s;
	}
}
