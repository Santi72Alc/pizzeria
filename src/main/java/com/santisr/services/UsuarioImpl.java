package com.santisr.services;

import java.util.List;
import java.util.Optional;

import com.santisr.constants.Role;
import com.santisr.dao.IUsuarioDao;
import com.santisr.entities.Usuario;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UsuarioImpl implements IUsuario {
    private IUsuarioDao connDao;

    public UsuarioImpl(IUsuarioDao connDao) {
        this.connDao = connDao;

        if (connDao.count() < 1) {
            createUsuarioAdmin();
        }
    }

    // Creamos el usuario 'admin' con password 'admin'
    private void createUsuarioAdmin() {
        connDao.save(new Usuario("admin", "", "admin@pizzeria.com", "admin", Role.ADMIN));
    }

    @Override
    public List<Usuario> findAll() {
       return connDao.findAll();
    }

    @Override
    public List<Usuario> findAll(Sort sort) {
        return connDao.findAll(sort);
    }

    @Override
    public Optional<Usuario> findById(long id) {
        return connDao.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return connDao.save(usuario);
    }

    @Override
    public void delete(long id) {
       connDao.deleteById(id);
    }

}
