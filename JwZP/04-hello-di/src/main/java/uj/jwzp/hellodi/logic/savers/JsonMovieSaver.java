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
@Named("json")
public class JsonMovieSaver implements MovieSaver {
    @Named("fileName")
    String fileName;

    @Inject
    @Autowired
    public JsonMovieSaver(@Named("fileName") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(List<Movie> movies) throws IOException {
        Writer writer = new FileWriter(fileName);
        writer.append("[\n");

        String result = movies.stream()
                .map(this::movieEntry)
                .collect(Collectors.joining(",\n"));

        writer.append(result);
        writer.append("\n]\n");

        writer.close();
    }

    private String movieEntry(Movie movie) {
        StringBuilder result = new StringBuilder();
        result.append("  ").append("{\n")
                .append("    ").append("\"title\": ").append("\"" + movie.getTitle() + "\",\n")
                .append("    ").append("\"director\": ").append("\"" + movie.getDirector() + "\",\n")
                .append("    ").append("\"year\": ").append("\"" + movie.getYear() + "\"\n")
                .append("  ").append("}");

        return result.toString();
    }
}
