package org.example.sistemagestionbiblioteca.repository;

import org.example.sistemagestionbiblioteca.model.Usuario;

import java.util.*;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    List<Usuario> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}