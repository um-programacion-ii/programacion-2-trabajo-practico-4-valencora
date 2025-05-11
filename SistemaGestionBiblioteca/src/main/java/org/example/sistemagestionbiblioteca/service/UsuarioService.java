package org.example.sistemagestionbiblioteca.service;

import org.example.sistemagestionbiblioteca.model.Usuario;

import java.util.*;

public interface UsuarioService {
    Usuario buscarPorId(Long id);
    List<Usuario> obtenerTodos();
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Usuario actualizar(Long id, Usuario usuario);
}