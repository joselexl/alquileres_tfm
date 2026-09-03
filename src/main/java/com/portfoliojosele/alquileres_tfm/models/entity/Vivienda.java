package com.portfoliojosele.alquileres_tfm.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "viviendas")
public class Vivienda implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "La dirección es obligatoria")
    private String direccion;

    @NotEmpty(message = "La ciudad o pueblo es obligatorio")
    private String localidad;

    // excepcion de control para controlar el texto con @NotNull
    @NotNull(message = "El precio de alquiler es obligatorio")
    private Double precio;

    @NotNull(message = "El número de habitaciones es obligatorio")
    private Integer habitaciones;

    @NotEmpty(message = "Indica el estado de la vivienda")
    private String estado;

    // Getters y Setters de la entidad vivienda
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

}
