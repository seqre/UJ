package com.example.WepAppBookApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<Book> getBooks(
            @RequestParam(required = false, defaultValue = "false") boolean all
    ) {
        return booksService.getBooks(all);
    }

    @GetMapping("/add")
    public ResponseEntity<List<Book>> addBook(
            @RequestParam(required = true) String isbn,
            @RequestParam(required = true) String title,
            @RequestParam(required = true) String author,
            @RequestParam(required = true) String genre
    ) {
        Book b = new Book(isbn, title, author, Genre.valueOf(genre));
        if (this.booksService.addBook(b)) {
            return ResponseEntity.ok(booksService.getBooks(false));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") String isbn) {
        Book result = booksService.getBook(isbn);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
