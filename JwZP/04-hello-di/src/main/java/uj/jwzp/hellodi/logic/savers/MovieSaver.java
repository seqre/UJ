package uj.jwzp.hellodi.logic.savers;

import uj.jwzp.hellodi.model.FileExt;
import uj.jwzp.hellodi.model.Movie;

import java.io.IOException;
import java.util.List;

public interface MovieSaver {
    void save(List<Movie> movies) throws IOException;

    static MovieSaver getInstance(FileExt ext, String filename) {
        switch (ext) {
            case YML:
                return new YmlMovieSaver(filename);

            case JSON:
                return new JsonMovieSaver(filename);

            case XML:
            default:
                return new XmlMovieSaver(filename);
        }
    }
}
