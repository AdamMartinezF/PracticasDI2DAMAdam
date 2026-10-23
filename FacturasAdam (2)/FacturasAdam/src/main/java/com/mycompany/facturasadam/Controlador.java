package com.mycompany.facturasadam;

import java.awt.event.ActionEvent;
import java.time.DateTimeException;
import java.time.LocalDate;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author adami
 */
public class Controlador implements ChangeListener {

    private final VistaFacturas vista;
    private final GestorFacturas modelo;

    public Controlador(VistaFacturas vista, GestorFacturas modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // La Vista notifica acciones -> el Controlador las atiende.
        this.vista.addAnadirListener(this::botonAñadir);
        this.vista.addEditarListener(this::botonEditar);
        this.vista.addEliminarListener(this::botonEliminar);

        // El Modelo notifica cambios -> el Controlador refresca la Vista.
        this.modelo.addChangeListener(this);
    }

    /** Lo llama el Modelo cada vez que cambia la lista de facturas. */
    @Override
    public void stateChanged(ChangeEvent e) {
        vista.refrescarLista(modelo.getFacturas());
    }

    private void botonAñadir(ActionEvent evt) {
        String asunto = vista.getAsunto();

        // 1. Validación de Asunto (entre 1 y 10 caracteres)
        if (asunto.isEmpty() || asunto.length() > 10) {
            vista.mostrarError("El asunto debe tener entre 1 y 10 caractéres");
            return;
        }

        // 2. Validación de Fecha
        LocalDate fecha;
        try {
            fecha = LocalDate.of(vista.getAño(), vista.getMes(), vista.getDia());
        } catch (DateTimeException ex) {
            vista.mostrarError("La fecha no es válida");
            return;
        }
        if (fecha.isBefore(LocalDate.of(2000, 1, 1))) {
            vista.mostrarError("La fecha no puede ser inferior al 1 de enero de 2000");
            return;
        }

        // 3. Validación de Cantidad
        double cantidad;
        try {
            cantidad = Double.parseDouble(vista.getCantidadTexto());
        } catch (NumberFormatException ex) {
            vista.mostrarError("La cantidad debe ser un número decimal");
            return;
        }
        if (cantidad < 0) {
            vista.mostrarError("La cantidad debe ser mayor o igual que 0");
            return;
        }

        String tipo = vista.getTipo();
        int seleccion = vista.getIndiceSeleccionado();
        if (seleccion != -1) {
            // Modo actualizar facturas.
            Factura facturaOriginal = modelo.getFacturas().get(seleccion);
            boolean exito = modelo.actualizarFactura(facturaOriginal, asunto, fecha, cantidad, tipo);
            if (exito) {
                vista.mostrarExito("Factura actualizada correctamente");
                vista.limpiarSeleccion();
            } else {
                vista.mostrarError("Ya existe otra factura con ese asunto");
            }
        } else {
            // Modo añadir facturas.
            Factura nuevaFactura = new Factura(asunto, fecha, cantidad, tipo);
            boolean exito = modelo.añadirFactura(nuevaFactura);
            if (exito) {
                vista.mostrarExito("Factura añadida correctamente");
            } else {
                vista.mostrarError("No se puede añadir una factura si ya existe otra con el mismo asunto");
            }
        }
    }

    private void botonEditar(ActionEvent evt) {
        int seleccion = vista.getIndiceSeleccionado();
        if (seleccion == -1) {
            vista.mostrarError("Debe seleccionar una factura de la lista para editar");
            return;
        }

        Factura f = modelo.getFacturas().get(seleccion);
        vista.cargarFactura(f);
        vista.mostrarExito("Factura cargada para edición");
    }

    private void botonEliminar(ActionEvent evt) {
        int seleccion = vista.getIndiceSeleccionado();
        if (seleccion == -1) {
            vista.mostrarError("Debe seleccionar una factura de la lista para eliminar");
            return;
        }

        Factura aEliminar = modelo.getFacturas().get(seleccion);
        boolean exito = modelo.eliminarFactura(aEliminar);
        if (exito) {
            vista.mostrarExito("Factura eliminada correctamente");
        } else {
            vista.mostrarError("No se pudo eliminar la factura");
        }
    }
}
