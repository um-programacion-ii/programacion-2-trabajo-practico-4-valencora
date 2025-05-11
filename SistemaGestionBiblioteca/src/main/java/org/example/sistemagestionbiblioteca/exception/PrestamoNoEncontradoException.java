package org.example.sistemagestionbiblioteca.exception;

public class PrestamoNoEncontradoException extends RuntimeException {
    public PrestamoNoEncontradoException(Long id) {
        super("Préstamo no encontrado con id: " + id);
    }
}