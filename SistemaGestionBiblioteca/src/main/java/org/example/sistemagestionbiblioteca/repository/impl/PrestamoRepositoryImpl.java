package org.example.sistemagestionbiblioteca.repository.impl;

import org.example.sistemagestionbiblioteca.model.Prestamo;
import org.example.sistemagestionbiblioteca.repository.PrestamoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PrestamoRepositoryImpl implements PrestamoRepository {
    private final List<Prestamo> storage = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public Prestamo save(Prestamo prestamo) {
        if (prestamo.getId() == null) {
            prestamo.setId(nextId++);
            storage.add(prestamo);
        } else {
            storage.removeIf(p -> p.getId().equals(prestamo.getId()));
            storage.add(prestamo);
        }
        return prestamo;
    }

    @Override
    public Optional<Prestamo> findById(Long id) {
        return storage.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Prestamo> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public void deleteById(Long id) {
        storage.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public boolean existsById(Long id) {
        return storage.stream().anyMatch(p -> p.getId().equals(id));
    }
}