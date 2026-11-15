/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import modelo.Reserva;
import modelo.Cancha;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class ReservaServicio {

    // Lista de reservas del sistema
    private ArrayList<Reserva> reservas;

    // Constructor
    public ReservaServicio() {
        reservas = new ArrayList<>();
    }

    // Consultar las reservas
    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    // Comprobar si una cancha esta disponible
    public boolean verificarDisponibilidad(
            Cancha cancha, LocalDate fecha,
            LocalTime horaInicio, LocalTime horaFin) {

        // Comprobar el estado de la cancha
        if (!cancha.getEstado().equals("DISPONIBLE")) {
            return false;
        }

        // Comprobar que las horas sean correctas
        if (!horaInicio.isBefore(horaFin)) {
            return false;
        }

        // Comprobar el horario de atencion
        if (horaInicio.isBefore(cancha.getHoraApertura())
                || horaFin.isAfter(cancha.getHoraCierre())) {
            return false;
        }

        // Revisar las reservas existentes
        for (Reserva reserva : reservas) {

            if (reserva.getCancha().getCodigo()
                    .equals(cancha.getCodigo())
                    && reserva.getFecha().equals(fecha)
                    && !reserva.getEstado().equals("CANCELADA")) {

                // Comprobar si los horarios se cruzan
                if (horaInicio.isBefore(reserva.getHoraFin())
                        && horaFin.isAfter(reserva.getHoraInicio())) {

                    return false;
                }
            }
        }

        return true;
    }

    // Registrar una reserva
    public boolean registrarReserva(Reserva reserva) {

        // Comprobar si el codigo ya existe
        for (Reserva actual : reservas) {
            if (actual.getCodigo().equals(reserva.getCodigo())) {
                System.out.println("El codigo de reserva ya existe.");
                return false;
            }
        }

        boolean disponible = verificarDisponibilidad(
                reserva.getCancha(),
                reserva.getFecha(),
                reserva.getHoraInicio(),
                reserva.getHoraFin()
        );

        if (disponible) {
            reservas.add(reserva);
            System.out.println("Reserva registrada correctamente.");
            return true;
        } else {
            System.out.println("La cancha no esta disponible.");
            return false;
        }
    }

    // Cancelar una reserva sin eliminarla
    public boolean cancelarReserva(String codigo) {

        for (Reserva reserva : reservas) {

            if (reserva.getCodigo().equals(codigo)) {

                if (reserva.getEstado().equals("CANCELADA")) {
                    return false;
                }

                reserva.setEstado("CANCELADA");
                System.out.println("Reserva cancelada correctamente.");
                return true;
            }
        }

        System.out.println("No se encontro la reserva.");
        return false;
    }
}
