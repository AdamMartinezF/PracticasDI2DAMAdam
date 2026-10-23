package com.mycompany.facturasadam;
import java.time.LocalDate;
public class Factura {
    private String asunto;
    private LocalDate fecha;
    private double Cantidad;
    private String tipo;
    public Factura (String asunto, LocalDate fecha, double cantidad, String tipo){
        this.asunto = asunto;
        this.fecha = fecha;
        this.Cantidad = cantidad;
        this.tipo = tipo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double Cantidad) {
        this.Cantidad = Cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString(){
        return asunto + ":" + tipo + ":" + Cantidad + ":" + fecha.toString();
    }
}
