package uj.jwzp.hellodi.launchers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uj.jwzp.hellodi.logic.MovieLister;
import uj.jwzp.hellodi.logic.savers.MovieSaver;
import uj.jwzp.hellodi.logic.utils.ArgumentsParser;
import uj.jwzp.hellodi.model.FileExt;
import uj.jwzp.hellodi.model.Movie;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SpringMain {
    private static String fileName = "saved.xml";
    private static FileExt fileExt = FileExt.XML;

    public static void main(String[] args) throws IOException {
        fileName = ArgumentsParser.getFileName(args);
        fileExt = ArgumentsParser.getFileExt(args);

        final ApplicationContext ctx = new AnnotationConfigApplicationContext(
                "uj.jwzp.hellodi.logic", "uj.jwzp.hellodi.logic.savers");
        MovieSaver saver = (MovieSaver) ctx.getBean(fileExt.getExt(), fileName);
        MovieLister lister = ctx.getBean(MovieLister.class);

        List<Movie> movies = lister.moviesDirectedBy("Lucas").stream()
                .peek(m -> System.out.println(m.toString()))
                .collect(Collectors.toList());
        saver.save(movies);
    }
}
