package uj.jwzp.hellodi.logic.savers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uj.jwzp.hellodi.model.Movie;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
@Named("yml")
public class YmlMovieSaver implements MovieSaver {
    @Named("fileName")
    String fileName;

    @Inject
    @Autowired
    public YmlMovieSaver(@Named("fileName") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(List<Movie> movies) throws IOException {
        Writer writer = new FileWriter(fileName);
        writer.append("movies:\n");

        String result = movies.stream()
                .map(this::movieEntry)
                .collect(Collectors.joining());

        writer.append(result);

        writer.close();
    }

    private String movieEntry(Movie movie) {
        StringBuilder result = new StringBuilder();
        result.append("  ").append("-\n")
                .append("    ").append("title: ").append("\"" + movie.getTitle() + "\"\n")
                .append("    ").append("director: ").append(movie.getDirector() + "\n")
                .append("    ").append("year: ").append(movie.getYear() + "\n");

        return result.toString();
    }
}
