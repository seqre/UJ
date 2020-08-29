package uj.jwzp.exam2020.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author implements WithId {
    private final int id;
    private final String name;
    private final String birthDate;
    private final String deathDate;

    public Author(int id, String name, String birthDate, String deathDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    @JsonProperty("id")
    public int id() {
        return id;
    }

    @JsonProperty("name")
    public String name() {
        return name;
    }

    @JsonProperty("birthDate")
    public String birthDate() {
        return birthDate;
    }

    @JsonProperty("deathDate")
    public String deathDate() {
        return deathDate;
    }
}
