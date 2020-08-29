package uj.jwzp.exam2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uj.jwzp.exam2020.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
