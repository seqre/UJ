package uj.jwzp.exam2020.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp.exam2020.models.Author;
import uj.jwzp.exam2020.models.Book;
import uj.jwzp.exam2020.services.AuthorService;
import uj.jwzp.exam2020.services.BookService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "", produces = "application/json")
    public List<Book> getBooks(@RequestParam Optional<String> byTitle) {
        log.info("Got request for books");

        if (byTitle.isPresent()) {
            String title = byTitle.get();
            if (title.length() < 2) {
                log.warn("Too short query");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Too short query");
            } else {
                bookService.getBooksByTitle(title);
            }
        }

        return bookService.getBooks();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = "application/json")
    public Book getBook(@PathVariable("id") Long id) {
        log.info("Got request for book with id: " + id);

        Optional<Book> book = bookService.getBook(id);

        return book.orElseThrow(() -> {
            log.warn("Unknown book with id " + id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown book with id " + id);
        });
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", produces = "application/json", consumes = "application/json")
    public Book createBook(@RequestBody Book book) {
        log.info("Got request for creating book");

        if (book.getAuthor() != null) {
            Optional<Author> author = authorService.getAuthor(book.getAuthor());

            if (author.isEmpty()) {
                log.warn("Author with id: " + book.getAuthor() + " does not exist ");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author with id: " + book.getAuthor() + " does not exist ");
            }
        }

        return bookService.save(book);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        log.info("Got request for deleting book with id: " + id);

        bookService.deleteBook(id);
    }
}
