package com.santisr.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.santisr.entities.Pizza;
import com.santisr.services.IPizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/pizzas")
public class PizzaController {

    @Autowired
    IPizza pizzaService;

    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas() {

        ResponseEntity<List<Pizza>> responseEntity = null;
        List<Pizza> listaPizzas = null;

        try {
            // Solicitamos TODOS los valores
            listaPizzas = pizzaService.findAll();

            if (listaPizzas != null && !listaPizzas.isEmpty())
                // Encontramos valores para devolver (NO HAY REGISTROS)
                responseEntity = new ResponseEntity<>(listaPizzas, HttpStatus.OK);
            else
                // NO hay nada que devolver
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
           // Error NO controlado
           responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
