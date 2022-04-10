package com.santisr.services;

import java.util.List;
import java.util.Optional;

import com.santisr.dao.IIngredienteDao;
import com.santisr.entities.Ingrediente;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class IngredienteImpl implements IIngrediente {
    private IIngredienteDao connDao;

    public IngredienteImpl(IIngredienteDao connDao) {
        this.connDao = connDao;

        if (connDao.count() < 1) {
            createIngredientesBasicos();
        }
    }

    // Creamos algunos ingredientes básicos
    private void createIngredientesBasicos() {

        connDao.save(new Ingrediente("Base fina", 3.40));
        connDao.save(new Ingrediente("Base normal", 3.40));
        connDao.save(new Ingrediente("Peperoni", 1.20));
        connDao.save(new Ingrediente("3Quesos", 1.45));
        connDao.save(new Ingrediente("Piña", 1.50));
        connDao.save(new Ingrediente("Atún", 1.20));
        connDao.save(new Ingrediente("Carne picada", 1.65));
        connDao.save(new Ingrediente("Orégano", 0.80));
        connDao.save(new Ingrediente("Tomillo", 0.80));
        connDao.save(new Ingrediente("Pimiento verde", 1.90));
        connDao.save(new Ingrediente("Pimiento rojo", 1.90));
        connDao.save(new Ingrediente("Jamón york", 0.95));

    }

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
