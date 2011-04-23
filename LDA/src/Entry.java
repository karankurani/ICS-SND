public class Entry {

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

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(String index) {
		this.indexNumber = index;
	}

	public String getReferenceIndexNumbers() {
		return referenceIndexNumbers;
	}

	public void setReferenceIndexNumbers(String references) {
		this.referenceIndexNumbers = references;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBooktitle() {
		return booktitle;
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String val) {
		this.title = val;
	}

	public String toString() {
		return this.indexNumber + " [Title]" + this.title + " [Authors]"
				+ this.author + " [Abstract]" + this.abstractText
				+ " [References]" + this.referenceIndexNumbers;
	}

	public boolean getIsSeed() {
		return isSeed;
	}

	public void setIsSeed(boolean val) {
		this.isSeed = val;
	}
}