package uj.jwzp.exam2020;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.List.of;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Exam2020Tests3 extends AbstractExamTest {

    @Test
	public void contextLoads() { }

    @Test
    public void checkGreetingMessage() throws Exception {
        mockMvc
            .perform(get("/greeting"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello World")))
            .andDo(print());
    }

    @Test
    public void checkBooksFlow() throws Exception {
        //Expect empty books endpoint
        checkBooks(of());
        checkBook404(1);

        //Add 1st book and check
        addBookNoAuth(1);
        checkBooksNoAuth(of(1));
        checkBookNoAuthById(1);
        checkBook404(2);

        //add 2nd book and check
        addBookNoAuth(2);
        checkBookNoAuthById(2);
        checkBooksNoAuth(of(1,2));

        //add 3rd book and check
        addBookNoAuth(3);
        checkBooksNoAuth(of(1,2,3));

        //delete 2nd book and check
        deleteBook(2);
        checkBook404(2);
        checkBooksNoAuth(of(1,3));
        checkBookNoAuthById(1);
    }

}
