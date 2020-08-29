package uj.jwzp.exam2020.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookNoAuthNoId implements WithId {
    private final int id;
    private final String title;
    private final int year;

    public BookNoAuthNoId(int id, String title, int year, int author) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    @JsonIgnore
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
