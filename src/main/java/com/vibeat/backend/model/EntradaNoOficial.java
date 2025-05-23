package com.vibeat.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ENTRADAS_NO_OFICIALES")
public class EntradaNoOficial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "EVENTO_NO_OFICIAL_ID", nullable = false)
    private Long eventoNoOficialId;
    
    @Column(name = "TIPO_ENTRADA", nullable = false)
    private String tipoEntrada;
    
    @Column(name = "REFERENCIA")
    private String referencia;
    
    @Column(name = "USUARIO_ID", nullable = false)
    private Long usuarioId;
    
    @Column(name = "NOMBRE_COMPRADOR", nullable = false, length = 100)
    private String nombreComprador;
    
    @Column(name = "APELLIDOS_COMPRADOR", length = 100)
    private String apellidosComprador;
    
    @Column(name = "FECHA_NACIMIENTO_COMPRADOR")
    private LocalDate fechaNacimientoComprador;
    
    @Column(name = "DNI_COMPRADOR", nullable = false, length = 20)
    private String dniComprador;
    
    @Column(name = "EMAIL_COMPRADOR", nullable = false, length = 100)
    private String emailComprador;
    
    @Column(name = "TELEFONO_COMPRADOR", length = 20)
    private String telefonoComprador;
    
    @Column(name = "CODIGO_QR", nullable = false, unique = true, length = 255)
    private String codigoQr;
    
    @Column(name = "PREMIADA", nullable = false)
    private Boolean premiada;
    
    @Column(name = "FECHA_COMPRA")
    private LocalDateTime fechaCompra;
    
    @Column(name = "ESCANEADAS", nullable = false)
    private int escaneadas = 0;

    @Column(name = "NOMBRE_PREMIO")
    private String nombrePremio;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getEventoNoOficialId() {
        return eventoNoOficialId;
    }
    public void setEventoNoOficialId(Long eventoNoOficialId) {
        this.eventoNoOficialId = eventoNoOficialId;
    }
    public String getTipoEntrada() {
        return tipoEntrada;
    }
    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }
    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    public String getNombreComprador() {
        return nombreComprador;
    }
    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }
    public String getApellidosComprador() {
        return apellidosComprador;
    }
    public void setApellidosComprador(String apellidosComprador) {
        this.apellidosComprador = apellidosComprador;
    }
    public LocalDate getFechaNacimientoComprador() {
        return fechaNacimientoComprador;
    }
    public void setFechaNacimientoComprador(LocalDate fechaNacimientoComprador) {
        this.fechaNacimientoComprador = fechaNacimientoComprador;
    }
    public String getDniComprador() {
        return dniComprador;
    }
    public void setDniComprador(String dniComprador) {
        this.dniComprador = dniComprador;
    }
    public String getEmailComprador() {
        return emailComprador;
    }
    public void setEmailComprador(String emailComprador) {
        this.emailComprador = emailComprador;
    }
    public String getTelefonoComprador() {
        return telefonoComprador;
    }
    public void setTelefonoComprador(String telefonoComprador) {
        this.telefonoComprador = telefonoComprador;
    }
    public String getCodigoQr() {
        return codigoQr;
    }
    public void setCodigoQr(String codigoQr) {
        this.codigoQr = codigoQr;
    }
    public Boolean getPremiada() {
        return premiada;
    }
    public void setPremiada(Boolean premiada) {
        this.premiada = premiada;
    }
    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }
    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    public int getEscaneadas() {
        return escaneadas;
    }
    public void setEscaneadas(int escaneadas) {
        this.escaneadas = escaneadas;
    }
    public String getNombrePremio() {
        return nombrePremio;
    }
    public void setNombrePremio(String nombrePremio) {
        this.nombrePremio = nombrePremio;
    }
}
