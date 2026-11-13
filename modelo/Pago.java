/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;

/**
 *
 * @author hurta
 */
public class Pago {

    // Atributos del pago
    private String codigo;
    private String codigoReserva;
    private LocalDateTime fechaPago;
    private double monto;
    private String metodoPago;
    private String observacion;

    // Constructor de la clase Pago
    public Pago(String codigo, String codigoReserva,
                LocalDateTime fechaPago, double monto,
                String metodoPago, String observacion) {

        this.codigo = codigo;
        this.codigoReserva = codigoReserva;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.observacion = observacion;
    }

    // Obtener y modificar el codigo
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // Obtener y modificar el codigo de reserva
    public String getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    // Obtener y modificar la fecha del pago
    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    // Obtener y modificar el monto
    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    // Obtener y modificar el metodo de pago
    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    // Obtener y modificar la observacion
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    // Mostrar los datos del pago
    public void mostrarDatos() {

        System.out.println("Codigo del pago: " + codigo);
        System.out.println("Codigo de reserva: " + codigoReserva);
        System.out.println("Fecha del pago: " + fechaPago);
        System.out.println("Monto: Q" + monto);
        System.out.println("Metodo de pago: " + metodoPago);
        System.out.println("Observacion: " + observacion);
    }
}

