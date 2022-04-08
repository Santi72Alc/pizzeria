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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Comment's fecha is required")
    private LocalDateTime fecha;

    @NotNull(message = "Comment's usuario is required")
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Usuario usuario;

    @NotNull(message = "Comment's pizza is required")
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Pizza pizza;

    @Size(min = 0, max = 5, message = "Comment's score value must to be between 0 and 5")
    private int puntuacion;

    @NotNull(message = "The comment text is required")
    private String comentario;

}
