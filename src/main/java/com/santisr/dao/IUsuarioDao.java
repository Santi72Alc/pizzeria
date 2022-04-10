package com.santisr.dao;

import java.util.List;
import java.util.Optional;

import com.santisr.entities.Usuario;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

    public List<Usuario> findAll();

    public List<Usuario> findAll(Sort sort);

    public Optional<Usuario> findById(long id);

}
