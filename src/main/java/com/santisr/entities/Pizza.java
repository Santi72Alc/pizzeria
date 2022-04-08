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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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

}
