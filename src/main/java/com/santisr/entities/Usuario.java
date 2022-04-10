package com.santisr.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.santisr.constants.Role;

@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "User's name is required")
    private String nombre;

    private String apellidos;

    @Email(message = "User's email has a wrong format")
    private String email;

    private String foto;

    @Size(min = 4, max = 10, message = "User's password must to have between 4 and 10 characters")
    private String password;

    private Role role;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    private List<Comentario> comentarios;

    /* CONSTRUCTORES */
    public Usuario() {
    }

    public Usuario(long id, String nombre, String apellidos, String email, String foto, String password, Role role,
            List<Comentario> comentarios) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.foto = foto;
        this.password = password;
        this.role = role;
        this.comentarios = comentarios;
    }
    public Usuario(String nombre, String apellidos, String email, String password,
            Role role) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Usuario(String nombre, String apellidos, String email, String foto, String password,
            Role role) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.foto = foto;
        this.password = password;
        this.role = role;
    }

    /* METODOS */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

}
