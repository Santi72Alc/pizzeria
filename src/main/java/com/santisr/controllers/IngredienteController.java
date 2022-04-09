package com.santisr.controllers;

import com.santisr.entities.Ingrediente;
import com.santisr.services.IIngrediente;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    IIngrediente ingredienteService;

    @GetMapping
    public ResponseEntity<List<Ingrediente>> getAllIngredientes(
            @RequestParam(name = "sort", defaultValue = "false", required = false) String sort) {

        ResponseEntity<List<Ingrediente>> responseEntity = null;
        List<Ingrediente> listaIngredientes = null;

        try {
            // Solicitamos TODOS los valores
            if (Boolean.parseBoolean(sort))
                listaIngredientes = ingredienteService.findAll(Sort.by("nombre"));
            else
                listaIngredientes = ingredienteService.findAll();

            if (listaIngredientes != null && !listaIngredientes.isEmpty())
                // Encontramos valores para devolver (NO HAY REGISTROS)
                responseEntity = new ResponseEntity<>(listaIngredientes, HttpStatus.OK);
            else
                // NO hay nada que devolver
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (DataAccessException e) {
            // Error NO controlado
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> getIngredienteById(
            @PathVariable(name = "id", required = true) Long idProducto) {

        ResponseEntity<Ingrediente> responseEntity = null;
        Ingrediente ingrediente = null;

        try {
            // Buscamos si el código de registro recibido existe
            ingrediente = ingredienteService.findById(idProducto).orElse(null);

            if (ingrediente != null)
                // Encontramos el registro a devolver
                responseEntity = new ResponseEntity<>(ingrediente, HttpStatus.OK);
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

    @PutMapping
    public ResponseEntity<Map<String, Object>> updatePizza(@Valid @RequestBody(required = true) Ingrediente ingrediente,
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
                Ingrediente newIngrediente = ingredienteService.findById(ingrediente.getId()).orElse(null);

                if (newIngrediente == null) {
                    // El registro no se ha encontrado
                    responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    // El registro EXISTE y hacemos la actualización
                    responseAsMap.put("body", ingredienteService.save(ingrediente));
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
    public ResponseEntity<String> deleteIngrediente(@PathVariable(name = "id") long idIngrediente) {
        ResponseEntity<String> responseEntity = null; // Variable a devolver con el STATUS

        // Buscamos el producto referido para su borrado
        Ingrediente ingredienteToDelete = ingredienteService.findById(idIngrediente).orElseGet(null);

        // Pueden ocurrir dos (2) cosas ¡... el registro exista o NO exista.
        if (ingredienteToDelete == null) {
            // No existe el registro a borrar
            responseEntity = new ResponseEntity<>(String.format("Ingredient (Ref. %d) NOT FOUND", idIngrediente),
                    HttpStatus.NOT_FOUND);
        } else {
            // Si existe el registro... puede que se borre bien o que haya un error
            try {
                // Se borra correctamente el registro
                ingredienteService.delete(idIngrediente);
                responseEntity = new ResponseEntity<>(
                        String.format("Ingredient (Ref. %d) deleted", idIngrediente), HttpStatus.OK);
            } catch (DataIntegrityViolationException e) {
                // Hay un error al borrar el registro
                // String mensaje = e.getMostSpecificCause().toString();
                String mensaje = "This ingredient is been used. Cant' be deleted";
                responseEntity = new ResponseEntity<>(mensaje, HttpStatus.CONFLICT);
            } catch (DataAccessException e) {
                // Hay un error al borrar el registro
                String mensaje = e.getMostSpecificCause().toString();
                responseEntity = new ResponseEntity<>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return responseEntity;
    }

}
