package com.santisr.services;

import java.util.List;

import com.santisr.entities.Pizza;

import org.springframework.data.domain.Sort;

public interface IPizza {

    public List<Pizza> findAll();

    public List<Pizza> findAll(Sort sort);

    public Pizza findById(long id);

}
