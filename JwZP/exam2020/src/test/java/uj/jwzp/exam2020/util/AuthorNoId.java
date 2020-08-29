package uj.jwzp.exam2020.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthorNoId extends Author {

    public AuthorNoId(int id, String name, String birthDate, String deathDate) {
        super(id, name, birthDate, deathDate);
    }

    @JsonIgnore
    @Override
    public int id() {
        return super.id();
    }
}
