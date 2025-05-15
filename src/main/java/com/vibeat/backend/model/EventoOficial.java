package com.vibeat.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EVENTOS_OFICIALES")
public class EventoOficial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "LUGAR", nullable = false)
    private String lugar;
    
    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDateTime fechaInicio;
    
    @Column(name = "FECHA_FIN", nullable = false)
    private LocalDateTime fechaFin;
    
    @Column(name = "EMPRESA_ID", nullable = false)
    private Long empresaId;
    
    @Column(name = "NUMERO_TIPOS_ENTRADA")
    private Integer numeroTiposEntrada;
    
    @Column(name = "NUMERO_ATENCION_CLIENTE")
    private String numeroAtencionCliente;
    
    @Column(name = "FECHA_CREACION", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
    public Long getEmpresaId() {
        return empresaId;
    }
    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
    public Integer getNumeroTiposEntrada() {
        return numeroTiposEntrada;
    }
    public void setNumeroTiposEntrada(Integer numeroTiposEntrada) {
        this.numeroTiposEntrada = numeroTiposEntrada;
    }
    public String getNumeroAtencionCliente() {
        return numeroAtencionCliente;
    }
    public void setNumeroAtencionCliente(String numeroAtencionCliente) {
        this.numeroAtencionCliente = numeroAtencionCliente;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
