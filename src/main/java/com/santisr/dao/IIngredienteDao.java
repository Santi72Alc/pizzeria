package com.santisr.dao;

import java.util.List;
import java.util.Optional;

import com.santisr.entities.Ingrediente;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIngredienteDao extends JpaRepository<Ingrediente, Long> {

    public List<Ingrediente> findAll();

    public List<Ingrediente> findAll(Sort sort);

    public Optional<Ingrediente> findById(long id);

}
