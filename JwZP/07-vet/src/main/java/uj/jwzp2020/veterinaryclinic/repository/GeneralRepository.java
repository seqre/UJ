package uj.jwzp2020.veterinaryclinic.repository;

import java.util.List;
import java.util.Optional;

public interface GeneralRepository<T> {
    List<T> findAll();

    T save(T entity);

    Optional<T> findById(Long id);
}
