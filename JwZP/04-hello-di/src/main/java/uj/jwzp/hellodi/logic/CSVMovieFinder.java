package uj.jwzp.hellodi.logic;

import io.vavr.control.Try;
import org.simpleflatmapper.csv.CsvParser;
import org.springframework.stereotype.Component;
import uj.jwzp.hellodi.model.Movie;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CSVMovieFinder implements MovieFinder {

    private final List<Movie> allMovies;
    private static final String FILE_NAME = "movies.txt";

    public CSVMovieFinder() {
        Reader reader = Try
            .of(() -> (Reader) new FileReader(FILE_NAME))
            .onFailure(ex -> System.err.println("Cannot read file" + FILE_NAME + " " + ex))
            .getOrElse(Reader.nullReader());
        allMovies = Try
            .of(() -> CsvParser.mapTo(Movie.class).stream(reader))
            .onFailure(ex -> System.err.println("Error while processing file " + FILE_NAME + " " + ex))
            .getOrElse(Stream.empty())
            .collect(Collectors.toList());
    }

    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(allMovies);
    }
}
