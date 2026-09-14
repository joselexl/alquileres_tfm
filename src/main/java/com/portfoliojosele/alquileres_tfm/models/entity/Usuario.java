package com.portfoliojosele.alquileres_tfm.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa a los usuarios Se utiliza para la autenticación y autorización mediante Spring Security.
 **/

@Entity 
@Table (name = "usuarios")
public class Usuario {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    // El nombre de usuario debe ser único
    @Column (nullable = false, unique = true, length = 50)
    private String username;

    // La contraseña se encripta con BCrypt
    @Column(nullable = false, length = 255)
    private String password;

    // Aqui guardaremos el rol 
    @Column(nullable = false, length = 50)
    private String rol;

    public Usuario() {
    }

    // Constructor para facilitarme la vida creando usuarios
    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    // Getters y Setters   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}