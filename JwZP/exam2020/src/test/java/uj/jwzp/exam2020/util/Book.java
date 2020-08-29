package uj.jwzp.exam2020.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book implements WithId {
    private final int id;
    private final String title;
    private final int year;
    private final int author;

    public Book(int id, String title, int year, int author) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.author = author;
    }

    @JsonProperty("id")
    public int id() {
        return id;
    }

    @JsonProperty("title")
    public String title() {
        return title;
    }

    @JsonProperty("year")
    public int year() {
        return year;
    }

    @JsonProperty("author")
    public int author() {
        return author;
    }
}
