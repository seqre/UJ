package uj.jwzp.hellodi.launchers;

import dagger.Module;
import dagger.Provides;
import uj.jwzp.hellodi.logic.CSVMovieFinder;
import uj.jwzp.hellodi.logic.MovieFinder;
import uj.jwzp.hellodi.logic.MovieLister;
import uj.jwzp.hellodi.logic.savers.MovieSaver;
import uj.jwzp.hellodi.logic.utils.ArgumentsParser;

import javax.inject.Singleton;

@Module
public class MovieModule {
    private final String[] args;

    public MovieModule(String[] args) {
        this.args = args;
    }

    @Provides
    @Singleton
    public MovieFinder provideMovieFinder() {
        return new CSVMovieFinder();
    }

    @Provides
    @Singleton
    public MovieLister provideMovieLister(MovieFinder movieFinder) {
        return new MovieLister(movieFinder);
    }

    @Provides
    @Singleton
    public MovieSaver provideMovieSaver() {
        return MovieSaver.getInstance(ArgumentsParser.getFileExt(args), ArgumentsParser.getFileName(args));
    }

}
