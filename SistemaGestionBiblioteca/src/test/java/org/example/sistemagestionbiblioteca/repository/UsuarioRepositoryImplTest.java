package org.example.sistemagestionbiblioteca.repository;

import org.example.sistemagestionbiblioteca.model.Usuario;
import org.example.sistemagestionbiblioteca.repository.impl.UsuarioRepositoryImpl;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRepositoryImplTest {

    private UsuarioRepositoryImpl repo;

    @BeforeEach
    void setUp() {
        repo = new UsuarioRepositoryImpl();
    }

    @Test
    void save_asignaIdYGuarda() {
        Usuario u = new Usuario(null, "Juan", "juan@mail", "ACTIVO");
        Usuario saved = repo.save(u);
        assertNotNull(saved.getId());
        assertEquals("juan@mail", repo.findById(saved.getId()).get().getEmail());
    }

    @Test
    void findAll_yExiste() {
        repo.save(new Usuario(null, "A", "a@mail", "ACTIVO"));
        repo.save(new Usuario(null, "B", "b@mail", "ACTIVO"));
        assertEquals(2, repo.findAll().size());
    }

    @Test
    void existsById_retornaTrueYFalse() {
        Usuario u = repo.save(new Usuario(null, "X", "x@mail", "ACTIVO"));
        assertTrue(repo.existsById(u.getId()));
        assertFalse(repo.existsById(999L));
    }

    @Test
    void deleteById_eliminaYexistsById() {
        Usuario u = repo.save(new Usuario(null, "X", "x@mail", "ACTIVO"));
        repo.deleteById(u.getId());
        assertFalse(repo.existsById(u.getId()));
    }
}