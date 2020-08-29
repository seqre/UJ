package com.example.WepAppBookApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BooksService {
    private final Set<Book> books;
    private final RestTemplate restTemplate;
    private final String resourceUrl;

    @Autowired
    public BooksService(RestTemplate restTemplate, @Value("${my.client}") String resourceUrl) {
        this.books = new HashSet<>();
        books.add(new Book("7321", "Przeżycia Jana Pawła II", "Wydawnictwo Watykańskie", Genre.NON_FICTION));
        this.restTemplate = restTemplate;
        this.resourceUrl = resourceUrl;
    }

    public List<Book> getBooks(boolean all) {
        List<Book> upstreamBooks = all ? getUpstreamBooks() : Collections.emptyList();
        ArrayList<Book> result = new ArrayList<>(books);
        result.addAll(upstreamBooks);
        return result;
    }

    private List<Book> getUpstreamBooks() {
        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(resourceUrl + "/books?all=true", Book[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public boolean addBook(Book b) {
        return books.add(b);
    }

    public Book getBook(String isbn) {
        return books.stream().filter(book -> book.isbn().equals(isbn)).findFirst().orElse(null);
    }
}
