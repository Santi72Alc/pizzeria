package com.santisr.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.santisr.constants.Importes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pizza implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Pizza's name is required")
    private String nombre;

    private String foto;

    // Para poder tener el indice secundario con la tabla ingredientes
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Ingrediente> ingredientes;

    // Para poder tener el indice secundario con la tabla comentarios
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "pizza")
    private List<Comentario> comentarios;

    private double getPrecioIngredientes() {
        double precioIngredientes = 0;
        for (Ingrediente ingrediente : ingredientes) {
            precioIngredientes += ingrediente.getPrecio();
        }
        return precioIngredientes;
    }

    public double getPrecio() {
        return getPrecioIngredientes() * Importes.COMISION;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setComentarios(List<Comentario> comentarios) {
        if (comentarios == null)
            comentarios = new ArrayList<>();
        this.comentarios = comentarios;
    }

    public void addIngrediente(Ingrediente ingrediente) {
        if (ingredientes == null)
            ingredientes = new ArrayList<>();
        ingredientes.add(ingrediente);
    }

    public boolean removeIngrediente(Ingrediente ingrediente) {
        return ingredientes.remove(ingrediente);
    }

}
