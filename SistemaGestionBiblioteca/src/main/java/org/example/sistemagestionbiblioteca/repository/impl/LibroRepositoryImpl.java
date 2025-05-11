package org.example.sistemagestionbiblioteca.repository.impl;

import org.example.sistemagestionbiblioteca.model.Libro;
import org.example.sistemagestionbiblioteca.repository.LibroRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LibroRepositoryImpl implements LibroRepository {
    private final Map<Long, Libro> storage = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Libro save(Libro libro) {
        if (libro.getId() == null) {
            libro.setId(nextId++);
        }
        storage.put(libro.getId(), libro);
        return libro;
    }

    @Override
    public Optional<Libro> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Libro> findByIsbn(String isbn) {
        return storage.values().stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst();
    }

    @Override
    public List<Libro> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}