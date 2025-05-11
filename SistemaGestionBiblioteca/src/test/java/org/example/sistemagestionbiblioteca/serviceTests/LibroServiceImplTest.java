package org.example.sistemagestionbiblioteca.serviceTests;

import org.example.sistemagestionbiblioteca.enums.EstadoLibro;
import org.example.sistemagestionbiblioteca.exception.LibroNoEncontradoException;
import org.example.sistemagestionbiblioteca.model.Libro;
import org.example.sistemagestionbiblioteca.repository.LibroRepository;
import org.example.sistemagestionbiblioteca.service.impl.LibroServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    @Mock
    private LibroRepository repo;

    @InjectMocks
    private LibroServiceImpl service;

    @Test

    void buscarPorIsbn_existente() {
        Libro l = new Libro(1L, "ISBN-1", "T", "A", EstadoLibro.DISPONIBLE);
        when(repo.findByIsbn("ISBN-1")).thenReturn(Optional.of(l));

        Libro resultado = service.buscarPorIsbn("ISBN-1");
        assertEquals(1L, resultado.getId());
        verify(repo).findByIsbn("ISBN-1");
    }

    @Test
    void buscarPorIsbn_noExistente_lanza() {
        when(repo.findByIsbn("NO")).thenReturn(Optional.empty());
        assertThrows(LibroNoEncontradoException.class, () ->
                service.buscarPorIsbn("NO")
        );
    }

    @Test
    void obtenerTodos_devuelveLista() {
        List<Libro> lista = List.of(
                new Libro(1L, "A", "T1", "Au1", EstadoLibro.DISPONIBLE),
                new Libro(2L, "B", "T2", "Au2", EstadoLibro.PRESTADO)
        );
        when(repo.findAll()).thenReturn(lista);

        List<Libro> resultado = service.obtenerTodos();
        assertEquals(2, resultado.size());
        verify(repo).findAll();
    }

    @Test
    void guardar_nuevoLibro_delegaEnRepo() {
        Libro sinId = new Libro(null, "X", "Titulo", "Autor", EstadoLibro.DISPONIBLE);
        Libro conId = new Libro(5L, "X", "Titulo", "Autor", EstadoLibro.DISPONIBLE);
        when(repo.save(sinId)).thenReturn(conId);

        Libro saved = service.guardar(sinId);
        assertEquals(5L, saved.getId());
        verify(repo).save(sinId);
    }

    @Test
    void eliminar_llamaAlRepo() {
        doNothing().when(repo).deleteById(3L);
        service.eliminar(3L);
        verify(repo).deleteById(3L);
    }

    @Test
    void actualizar_existente_actualizaYDevuelve() {
        Libro actualizado = new Libro(2L, "Y", "T", "Au", EstadoLibro.PRESTADO);
        when(repo.existsById(2L)).thenReturn(true);
        when(repo.save(actualizado)).thenReturn(actualizado);

        Libro result = service.actualizar(2L, actualizado);
        assertEquals(EstadoLibro.PRESTADO, result.getEstado());
        verify(repo).existsById(2L);
        verify(repo).save(actualizado);
    }

    @Test
    void actualizar_noExistente_lanzaExcepcion() {
        Libro l = new Libro(null, "Z", "T", "Au", EstadoLibro.DISPONIBLE);
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(LibroNoEncontradoException.class, () ->
                service.actualizar(99L, l)
        );
    }
}