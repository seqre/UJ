package uj.jwzp.exam2020.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BookNoId extends Book {

    public BookNoId(int id, String title, int year, int author) {
        super(id, title, year, author);
    }

    @JsonIgnore
    @Override
    public int id() {
        return super.id();
    }
}
