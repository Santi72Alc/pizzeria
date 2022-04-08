package com.santisr.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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

    // Lo creo aqui porque con @getter NO ME LO RECONOCE al necesitarlo
    // antes de la ejecución en Pizza (calculo de precio)
    public double getPrecio() {
        return precio;
    }

}
