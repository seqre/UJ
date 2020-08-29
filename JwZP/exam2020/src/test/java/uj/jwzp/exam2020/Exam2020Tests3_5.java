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
public class Exam2020Tests3_5 extends AbstractExamTest {

    @Test
    public void checkAuthorsFlow() throws Exception {
        //Expect empty books endpoint
        checkAuthors(of());
        checkAuthor404(1);

        //Try to add book with author #1 and fail
        addBook400(1);
        checkBooks(of());
        checkBook404(1);

        //add author
        addAuthor(1);
        checkAuthors(of(1));
        checkAuthorById(1);

        //Try to add book with author #1 and success
        addBook(1);
        checkBooks(of(1));

        //add 2nd author and check
        addAuthor(2);
        checkAuthors(of(1,2));

        //add 3rd author and check
        addAuthor(3);
        checkAuthors(of(1,2,3));

        //delete 2nd book and check
        deleteAuthor(2);
        checkAuthor404(2);
        checkAuthors(of(1,3));

        //try delete author 1 and fail
        deleteAuthor400(1);
        checkAuthorById(1);

        //delete book 1, then delete author 1
        deleteBook(1);
        checkBook404(1);
        deleteAuthor(1);
        checkAuthor404(1);
        checkAuthors(of(3));
    }

}
