package org.example.sistemagestionbiblioteca.repository;

import org.example.sistemagestionbiblioteca.enums.EstadoLibro;
import org.example.sistemagestionbiblioteca.model.Libro;
import org.example.sistemagestionbiblioteca.repository.impl.LibroRepositoryImpl;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LibroRepositoryImplTest {

    private LibroRepositoryImpl repo;

    @BeforeEach
    void setUp() {
        repo = new LibroRepositoryImpl();
    }

    @Test
    void save_asignaIdYGuarda() {
        Libro libro = new Libro(null, "ISBN-1", "Título", "Autor", EstadoLibro.DISPONIBLE);
        Libro guardado = repo.save(libro);

        assertNotNull(guardado.getId());
        assertEquals("ISBN-1", repo.findById(guardado.getId()).get().getIsbn());
    }

    @Test
    void findByIsbn_encuentraCuandoExiste() {
        Libro libro = repo.save(new Libro(null, "ABC-123", "X", "Y", EstadoLibro.DISPONIBLE));
        Optional<Libro> encontrado = repo.findByIsbn("ABC-123");

        assertTrue(encontrado.isPresent());
        assertEquals(libro.getId(), encontrado.get().getId());
    }

    @Test
    void findByIsbn_noExiste_devuelveEmpty() {
        assertFalse(repo.findByIsbn("NO-EXISTE").isPresent());
    }

    @Test
    void findAll_devuelveTodos() {
        repo.save(new Libro(null, "1", "A", "B", EstadoLibro.DISPONIBLE));
        repo.save(new Libro(null, "2", "C", "D", EstadoLibro.DISPONIBLE));

        List<Libro> lista = repo.findAll();
        assertEquals(2, lista.size());
    }

    @Test
    void deleteById_eliminaCorrectamente() {
        Libro libro = repo.save(new Libro(null, "DEL-1", "T", "A", EstadoLibro.DISPONIBLE));
        repo.deleteById(libro.getId());
        assertFalse(repo.findById(libro.getId()).isPresent());
    }

    @Test
    void existsById_retornaTrueYFalse() {
        Libro libro = repo.save(new Libro(null, "EX-1", "T", "A", EstadoLibro.DISPONIBLE));
        assertTrue(repo.existsById(libro.getId()));
        assertFalse(repo.existsById(999L));
    }
}