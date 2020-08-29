package uj.jwzp.hellodi.logic.savers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uj.jwzp.hellodi.model.Movie;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
@Primary
@Scope("prototype")
@Named("xml")
public class XmlMovieSaver implements MovieSaver {
    @Named("fileName")
    String fileName;

    @Inject
    @Autowired
    public XmlMovieSaver(@Named("fileName") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(List<Movie> movies) throws IOException {
        Writer writer = new FileWriter(fileName);
        writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n").append("<movies>\n");
        for (Movie movie : movies) {
            writer.append(movieEntry(movie));
        }
        writer.append("</movies>\n");
        writer.close();
    }

    private String movieEntry(Movie movie) {
        StringBuilder result = new StringBuilder("  <movie>\n");
        result
            .append("    <title>" + movie.getTitle() + "</title>\n")
            .append("    <director>" + movie.getDirector() + "</director>\n")
            .append("    <year>" + movie.getYear() + "</year>\n")
            .append("  </movie>\n");
        return result.toString();
    }
}
