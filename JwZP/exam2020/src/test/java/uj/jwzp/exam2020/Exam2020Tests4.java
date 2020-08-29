package uj.jwzp.exam2020;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.List.of;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Exam2020Tests4 extends AbstractExamTest {

    @Test
    public void findBooks() throws Exception {
        addAuthor(1);
        addAuthor(2);
        addAuthor(3);
        addBook(1);
        addBook(2);
        addBook(3);
        addBook(4);
        addBook(5);
        addBook(6);
        addBook(7);

        findBooksByTitle400("");
        findBooksByTitle400("x");
        findBooksByTitle("Lord", of(1));
        findBooksByTitle("Winnetou", of());
        findBooksByTitle("THE", of(1,3));
    }

}
