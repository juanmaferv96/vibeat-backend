package com.vibeat.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EMPRESAS")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "CIF", unique = true, nullable = false)
    private String cif;
    
    @Column(name = "DIRECCION")
    private String direccion;
    
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;
    
    @Column(name = "TELEFONO")
    private String telefono;
    
    @Column(name = "MOVIL")
    private String movil;
    
    @Column(name = "SITIO_WEB")
    private String sitioWeb;
    
    @Column(name = "FECHA_REGISTRO", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;
    
    @Column(name = "USER", nullable = false)
    private String user;
    
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    
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
    public String getCif() {
        return cif;
    }
    public void setCif(String cif) {
        this.cif = cif;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getMovil() {
        return movil;
    }
    public void setMovil(String movil) {
        this.movil = movil;
    }
    public String getSitioWeb() {
        return sitioWeb;
    }
    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
