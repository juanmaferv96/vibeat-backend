package com.vibeat.backend.model;
import com.vibeat.backend.model.TipoEntrada;
import com.vibeat.backend.util.TipoEntradaConverter;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "EVENTOS_NO_OFICIALES")
public class EventoNoOficial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "LUGAR", nullable = false)
    private String lugar;
    
    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;
    
    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDateTime fechaInicio;
    
    @Column(name = "FECHA_FIN", nullable = false)
    private LocalDateTime fechaFin;
    
    @Column(name = "USUARIO_ID", nullable = false)
    private Long usuarioId;
    
    @Column(name = "NUMERO_TIPOS_ENTRADA")
    private Integer numeroTiposEntrada;
    
    @Convert(converter = TipoEntradaConverter.class)
    @Column(name = "TIPOS_ENTRADA", columnDefinition = "json")
    private List<TipoEntrada> tiposEntrada;
    
    @Column(name = "NUMERO_ATENCION_CLIENTE")
    private String numeroAtencionCliente;
    
    @Column(name = "EMAIL_ATENCION_CLIENTE")
    private String emailAtencionCliente;
    
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
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    public Integer getNumeroTiposEntrada() {
        return numeroTiposEntrada;
    }
    public void setNumeroTiposEntrada(Integer numeroTiposEntrada) {
        this.numeroTiposEntrada = numeroTiposEntrada;
    }
    
    public List<TipoEntrada> getTiposEntrada() {
		return tiposEntrada;
	}
	public void setTiposEntrada(List<TipoEntrada> tiposEntrada) {
		this.tiposEntrada = tiposEntrada;
	}
	public String getNumeroAtencionCliente() {
        return numeroAtencionCliente;
    }
    public void setNumeroAtencionCliente(String numeroAtencionCliente) {
        this.numeroAtencionCliente = numeroAtencionCliente;
    }
    public String getEmailAtencionCliente() {
        return emailAtencionCliente;
    }

    public void setEmailAtencionCliente(String emailAtencionCliente) {
        this.emailAtencionCliente = emailAtencionCliente;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
}
