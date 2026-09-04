package com.portfoliojosele.alquileres_tfm.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "fianzas")
public class Fianza implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El importe de la fianza es obligatorio")
    private Double importe;

    @NotEmpty(message = "El estado del pago es obligatorio")
    private String estadoPago; // PENDIENTE, ABONADA

    @NotEmpty(message = "El estado legal en la administración es obligatorio")
    private String estadoLegal; // NO_DEPOSITADA, DEPOSITADA, SOLICITADA_DEVOLUCION, DEVUELTA

    @NotNull(message = "La fianza debe asociarse a un contrato")
    @OneToOne(fetch = FetchType.LAZY)
    // control de fianza de una para un solo contrato.
    @JoinColumn(name = "contrato_id", unique = true) 
    private Contrato contrato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getEstadoLegal() {
        return estadoLegal;
    }

    public void setEstadoLegal(String estadoLegal) {
        this.estadoLegal = estadoLegal;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    
}
