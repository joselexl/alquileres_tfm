package com.portfoliojosele.alquileres_tfm.models.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.Audited.Table;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "contratos")
public class Contrato implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usamos LocalDate para fechas sin hora. 
    // @DateTimeFormat ayuda a que el HTML entienda el formato del calendario.
    @NotNull(message = "La fecha de inicio es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    @NotEmpty(message = "El estado del contrato es obligatorio")
    private String estadoContrato; // ESTADO DEL CONTRATO PENDIENTE, ENVIADO, FIRMADO

    @NotNull(message = "Debes asignar un inquilino al contrato")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquilino_id") // creo columna inquilino_id con su FK para relacionar.
    private Inquilino inquilino;

    @NotNull(message = "Debes asignar una vivienda al contrato")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vivienda_id") // creo columna vivienda_id con su FK para relacionar.
    private Vivienda vivienda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstadoContrato() {
        return estadoContrato;
    }

    public void setEstadoContrato(String estadoContrato) {
        this.estadoContrato = estadoContrato;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Vivienda getVivienda() {
        return vivienda;
    }

    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }


    
}
