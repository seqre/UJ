package uj.jwzp.exam2020;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import uj.jwzp.exam2020.util.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uj.jwzp.exam2020.util.DataMaker.*;

abstract class AbstractExamTest {

    @Autowired
    MockMvc mockMvc;

    private final List<Author> authors;
    private final List<AuthorNoId> authorsNoId;
    private final List<Book> books;
    private final List<BookNoAuth> booksNoAuth;
    private final List<BookNoId> booksNoId;
    private final List<BookNoId> booksNoAuthNoId;

    @SuppressWarnings("unchecked")
    protected AbstractExamTest() {
        authors = read(Author.class, "authors.csv");
        authorsNoId = read(AuthorNoId.class, "authors.csv");
        books = read(Book.class, "books.csv");
        booksNoAuth = read(BookNoAuth.class, "books.csv");
        booksNoId = read(BookNoId.class, "books.csv");
        booksNoAuthNoId = read(BookNoAuthNoId.class, "books.csv");
    }

    private String jsonAuthor(int id) {
        return jsonSingle(authors, id);
    }

    private String jsonAuthors(List<Integer> ids) {
        return jsonMulti(authors, ids);
    }

    private String jsonAuthorNoId(int id) {
        return jsonSingle(authorsNoId, id);
    }

    private String jsonAuthorsNoId(List<Integer> ids) {
        return jsonMulti(authorsNoId, ids);
    }

    private String jsonBook(int id) {
        return jsonSingle(books, id);
    }

    private String jsonBooks(List<Integer> ids) {
        return jsonMulti(books, ids);
    }

    private String jsonBookNoAuth(int id) {
        return jsonSingle(booksNoAuth, id);
    }

    private String jsonBooksNoAuth(List<Integer> ids) {
        return jsonMulti(booksNoAuth, ids);
    }

    private String jsonBookNoId(int id) {
        return jsonSingle(booksNoId, id);
    }

    private String jsonBooksNoId(List<Integer> ids) {
        return jsonMulti(booksNoId, ids);
    }

    private String jsonBookNoAuthNoId(int id) {
        return jsonSingle(booksNoAuthNoId, id);
    }

    private String jsonBooksNoAuthNoId(List<Integer> ids) {
        return jsonMulti(booksNoAuthNoId, ids);
    }

    void checkBooksNoAuth(List<Integer> ids) throws Exception {
        mockMvc
                .perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBooksNoAuth(ids)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void checkBooks(List<Integer> ids) throws Exception {
        mockMvc
                .perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBooks(ids)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void addBookNoAuth(int id) throws Exception {
        mockMvc
                .perform(post("/books").content(jsonBookNoAuthNoId(id)).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonBookNoAuth(id)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void addBook(int id) throws Exception {
        mockMvc
                .perform(post("/books").content(jsonBookNoId(id)).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonBook(id)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void addBook400(int id) throws Exception {
        mockMvc
                .perform(post("/books").content(jsonBookNoId(id)).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    void checkBookNoAuthById(int id) throws Exception {
        mockMvc
                .perform(get("/books/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBookNoAuth(id)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void checkBookById(int id) throws Exception {
        mockMvc
                .perform(get("/books/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBook(id)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void checkAuthors(List<Integer> ids) throws Exception {
        mockMvc
                .perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonAuthors(ids)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void checkAuthorById(int id) throws Exception {
        mockMvc
                .perform(get("/authors/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonAuthor(id)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void addAuthor(int id) throws Exception {
        mockMvc
                .perform(post("/authors").content(jsonAuthorNoId(id)).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonAuthor(id)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void findBooksByTitle400(String title) throws Exception {
        mockMvc
                .perform(get("/books?byTitle=" + title))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    void findBooksByTitle(String title, List<Integer> ids) throws Exception {
        mockMvc
                .perform(get("/books?byTitle=" + title))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBooks(ids)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void findAuthorsByName400(String title) throws Exception {
        mockMvc
                .perform(get("/authors?byName=" + title))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    void findAuthorsByName(String name, List<Integer> ids) throws Exception {
        mockMvc
                .perform(get("/authors?byName=" + name))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonAuthors(ids)))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andDo(print());
    }

    void deleteBook(int id) throws Exception {
        mockMvc
                .perform(delete("/books/" + id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    void deleteAuthor(int id) throws Exception {
        mockMvc
                .perform(delete("/authors/" + id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    void deleteAuthor400(int id) throws Exception {
        mockMvc
                .perform(delete("/authors/" + id))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    void checkBook404(int id) throws Exception {
        mockMvc
                .perform(get("/books/" + id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    void checkAuthor404(int id) throws Exception {
        mockMvc
                .perform(get("/authors/" + id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
