package com.santisr.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Fecha's comment is required")
    private LocalDateTime fecha;

    @NotNull(message = "Usuario's comment is required")
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Usuario usuario;

    @NotNull(message = "Pizza's comment is required")
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Pizza pizza;

    @Size(min = 0, max = 5, message = "The score value must to be between 0 and 5")
    private int puntuacion;

    private String comentario;

    public Comentario() {
        super();
    }

    public Comentario(long id, LocalDateTime fecha, Usuario usuario, Pizza pizza, int puntuacion,
            String comentario) {
        super();
        this.id = id;
        this.fecha = fecha;
        this.usuario = usuario;
        this.pizza = pizza;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
