package org.example.sistemagestionbiblioteca.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sistemagestionbiblioteca.controller.UsuarioController;
import org.example.sistemagestionbiblioteca.model.Usuario;
import org.example.sistemagestionbiblioteca.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void getAll_deberiaRetornarUsuarios() throws Exception {
        List<Usuario> lista = List.of(
                new Usuario(1L, "Ana", "ana@mail", "ACTIVO"),
                new Usuario(2L, "Luis", "luis@mail", "ACTIVO")
        );
        when(usuarioService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Luis"));
    }

    @Test
    void getById_deberiaRetornarUsuario() throws Exception {
        Usuario u = new Usuario(2L, "Luis", "luis@mail", "ACTIVO");
        when(usuarioService.buscarPorId(2L)).thenReturn(u);

        mockMvc.perform(get("/api/usuarios/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("luis@mail"));
    }

    @Test
    void post_crearUsuario_deberiaRetornarUsuarioCreado() throws Exception {
        Usuario input = new Usuario(null, "María", "maria@mail", "ACTIVO");
        Usuario creado = new Usuario(5L, "María", "maria@mail", "ACTIVO");
        when(usuarioService.guardar(eq(input))).thenReturn(creado);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.nombre").value("María"));
    }

    @Test
    void put_actualizarUsuario_deberiaRetornarUsuarioActualizado() throws Exception {
        Usuario update = new Usuario(null, "AnaMod", "ana2@mail", "INACTIVO");
        Usuario updated = new Usuario(1L, "AnaMod", "ana2@mail", "INACTIVO");
        when(usuarioService.actualizar(eq(1L), eq(update))).thenReturn(updated);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("INACTIVO"));
    }

    @Test
    void delete_eliminarUsuario_deberiaRetornarOk() throws Exception {
        doNothing().when(usuarioService).eliminar(3L);

        mockMvc.perform(delete("/api/usuarios/3"))
                .andExpect(status().isOk());

        verify(usuarioService).eliminar(3L);
    }
}