package com.santisr.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Ingrediente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Ingredient's name is required")
    private String nombre;

    @PositiveOrZero(message = "Ingredient's price can't be negative")
    private double precio;

    // Creo que no hace falta referenciar la pizza en el ingrediente porque
    // no depende de que esté o no en ninguna pizza
    // @ManyToOne
    // private Pizza pizza;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Ingrediente() {
    }

    public Ingrediente(long id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
