package uj.jwzp.hellodi.model;

public class Movie {
    private final String title;
    private final int year;
    private final String director;

    public Movie(String title, int year, String director) {
        this.title = title;
        this.year = year;
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", director='" + director + '\'' +
                '}';
    }
}
