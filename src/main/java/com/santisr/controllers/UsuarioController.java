package com.santisr.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.santisr.entities.Usuario;
import com.santisr.services.IUsuario;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuario usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios(
            @RequestParam(name = "sort", defaultValue = "false", required = false) String sort) {

        ResponseEntity<List<Usuario>> responseEntity = null;
        List<Usuario> listaUsuarios = null;

        try {
            // Solicitamos TODOS los valores
            if (Boolean.parseBoolean(sort))
                listaUsuarios = usuarioService.findAll(Sort.by("nombre"));
            else
                listaUsuarios = usuarioService.findAll();

            if (listaUsuarios != null && !listaUsuarios.isEmpty())
                // Encontramos valores para devolver (NO HAY REGISTROS)
                responseEntity = new ResponseEntity<>(listaUsuarios, HttpStatus.OK);
            else
                // NO hay nada que devolver
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (DataAccessException e) {
            // Error NO controlado
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    // Mapping que devuelve Un registro de usuario
    // Se indica el 'id' del registro a obtener mediante el parametro
    // en la URI ( ej.: http://localhost:8080/usuarios/3 )
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserId(
            @PathVariable(name = "id", required = true) Long idUsuario) {

        ResponseEntity<Usuario> responseEntity = null;
        Usuario usuario = null;

        try {
            // Buscamos si el código de registro recibido existe
            usuario = usuarioService.findById(idUsuario).orElse(null);

            if (usuario != null)
                // Encontramos el registro a devolver
                responseEntity = new ResponseEntity<>(usuario, HttpStatus.OK);
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
    public ResponseEntity<Map<String, Object>> addUsuario(
            @Valid @RequestBody(required = true) Usuario usuario,
            BindingResult result) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        if (result.hasErrors() || usuario.getId() > 0) {
            // Se ha encontrado algún error en la validación puesta en la clase
            List<String> errores = new ArrayList<>();
            if (usuario.getId() > 0)
                errores.add("The new record already has Id value'");
            for (ObjectError error : result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("errors", errores);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);

        } else {
            // LOS DATOS RECIBIDOS ESTÁN BIEN, Intentamos la actualización
            try {
                // El registro no EXISTE y lo grabamos
                responseAsMap.put("body", usuarioService.save(usuario));
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);

            } catch (DataAccessException e) {
                // Error NO controlado
                responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return responseEntity;
    }


    @PutMapping
    public ResponseEntity<Map<String, Object>> updateUsuario(@Valid @RequestBody(required = true) Usuario usuario,
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
                Usuario newUsuario = usuarioService.findById(usuario.getId()).orElse(null);

                if (newUsuario == null) {
                    // El registro no se ha encontrado
                    responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    // El registro EXISTE y hacemos la actualización
                    responseAsMap.put("body", usuarioService.save(usuario));
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
    public ResponseEntity<String> deleteUsuario(@PathVariable(name = "id") long idUsuario) {
        ResponseEntity<String> responseEntity = null; // Variable a devolver con el STATUS

        // Buscamos el producto referido para su borrado
        Usuario usuarioToDelete = usuarioService.findById(idUsuario).orElseGet(null);

        // Pueden ocurrir dos (2) cosas ¡... el registro exista o NO exista.
        if (usuarioToDelete == null) {
            // No existe el registro a borrar
            responseEntity = new ResponseEntity<>(String.format("User (Ref. %d) NOT FOUND", idUsuario),
                    HttpStatus.NOT_FOUND);
        } else {
            // Si existe el registro... puede que se borre bien o que haya un error
            try {
                // Se borra correctamente el registro
                usuarioService.delete(idUsuario);
                responseEntity = new ResponseEntity<>(
                        String.format("User (Ref. %d) deleted", idUsuario), HttpStatus.OK);
            } catch (DataAccessException e) {
                // Hay un error al borrar el registro
                String mensaje = e.getMostSpecificCause().toString();
                responseEntity = new ResponseEntity<>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return responseEntity;
    }

}
