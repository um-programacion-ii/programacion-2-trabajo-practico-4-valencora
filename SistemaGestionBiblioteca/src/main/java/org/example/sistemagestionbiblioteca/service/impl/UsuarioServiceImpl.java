package org.example.sistemagestionbiblioteca.service.impl;

import org.example.sistemagestionbiblioteca.exception.UsuarioNoEncontradoException;
import org.example.sistemagestionbiblioteca.model.Usuario;
import org.example.sistemagestionbiblioteca.repository.UsuarioRepository;
import org.example.sistemagestionbiblioteca.service.UsuarioService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException(id);
        }
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }
}
