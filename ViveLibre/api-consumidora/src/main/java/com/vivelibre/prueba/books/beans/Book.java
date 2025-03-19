package com.vivelibre.prueba.books.beans;

public class Book {

	private long id;
    private String title;
    private String summary;
    private int pages;
    private String publicationTimestamp;  // Lo vamos a almacenar como String para manejar el timestamp.
    private Author author;
    private int wordCount;
    
	public Book(long id, String title, String summary, int pages, String publicationTimestamp, Author author) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.pages = pages;
		this.publicationTimestamp = publicationTimestamp;
		this.author = author;
	}

	public Book(long id, String title, Author author, int pages) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.pages = pages;
	}
	
	public Book() {
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getPublicationTimestamp() {
		return publicationTimestamp;
	}

	public void setPublicationTimestamp(String publicationTimestamp) {
		this.publicationTimestamp = publicationTimestamp;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
}
