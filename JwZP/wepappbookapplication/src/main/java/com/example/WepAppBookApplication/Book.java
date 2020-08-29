package com.example.WepAppBookApplication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final Genre genre;

    public Book(String isbn, String title, String author, Genre genre) {
        if (isbn == null) throw new NullPointerException("ISBN cannot be null");
        if (title == null) throw new NullPointerException("Title cannot be null");
        if (author == null) throw new NullPointerException("Author cannot be null");
        if (genre == null) throw new NullPointerException("Genre cannot be null");
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @JsonProperty("isbn")
    public String isbn() {
        return isbn;
    }

    @JsonProperty("title")
    public String title() {
        return title;
    }

    @JsonProperty("author")
    public String author() {
        return author;
    }

    @JsonProperty("genre")
    public Genre genre() {
        return genre;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Book && isbn.equals(((Book) o).isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
