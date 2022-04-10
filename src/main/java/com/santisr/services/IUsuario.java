package com.santisr.services;

import java.util.List;
import java.util.Optional;

import com.santisr.entities.Usuario;

import org.springframework.data.domain.Sort;

public interface IUsuario {

    public List<Usuario> findAll();

    public List<Usuario> findAll(Sort sort);

    public Optional<Usuario> findById(long id);

    public void delete(long id);
    
    public Usuario save(Usuario usuario);

}
