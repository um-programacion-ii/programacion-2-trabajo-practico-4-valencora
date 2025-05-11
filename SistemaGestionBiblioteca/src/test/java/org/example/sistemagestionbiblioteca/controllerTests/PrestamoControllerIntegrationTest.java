package org.example.sistemagestionbiblioteca.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sistemagestionbiblioteca.controller.PrestamoController;
import org.example.sistemagestionbiblioteca.enums.EstadoLibro;
import org.example.sistemagestionbiblioteca.model.Libro;
import org.example.sistemagestionbiblioteca.model.Prestamo;
import org.example.sistemagestionbiblioteca.model.Usuario;
import org.example.sistemagestionbiblioteca.service.PrestamoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrestamoController.class)
class PrestamoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PrestamoService prestamoService;

    private final Libro libroSample = new Libro(1L, "L-1", "T1", "A1", EstadoLibro.DISPONIBLE);
    private final Usuario usuarioSample = new Usuario(1L, "U1", "u1@mail", "ACTIVO");

    @Test
    void getAll_deberiaRetornarPrestamos() throws Exception {
        Prestamo p1 = new Prestamo(5L, libroSample, usuarioSample, LocalDate.now(), LocalDate.now().plusDays(1));
        Prestamo p2 = new Prestamo(6L, libroSample, usuarioSample, LocalDate.now(), LocalDate.now().plusDays(2));
        when(prestamoService.obtenerTodos()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getById_deberiaRetornarPrestamo() throws Exception {
        Prestamo p = new Prestamo(7L, libroSample, usuarioSample, LocalDate.now(), LocalDate.now().plusDays(1));
        when(prestamoService.buscarPorId(7L)).thenReturn(p);

        mockMvc.perform(get("/api/prestamos/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7));
    }

    @Test
    void post_crearPrestamo_deberiaRetornarPrestamoCreado() throws Exception {
        Prestamo input = new Prestamo(null, libroSample, usuarioSample, LocalDate.now(), LocalDate.now().plusDays(3));
        Prestamo creado = new Prestamo(8L, libroSample, usuarioSample, LocalDate.now(), LocalDate.now().plusDays(3));
        when(prestamoService.guardar(eq(input))).thenReturn(creado);

        mockMvc.perform(post("/api/prestamos")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8));
    }

    @Test
    void put_actualizarPrestamo_deberiaRetornarPrestamoActualizado() throws Exception {
        Prestamo update = new Prestamo(null, libroSample, usuarioSample, LocalDate.now(), LocalDate.now().plusDays(5));
        Prestamo updated = new Prestamo(5L, libroSample, usuarioSample, LocalDate.now(), LocalDate.now().plusDays(5));
        when(prestamoService.actualizar(eq(5L), eq(update))).thenReturn(updated);

        mockMvc.perform(put("/api/prestamos/5")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fechaDevolucion")
                        .value(LocalDate.now().plusDays(5).toString()));
    }

    @Test
    void delete_eliminarPrestamo_deberiaRetornarOk() throws Exception {
        doNothing().when(prestamoService).eliminar(9L);

        mockMvc.perform(delete("/api/prestamos/9"))
                .andExpect(status().isOk());

        verify(prestamoService).eliminar(9L);
    }
}