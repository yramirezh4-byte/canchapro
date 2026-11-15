/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;


/**
 *
 * @author hurta
 */
public class TarifaServicio {

    // Obtener el precio por hora
    public double obtenerPrecio(LocalDate fecha, LocalTime hora) {

        DayOfWeek dia = fecha.getDayOfWeek();

        // Sabado y domingo
        if (dia == DayOfWeek.SATURDAY
                || dia == DayOfWeek.SUNDAY) {
            return 250;
        }

        // Lunes a viernes, horario de dia
        if (hora.isBefore(LocalTime.of(17, 0))) {
            return 150;
        }

        // Lunes a viernes, horario de noche
        return 225;
    }

    // Calcular el total de una reserva
    public double calcularTotal(LocalDate fecha,
            LocalTime horaInicio, LocalTime horaFin) {

        if (fecha == null || horaInicio == null
                || horaFin == null) {
            return -1;
        }

        if (horaInicio.isBefore(LocalTime.of(8, 0))
                || horaFin.isAfter(LocalTime.of(23, 0))
                || !horaInicio.isBefore(horaFin)) {
            return -1;
        }

        double total = 0;
        LocalTime horaActual = horaInicio;

        // Calcular por minutos para respetar cambios de tarifa
        while (horaActual.isBefore(horaFin)) {

            LocalTime siguiente = horaActual.plusMinutes(1);

            double precioHora = obtenerPrecio(
                    fecha, horaActual);

            total = total + precioHora / 60.0;

            horaActual = siguiente;
        }

        // Redondear a dos decimales
        return Math.round(total * 100.0) / 100.0;
    }
}

