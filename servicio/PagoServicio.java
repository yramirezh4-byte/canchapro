/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import modelo.Pago;
import modelo.Reserva;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class PagoServicio {

    private ArrayList<Pago> pagos;

    public PagoServicio() {
        pagos = new ArrayList<>();
    }

    public ArrayList<Pago> getPagos() {
        return pagos;
    }

    // Registrar un pago de una reserva
    public boolean registrarPago(Reserva reserva, Pago pago) {

        // Comprobar que el pago corresponde a la reserva
        if (!pago.getCodigoReserva().equals(reserva.getCodigo())) {
            System.out.println("El pago no corresponde a la reserva.");
            return false;
        }

        // Evitar pagos duplicados
        for (Pago actual : pagos) {
            if (actual.getCodigo().equals(pago.getCodigo())) {
                System.out.println("El codigo de pago ya existe.");
                return false;
            }
        }

        // No permitir pagos en reservas canceladas
        if (reserva.getEstado().equals("CANCELADA")) {
            System.out.println("No se puede pagar una reserva cancelada.");
            return false;
        }

        // Comprobar que el monto sea mayor que cero
        if (pago.getMonto() <= 0) {
            System.out.println("El monto debe ser mayor que cero.");
            return false;
        }

        // Comprobar el saldo pendiente
        if (pago.getMonto() > reserva.calcularSaldoPendiente()) {
            System.out.println("El pago supera el saldo pendiente.");
            return false;
        }

        // Registrar el pago
        pagos.add(pago);
        reserva.agregarPago(pago);

        System.out.println("Pago registrado correctamente.");
        System.out.println("Saldo pendiente: Q"
                + reserva.calcularSaldoPendiente());

        return true;
    }
}
