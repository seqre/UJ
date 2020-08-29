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
public class Exam2020Tests4_5 extends AbstractExamTest {

    //Pierwszy test - uruchamiać osobno

    @Test
    public void checkBeforePersistence() throws Exception {
        //Check if initially is empty
        checkBooks(of());
        checkAuthors(of());

        addAuthor(1);
        addBook(1);
        addBook(2);
        checkAuthorById(1);
        checkBookById(1);
        checkBookById(2);

        addAuthor(2);
        addBook(3);
        addBook(4);
        checkAuthorById(2);
        checkBookById(3);
        checkBookById(4);

    }

    //Drugi test do zaliczenia na 4
    @Test
    public void checkAfterPersistence() throws Exception {
        //Check persistence
        checkAuthors(of(1,2));
        checkBooks(of(1,2,3,4));
    }

}
