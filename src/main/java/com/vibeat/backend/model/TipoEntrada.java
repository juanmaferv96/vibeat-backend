package com.vibeat.backend.model;

public class TipoEntrada {
    private String nombre;
    private double precio;
    private String descripcion;
    private int numeroPremiadas;
    private int totalEntradas;
    private String tipoSorteo;

    public TipoEntrada() {}

    public TipoEntrada(String nombre, double precio, String descripcion, int numeroPremiadas, int totalEntradas, String tipoSorteo) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.numeroPremiadas = numeroPremiadas;
        this.totalEntradas = totalEntradas;
        this.tipoSorteo = tipoSorteo;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroPremiadas() {
        return numeroPremiadas;
    }

    public void setNumeroPremiadas(int numeroPremiadas) {
        this.numeroPremiadas = numeroPremiadas;
    }

    public int getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(int totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public String getTipoSorteo() {
        return tipoSorteo;
    }

    public void setTipoSorteo(String tipoSorteo) {
        this.tipoSorteo = tipoSorteo;
    }
}
