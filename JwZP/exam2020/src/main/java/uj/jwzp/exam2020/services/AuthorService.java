package uj.jwzp.exam2020.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uj.jwzp.exam2020.models.Author;
import uj.jwzp.exam2020.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthor(Long id) {
        return authorRepository.findById(id);
    }

    public Author save(Author author) {
        log.info("Saving new author: " + author.getName());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
