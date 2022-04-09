package com.santisr.services;

import java.util.List;
import java.util.Optional;

import com.santisr.entities.Ingrediente;

import org.springframework.data.domain.Sort;

public interface IIngrediente {

    public List<Ingrediente> findAll();

    public List<Ingrediente> findAll(Sort sort);

    public Optional<Ingrediente> findById(long id);
}
