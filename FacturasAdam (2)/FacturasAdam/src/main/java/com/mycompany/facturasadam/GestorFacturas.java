package com.mycompany.facturasadam;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * @author adami
 */
public class GestorFacturas {
    private final ArrayList<Factura> facturas = new ArrayList<Factura>();
    private final ArrayList<ChangeListener> oyentes = new ArrayList<ChangeListener>();

    public void addChangeListener(ChangeListener l) {
        oyentes.add(l);
    }

    private void notificarCambio() {
        ChangeEvent evento = new ChangeEvent(this);
        for (ChangeListener l : oyentes) {
            l.stateChanged(evento);
        }
    }

    public ArrayList<Factura> getFacturas() {
        return facturas;
    }
    public boolean añadirFactura(Factura f) {
        for (Factura ftemp : facturas) {
            if (ftemp.getAsunto().equalsIgnoreCase(f.getAsunto())) {
                return false;
            }
        }
        facturas.add(f);
        notificarCambio();
        return true;
    }
    public boolean eliminarFactura(Factura f) {
        for (Factura ftemp : facturas) {
            if (ftemp.getAsunto().equalsIgnoreCase(f.getAsunto())) {
                facturas.remove(ftemp);
                notificarCambio();
                return true;
            }
        }
        return false;
    }
    public boolean actualizarFactura(Factura original, String asunto, LocalDate fecha, double cantidad, String tipo) {
        for (Factura f : facturas) {
            if (f != original && f.getAsunto().equalsIgnoreCase(asunto)) {
                return false; // Ya existe otra factura con ese nuevo asunto
            }
        }
        original.setAsunto(asunto);
        original.setFecha(fecha);
        original.setCantidad(cantidad);
        original.setTipo(tipo);
        notificarCambio();
        return true;
    }
}
