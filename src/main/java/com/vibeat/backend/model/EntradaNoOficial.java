package com.vibeat.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    // Antes: EVENTO_NO_OFICIAL_ID
    @Column(name = "EVENTO_ID", nullable = false)
    private Long eventoId;

    @Column(name = "USUARIO_ID", nullable = false)
    private Long usuarioId;

    @Column(name = "TIPO_ENTRADA", nullable = false, length = 100)
    private String tipoEntrada;

    @Column(name = "NOMBRE_COMPRADOR", nullable = false, length = 100)
    private String nombreComprador;

    @Column(name = "APELLIDOS_COMPRADOR", length = 100)
    private String apellidosComprador;

    @JsonFormat(pattern = "yyyy-MM-dd")
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

    @Column(name = "REFERENCIA", length = 255)
    private String referencia;

    @Column(name = "PREMIADA", nullable = false)
    private Boolean premiada = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "FECHA_COMPRA")
    private LocalDateTime fechaCompra;

    // Antes: ESCANEADAS
    @Column(name = "ESCANEADA", nullable = false)
    private Boolean escaneada = false;

    @Column(name = "NOMBRE_PREMIO", length = 150)
    private String nombrePremio;

    public EntradaNoOficial() {
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventoId() { return eventoId; }
    public void setEventoId(Long eventoId) { this.eventoId = eventoId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getTipoEntrada() { return tipoEntrada; }
    public void setTipoEntrada(String tipoEntrada) { this.tipoEntrada = tipoEntrada; }

    public String getNombreComprador() { return nombreComprador; }
    public void setNombreComprador(String nombreComprador) { this.nombreComprador = nombreComprador; }

    public String getApellidosComprador() { return apellidosComprador; }
    public void setApellidosComprador(String apellidosComprador) { this.apellidosComprador = apellidosComprador; }

    public LocalDate getFechaNacimientoComprador() { return fechaNacimientoComprador; }
    public void setFechaNacimientoComprador(LocalDate fechaNacimientoComprador) { this.fechaNacimientoComprador = fechaNacimientoComprador; }

    public String getDniComprador() { return dniComprador; }
    public void setDniComprador(String dniComprador) { this.dniComprador = dniComprador; }

    public String getEmailComprador() { return emailComprador; }
    public void setEmailComprador(String emailComprador) { this.emailComprador = emailComprador; }

    public String getTelefonoComprador() { return telefonoComprador; }
    public void setTelefonoComprador(String telefonoComprador) { this.telefonoComprador = telefonoComprador; }

    public String getCodigoQr() { return codigoQr; }
    public void setCodigoQr(String codigoQr) { this.codigoQr = codigoQr; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public Boolean getPremiada() { return premiada; }
    public void setPremiada(Boolean premiada) { this.premiada = premiada; }

    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }

    public Boolean getEscaneada() { return escaneada; }
    public void setEscaneada(Boolean escaneada) { this.escaneada = escaneada; }

    public String getNombrePremio() { return nombrePremio; }
    public void setNombrePremio(String nombrePremio) { this.nombrePremio = nombrePremio; }
}
