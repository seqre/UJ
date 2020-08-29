package uj.jwzp.hellodi.launchers;

import uj.jwzp.hellodi.logic.CSVMovieFinder;
import uj.jwzp.hellodi.logic.MovieFinder;
import uj.jwzp.hellodi.logic.MovieLister;
import uj.jwzp.hellodi.logic.savers.JsonMovieSaver;
import uj.jwzp.hellodi.logic.savers.MovieSaver;
import uj.jwzp.hellodi.logic.savers.XmlMovieSaver;
import uj.jwzp.hellodi.logic.savers.YmlMovieSaver;
import uj.jwzp.hellodi.logic.utils.ArgumentsParser;
import uj.jwzp.hellodi.model.FileExt;
import uj.jwzp.hellodi.model.Movie;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ManualMain {

    public static void main(String[] args) throws IOException {
        MovieFinder finder = new CSVMovieFinder();
        MovieLister lister = new MovieLister(finder);
        MovieSaver saver = getMovieSaver(ArgumentsParser.getFileExt(args), ArgumentsParser.getFileName(args));

        List<Movie> movies = lister.moviesDirectedBy("Lucas").stream()
                .peek(m -> System.out.println(m.toString()))
                .collect(Collectors.toList());
        saver.save(movies);
    }

    private static MovieSaver getMovieSaver(FileExt fileExt, String fileName) {
        switch (fileExt) {
            case YML:
                return new YmlMovieSaver(fileName);

            case JSON:
                return new JsonMovieSaver(fileName);

            case XML:
            default:
                return new XmlMovieSaver(fileName);
        }
    }
}
