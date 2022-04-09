package com.santisr.dao;

import java.util.List;

import com.santisr.entities.Pizza;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPizzaDao extends JpaRepository<Pizza, Long> {

    @Query( value = "select p from Pizza p")
    public List<Pizza> findAll();

    @Query( value = "select p from Pizza p")
    public List<Pizza> findAll(Sort sort);
    
    // // @Query( value = "select p from pizza p left join fetch p.comentarios, p.ingredientes where id = :id")
    @Query( value = "select p from Pizza p where id = :id")
    public Pizza findById(long id);
}
