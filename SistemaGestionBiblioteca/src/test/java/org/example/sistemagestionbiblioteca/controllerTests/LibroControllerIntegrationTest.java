package org.example.sistemagestionbiblioteca.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sistemagestionbiblioteca.controller.LibroController;
import org.example.sistemagestionbiblioteca.enums.EstadoLibro;
import org.example.sistemagestionbiblioteca.model.Libro;
import org.example.sistemagestionbiblioteca.service.LibroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroController.class)
class LibroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LibroService libroService;

    @Test
    void getAll_deberiaRetornarLista() throws Exception {
        List<Libro> lista = List.of(
                new Libro(1L, "111-222-333", "Título1", "Autor1", EstadoLibro.DISPONIBLE),
                new Libro(2L, "444-555-666", "Título2", "Autor2", EstadoLibro.PRESTADO)
        );
        when(libroService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].isbn").value("111-222-333"));
    }

    @Test
    void getById_deberiaRetornarLibro() throws Exception {
        Libro libro = new Libro(2L, "444-555-666", "Otro", "Autor2", EstadoLibro.PRESTADO);
        when(libroService.buscarPorId(2L)).thenReturn(libro);

        mockMvc.perform(get("/api/libros/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Otro"));
    }

    @Test
    void post_crearLibro_deberiaRetornarLibroCreado() throws Exception {
        Libro input = new Libro(null, "999-888-777", "Nuevo", "AutorN", EstadoLibro.DISPONIBLE);
        Libro creado = new Libro(3L, "999-888-777", "Nuevo", "AutorN", EstadoLibro.DISPONIBLE);
        when(libroService.guardar(eq(input))).thenReturn(creado);

        mockMvc.perform(post("/api/libros")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.isbn").value("999-888-777"));
    }

    @Test
    void put_actualizarLibro_deberiaRetornarLibroActualizado() throws Exception {
        Libro update = new Libro(null, "111-222-333", "Modificado", "AutorM", EstadoLibro.EN_REPARACION);
        Libro updated = new Libro(1L, "111-222-333", "Modificado", "AutorM", EstadoLibro.EN_REPARACION);
        when(libroService.actualizar(eq(1L), eq(update))).thenReturn(updated);

        mockMvc.perform(put("/api/libros/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("EN_REPARACION"));
    }

    @Test
    void delete_eliminarLibro_deberiaRetornarOk() throws Exception {
        doNothing().when(libroService).eliminar(4L);

        mockMvc.perform(delete("/api/libros/4"))
                .andExpect(status().isOk());

        verify(libroService).eliminar(4L);
    }
}