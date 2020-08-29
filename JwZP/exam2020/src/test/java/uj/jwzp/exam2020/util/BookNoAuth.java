package uj.jwzp.exam2020.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookNoAuth implements WithId {
    private final int id;
    private final String title;
    private final int year;

    public BookNoAuth(int id, String title, int year, int author) {
        this.id = id;
        this.title = title;
        this.year = year;
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

}
