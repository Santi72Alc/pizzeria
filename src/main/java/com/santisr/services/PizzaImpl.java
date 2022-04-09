package com.santisr.services;

import java.util.List;

import com.santisr.dao.IPizzaDao;
import com.santisr.entities.Pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PizzaImpl implements IPizza {

    @Autowired
    IPizzaDao connDao;

    @Override
    public List<Pizza> findAll() {
        return connDao.findAll();
    }

    @Override
    public List<Pizza> findAll(Sort sort) {
        return null;
    }

    @Override
    public Pizza findById(long id) {
        return null;
    }

}
