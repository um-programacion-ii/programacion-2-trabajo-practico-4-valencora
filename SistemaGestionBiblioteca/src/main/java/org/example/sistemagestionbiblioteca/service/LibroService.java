package org.example.sistemagestionbiblioteca.service;

import org.example.sistemagestionbiblioteca.model.Libro;

import java.util.*;

public interface LibroService {
    Libro buscarPorIsbn(String isbn);
    List<Libro> obtenerTodos();
    Libro guardar(Libro libro);
    void eliminar(Long id);
    Libro actualizar(Long id, Libro libro);
}