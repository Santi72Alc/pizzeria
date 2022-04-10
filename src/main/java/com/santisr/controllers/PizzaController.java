package com.santisr.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;

import com.santisr.entities.Pizza;
import com.santisr.services.IPizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/pizzas")
public class PizzaController {

    @Autowired
    private IPizza pizzaService;

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

        } catch (DataAccessException e) {
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
            // Buscamos si el código de registro recibido existe
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


    @PostMapping
    public ResponseEntity<Map<String, Object>> addPizza(
            @Valid @RequestBody(required = true) Pizza pizza,
            BindingResult result) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        if (result.hasErrors() || pizza.getId() > 0) {
            // Se ha encontrado algún error en la validación puesta en la clase
            List<String> errores = new ArrayList<>();
            if (pizza.getId() > 0)
                errores.add("The new record must'n has Id value'");
            for (ObjectError error : result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("errors", errores);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);

        } else {
            // LOS DATOS RECIBIDOS ESTÁN BIEN, Intentamos la actualización
            try {
                // El registro no EXISTE y lo grabamos
                responseAsMap.put("body", pizzaService.save(pizza));
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);

            } catch (DataAccessException e) {
                // Error NO controlado
                responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return responseEntity;
    }


    @PutMapping
    public ResponseEntity<Map<String, Object>> updatePizza(@Valid @RequestBody(required = true) Pizza pizza,
            BindingResult result) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        if (result.hasErrors()) {
            // Se ha encontrado algún error en la validación puesta en la clase
            List<String> errores = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("errors", errores);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);

        } else {
            // LOS DATOS RECIBIDOS ESTÁN BIEN, Intentamos la actualización
            try {
                Pizza newPizza = pizzaService.findById(pizza.getId()).orElse(null);

                if (newPizza == null) {
                    // El registro no se ha encontrado
                    responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    // El registro EXISTE y hacemos la actualización
                    responseAsMap.put("body", pizzaService.save(pizza));
                    responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);
                }
            } catch (DataAccessException e) {
                // Error NO controlado
                responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePizza(@PathVariable(name = "id") long idPizza) {
        ResponseEntity<String> responseEntity = null; // Variable a devolver con el STATUS

        // Buscamos el producto referido para su borrado
        Pizza pizzaToDelete = pizzaService.findById(idPizza).orElseGet(null);

        // Pueden ocurrir dos (2) cosas ¡... el registro exista o NO exista.
        if (pizzaToDelete == null) {
            // No existe el registro a borrar
            responseEntity = new ResponseEntity<>(String.format("Pizza (Ref. %d) NOT FOUND", idPizza),
                    HttpStatus.NOT_FOUND);
        } else {
            // Si existe el registro... puede que se borre bien o que haya un error
            try {
                // Se borra correctamente el registro
                pizzaService.delete(idPizza);
                responseEntity = new ResponseEntity<>(
                        String.format("Pizza (Ref. %d) deleted", idPizza), HttpStatus.OK);
            } catch (DataAccessException e) {
                // Hay un error al borrar el registro
                String mensaje = e.getMostSpecificCause().toString();
                responseEntity = new ResponseEntity<>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return responseEntity;
    }

}
