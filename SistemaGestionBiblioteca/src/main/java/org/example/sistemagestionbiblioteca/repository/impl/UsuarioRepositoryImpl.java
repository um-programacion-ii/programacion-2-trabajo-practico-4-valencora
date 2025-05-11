package org.example.sistemagestionbiblioteca.repository.impl;

import org.example.sistemagestionbiblioteca.model.Usuario;
import org.example.sistemagestionbiblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final Map<Long, Usuario> storage = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(nextId++);
        }
        storage.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Usuario> findAll() {
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