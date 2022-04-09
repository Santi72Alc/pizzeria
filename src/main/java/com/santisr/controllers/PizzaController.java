package com.santisr.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.santisr.entities.Pizza;
import com.santisr.services.IPizza;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/pizzas")
public class PizzaController {
    IPizza pizzaService;

    public PizzaController(IPizza pizzaService) {
        this.pizzaService = pizzaService;
    }

    // Mapping que devuelve TODOS los registros de pizzas
    // Se puede recibir el parámetro 'sort' {true|false} para recibir
    // lo datos ordenados por nombre
    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizzas(
            @RequestParam(name = "sort", defaultValue = "false", required = false) String sort) {

        ResponseEntity<List<Pizza>> responseEntity = null;
        List<Pizza> listaPizzas = null;

        try {
            // Solicitamos TODOS los valores
            if (Boolean.parseBoolean(sort))
                listaPizzas = pizzaService.findAll(Sort.by("nombre"));
            else
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

    // Mapping que devuelve Un registro de pizza
    // Se indica el 'id' del registroa obtener mediante el parametro
    // en la URI ( ej.: http://localhost:8080/pizzas/3 )
    @GetMapping("/{id}")
    public ResponseEntity<Pizza> getPizzaById(
            @PathVariable(name = "id", required = true) Long idProducto) {

        ResponseEntity<Pizza> responseEntity = null;
        Pizza pizza = null;

        try {
            // // Buscamos si el código de registro recibido existe
            pizza = pizzaService.findById(idProducto).orElse(null);

            if (pizza != null)
                // Encontramos el registro a devolver
                responseEntity = new ResponseEntity<>(pizza, HttpStatus.OK);
            else
                // NO se ha encontrado el registro
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (NumberFormatException e) {
            // El error se produce al reibir el código del registro a buscar
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            // Error NO controlado
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<Void> deletePizza(
    //         @PathVariable(name = "id", required = true) Long idProducto) {

    //     ResponseEntity<Void> responseEntity = null;
    //     Pizza pizza = null;

    //     try {
    //         // // Buscamos si el código de registro recibido existe
    //         pizza = pizzaService.existById();

    //         if (pizza != null)
    //             // Encontramos el registro a devolver
    //             responseEntity = null;
    //         else
    //             // NO se ha encontrado el registro
    //             responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    //     } catch (NumberFormatException e) {
    //         // El error se produce al reibir el código del registro a buscar
    //         responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     } catch (DataAccessException e) {
    //         // Error NO controlado
    //         responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    //     return responseEntity;
    // }

}
