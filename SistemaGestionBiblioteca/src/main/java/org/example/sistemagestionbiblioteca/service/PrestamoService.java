package org.example.sistemagestionbiblioteca.service;

import org.example.sistemagestionbiblioteca.model.Prestamo;

import java.util.*;

public interface PrestamoService {
    Prestamo buscarPorId(Long id);
    List<Prestamo> obtenerTodos();
    Prestamo guardar(Prestamo prestamo);
    void eliminar(Long id);
    Prestamo actualizar(Long id, Prestamo prestamo);
}