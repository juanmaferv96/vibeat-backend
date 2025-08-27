package com.vibeat.backend.model;

public class TipoEntrada {
    private String nombre;
    private double precio;
    private String tipoSorteo;
    private String descripcion;
    private int totalEntradas;
    private int entradasDisponibles;
    private int numeroPremiadas;
    // CAMBIO: premiosEntregados -> premiosDisponibles
    private int premiosDisponibles;
    private String nombrePremio;

    public TipoEntrada() {}

    public TipoEntrada(
        String nombre,
        double precio,
        String descripcion,
        int numeroPremiadas,
        int totalEntradas,
        String tipoSorteo,
        String nombrePremio,
        int entradasDisponibles,
        int premiosDisponibles
    ) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.numeroPremiadas = numeroPremiadas;
        this.totalEntradas = totalEntradas;
        this.tipoSorteo = tipoSorteo;
        this.nombrePremio = nombrePremio;

        // Mantener la semántica "disponibles" para ambos contadores
        this.entradasDisponibles = totalEntradas;
        // Inicializa premiosDisponibles con el número de premiadas
        this.premiosDisponibles = Math.max(0, numeroPremiadas);
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
        // mantener comportamiento actual: al setear total, entradasDisponibles = total
        this.entradasDisponibles = totalEntradas;
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
        // si cambia numeroPremiadas y aún no se ha configurado explícitamente, lo normal es alinear premiosDisponibles
        if (this.premiosDisponibles < 0) {
            this.premiosDisponibles = Math.max(0, numeroPremiadas);
        }
    }

    // CAMBIO: nuevo nombre del campo y getters/setters
    public int getPremiosDisponibles() {
        return premiosDisponibles;
    }
    public void setPremiosDisponibles(int premiosDisponibles) {
        this.premiosDisponibles = premiosDisponibles;
    }

    public String getNombrePremio() {
        return nombrePremio;
    }
    public void setNombrePremio(String nombrePremio) {
        this.nombrePremio = nombrePremio;
    }
}
