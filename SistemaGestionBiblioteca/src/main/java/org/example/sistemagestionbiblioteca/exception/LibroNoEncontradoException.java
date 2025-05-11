package org.example.sistemagestionbiblioteca.exception;

public class LibroNoEncontradoException extends RuntimeException {
    public LibroNoEncontradoException(Long id) {
        super("Libro no encontrado con id: " + id);
    }
    public LibroNoEncontradoException(String isbn) {
        super("Libro no encontrado con ISBN: " + isbn);
    }
}