package uj.jwzp.hellodi.launchers;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
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

public class GuiceMain {

    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new GuiceMovieModule(args));
        MovieLister lister = injector.getInstance(MovieLister.class);
        MovieSaver saver = injector.getInstance(MovieSaver.class);

        List<Movie> movies = lister.moviesDirectedBy("Lucas").stream()
                .peek(m -> System.out.println(m.toString()))
                .collect(Collectors.toList());
        saver.save(movies);
    }
}

class GuiceMovieModule extends AbstractModule {
    private final String fileName;
    private final FileExt fileExt;

    public GuiceMovieModule(String[] args) {
        this.fileName = ArgumentsParser.getFileName(args);
        this.fileExt = ArgumentsParser.getFileExt(args);
    }

    @Override
    protected void configure() {
        bind(MovieFinder.class).to(CSVMovieFinder.class);
        switch (fileExt) {
            case YML:
                bind(MovieSaver.class).to(YmlMovieSaver.class);
                break;
            case JSON:
                bind(MovieSaver.class).to(JsonMovieSaver.class);
                break;
            default:
            case XML:
                bind(MovieSaver.class).to(XmlMovieSaver.class);
                break;
        }
        bindConstant().annotatedWith(Names.named("fileName")).to(fileName);
        bindConstant().annotatedWith(Names.named("fileExt")).to(fileExt);
    }
}
