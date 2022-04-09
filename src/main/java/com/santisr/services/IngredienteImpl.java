package com.santisr.services;

import java.util.List;
import java.util.Optional;

import com.santisr.dao.IIngredienteDao;
import com.santisr.entities.Ingrediente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class IngredienteImpl implements IIngrediente {
    @Autowired
    private IIngredienteDao connDao;

    @Override
    public List<Ingrediente> findAll() {
        return connDao.findAll();
    }

    @Override
    public List<Ingrediente> findAll(Sort sort) {
        return connDao.findAll(sort);
    }

    @Override
    public Optional<Ingrediente> findById(long id) {
        return connDao.findById(id);
    }

    @Override
    public void delete(long id) {
        connDao.deleteById(id);
    }

    @Override
    public Ingrediente save(Ingrediente ingrediente) {
        return connDao.save(ingrediente);
    }

}
