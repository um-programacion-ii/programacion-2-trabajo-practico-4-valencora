package org.example.sistemagestionbiblioteca.serviceTests;


import org.example.sistemagestionbiblioteca.exception.UsuarioNoEncontradoException;
import org.example.sistemagestionbiblioteca.model.Usuario;
import org.example.sistemagestionbiblioteca.repository.UsuarioRepository;
import org.example.sistemagestionbiblioteca.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioServiceImpl service;

    @Test
    void buscarPorId_existente() {
        Usuario u = new Usuario(1L, "Ana", "ana@mail", "ACTIVO");
        when(repo.findById(1L)).thenReturn(Optional.of(u));

        Usuario res = service.buscarPorId(1L);
        assertEquals("Ana", res.getNombre());
        verify(repo).findById(1L);
    }

    @Test
    void buscarPorId_noExistente_lanza() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(UsuarioNoEncontradoException.class, () ->
                service.buscarPorId(99L)
        );
    }

    @Test
    void obtenerTodos_devuelveLista() {
        List<Usuario> lista = List.of(
                new Usuario(1L, "A", "a@mail", "ACTIVO"),
                new Usuario(2L, "B", "b@mail", "ACTIVO")
        );
        when(repo.findAll()).thenReturn(lista);

        List<Usuario> res = service.obtenerTodos();
        assertEquals(2, res.size());
        verify(repo).findAll();
    }

    @Test
    void guardar_nuevoUsuario_delegaEnRepo() {
        Usuario sinId = new Usuario(null, "C", "c@mail", "ACTIVO");
        Usuario conId = new Usuario(5L, "C", "c@mail", "ACTIVO");
        when(repo.save(sinId)).thenReturn(conId);

        Usuario saved = service.guardar(sinId);
        assertEquals(5L, saved.getId());
        verify(repo).save(sinId);
    }

    @Test
    void eliminar_llamaAlRepo() {
        doNothing().when(repo).deleteById(7L);
        service.eliminar(7L);
        verify(repo).deleteById(7L);
    }

    @Test
    void actualizar_existente_actualizaYDevuelve() {
        Usuario u = new Usuario(3L, "D", "d@mail", "ACTIVO");
        when(repo.existsById(3L)).thenReturn(true);
        when(repo.save(u)).thenReturn(u);

        Usuario res = service.actualizar(3L, u);
        assertEquals("d@mail", res.getEmail());
        verify(repo).existsById(3L);
        verify(repo).save(u);
    }

    @Test
    void actualizar_noExistente_lanzaExcepcion() {
        Usuario u = new Usuario(null, "E", "e@mail", "ACTIVO");
        when(repo.existsById(50L)).thenReturn(false);
        assertThrows(UsuarioNoEncontradoException.class, () ->
                service.actualizar(50L, u)
        );
    }
}
