package uj.jwzp.hellodi.launchers;

import dagger.Component;
import uj.jwzp.hellodi.logic.savers.MovieSaver;
import uj.jwzp.hellodi.logic.MovieLister;

import javax.inject.Singleton;

@Singleton
@Component(modules = {MovieModule.class,})
public interface DaggerComponent {

    MovieLister getMovieLister();

    MovieSaver getMovieSaver();
}
