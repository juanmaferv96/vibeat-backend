package com.vibeat.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIOS")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "APELLIDOS")
    private String apellidos;

    @Column(name = "DNI", unique = true, nullable = false)
    private String dni;

    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "FECHA_REGISTRO", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "USER", nullable = false)
    private String user;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ES_RRPP", nullable = false)
    private int esRrpp = 0;

    @Column(name = "ES_ESCANEADOR", nullable = false)
    private int esEscaneador = 0;

    // Getters & Setters

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

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }
    // Normalizamos a MAYÚSCULAS al guardar
    public void setDni(String dni) {
        this.dni = (dni != null) ? dni.toUpperCase() : null;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }
    // Normalizamos a minúsculas al guardar
    public void setEmail(String email) {
        this.email = (email != null) ? email.toLowerCase() : null;
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
    // Se guarda tal cual lo escribe el usuario (sin normalizar)
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getEsRrpp() {
        return esRrpp;
    }
    public void setEsRrpp(int esRrpp) {
        this.esRrpp = esRrpp;
    }

    public int getEsEscaneador() {
        return esEscaneador;
    }
    public void setEsEscaneador(int esEscaneador) {
        this.esEscaneador = esEscaneador;
    }

    // Opcionalmente, si quieres reforzar la normalización también en updates de JPA:
    @PrePersist
    @PreUpdate
    private void normalizeFields() {
        if (this.dni != null) this.dni = this.dni.toUpperCase();
        if (this.email != null) this.email = this.email.toLowerCase();
        // 'user' se mantiene tal y como lo introduce el usuario
    }
}
