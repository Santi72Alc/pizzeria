package com.santisr.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Pizza implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double COMISION = 1.20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Para poder tener el indice secundario con la tabla ingredientes
    @OneToMany
    private List<Ingrediente> ingredientes;

    // Para poder tener el indice secundario con la tabla comentarios
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "pizza")
    private List<Comentario> comentarios;

    public Pizza() {
        super();
    }

    public Pizza(long id, List<Ingrediente> ingredientes, List<Comentario> comentarios) {
        super();
        this.id = id;
        this.ingredientes = ingredientes;
        this.comentarios = comentarios;
    }

    private double getPrecioIngredientes() {
        double precioIngredientes = 0;
        for (Ingrediente ingrediente : ingredientes) {
            precioIngredientes += ingrediente.getPrecio();
        }
        return precioIngredientes;
    }

    public double getPrecio() {
        return getPrecioIngredientes() * COMISION;
    }

    public double getCOMISION() {
        return COMISION;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

}
