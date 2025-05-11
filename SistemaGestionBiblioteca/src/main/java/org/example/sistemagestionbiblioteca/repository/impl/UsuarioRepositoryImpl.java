package org.example.sistemagestionbiblioteca.repository.impl;

import org.example.sistemagestionbiblioteca.model.Usuario;
import org.example.sistemagestionbiblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final List<Usuario> storage = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(nextId++);
            storage.add(usuario);
        } else {
            storage.removeIf(u -> u.getId().equals(usuario.getId()));
            storage.add(usuario);
        }
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return storage.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public void deleteById(Long id) {
        storage.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public boolean existsById(Long id) {
        return storage.stream().anyMatch(u -> u.getId().equals(id));
    }
}