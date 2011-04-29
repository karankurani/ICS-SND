package edu.cornell.ics.entities;

import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import edu.cornell.ics.interfaces.IEntry;

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
    private String type;
    private boolean isSeed;
    private Set<Author> authors = new HashSet<Author>(0);

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @Override
    public Set<Author> getAuthors() {
        return authors;
    }

    @Override
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

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
    @Column(length = 1024)
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
    @Column(length = 1024)
    public String getReferenceIndexNumbers() {
        return referenceIndexNumbers;
    }

    @Override
    public void setReferenceIndexNumbers(String references) {
        this.referenceIndexNumbers = references;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
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
        return this.indexNumber + " [Title]" + this.title + " [Authors]" + this.author + " [Abstract]" + this.abstractText + " [References]" + this.referenceIndexNumbers;
    }

    @Override
    public boolean getIsSeed() {
        return isSeed;
    }

    @Override
    public void setIsSeed(boolean val) {
        this.isSeed = val;
    }

    public void addAuthor(Author author) {
        if (!authors.contains(author)) {
            authors.add(author);
        }
    }
}
