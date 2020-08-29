package uj.jwzp.exam2020.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp.exam2020.models.Author;
import uj.jwzp.exam2020.services.AuthorService;
import uj.jwzp.exam2020.services.BookService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "", produces = "application/json")
    public List<Author> getAuthors() {
        log.info("Got request for authors");

        return authorService.getAuthors();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = "application/json")
    public Author getAuthor(@PathVariable("id") Long id) {
        log.info("Got request for author with id: " + id);

        Optional<Author> author = authorService.getAuthor(id);

        return author.orElseThrow(() -> {
            log.warn("Unknown author with id " + id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown author with id " + id);
        });
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", produces = "application/json", consumes = "application/json")
    public Author createAuthor(@RequestBody Author author) {
        log.info("Got request for creating author");

        return authorService.save(author);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public void deleteAuthor(@PathVariable("id") Long id) {
        log.info("Got request for deleting author with id: " + id);

        if (!bookService.booksOfAuthor(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author with id " + id + " has books");
        }

        authorService.deleteAuthor(id);
    }
}
