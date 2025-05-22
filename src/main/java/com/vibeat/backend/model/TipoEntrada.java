package com.vibeat.backend.model;

public class TipoEntrada {
    private String nombre;
    private double precio;
    private String tipoSorteo;
    private String descripcion;
    private int totalEntradas;
    private int entradasDisponibles;
    private int numeroPremiadas;
    private int premiosEntregados;
    private String nombrePremio;

    public TipoEntrada() {}
    
    public TipoEntrada(String nombre, double precio, String descripcion, int numeroPremiadas, int totalEntradas, String tipoSorteo, String nombrePremio, int entradasDisponibles, int premiosEntregados) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.numeroPremiadas = numeroPremiadas;
        this.totalEntradas = totalEntradas;
        this.tipoSorteo = tipoSorteo;
        this.nombrePremio = nombrePremio;
        this.entradasDisponibles = this.totalEntradas; 
        this.premiosEntregados = 0;                     
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipoSorteo() {
        return tipoSorteo;
    }

    public void setTipoSorteo(String tipoSorteo) {
        this.tipoSorteo = tipoSorteo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(int totalEntradas) {
        this.totalEntradas = totalEntradas;
        this.entradasDisponibles = totalEntradas; // default logic
    }

    public int getEntradasDisponibles() {
        return entradasDisponibles;
    }

    public void setEntradasDisponibles(int entradasDisponibles) {
        this.entradasDisponibles = entradasDisponibles;
    }

    public int getNumeroPremiadas() {
        return numeroPremiadas;
    }

    public void setNumeroPremiadas(int numeroPremiadas) {
        this.numeroPremiadas = numeroPremiadas;
    }

    public int getPremiosEntregados() {
        return premiosEntregados;
    }

    public void setPremiosEntregados(int premiosEntregados) {
        this.premiosEntregados = premiosEntregados;
    }

    public String getNombrePremio() {
        return nombrePremio;
    }

    public void setNombrePremio(String nombrePremio) {
        this.nombrePremio = nombrePremio;
    }
}
