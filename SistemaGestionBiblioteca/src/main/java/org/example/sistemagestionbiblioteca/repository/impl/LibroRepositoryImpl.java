package org.example.sistemagestionbiblioteca.repository.impl;

import org.example.sistemagestionbiblioteca.model.Libro;
import org.example.sistemagestionbiblioteca.repository.LibroRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LibroRepositoryImpl implements LibroRepository {
    private final List<Libro> storage = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public Libro save(Libro libro) {
        if (libro.getId() == null) {
            libro.setId(nextId++);
            storage.add(libro);
        } else {
            storage.removeIf(l -> l.getId().equals(libro.getId()));
            storage.add(libro);
        }
        return libro;
    }

    @Override
    public Optional<Libro> findById(Long id) {
        return storage.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Libro> findByIsbn(String isbn) {
        return storage.stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst();
    }

    @Override
    public List<Libro> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public void deleteById(Long id) {
        storage.removeIf(l -> l.getId().equals(id));
    }

    @Override
    public boolean existsById(Long id) {
        return storage.stream().anyMatch(l -> l.getId().equals(id));
    }
}
