/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package canchaspro;

import modelo.Cliente;
import datos.ArchivoCliente;
import java.time.LocalDate;
import java.util.ArrayList;
import modelo.Cancha;
import datos.ArchivoCancha;
import java.time.LocalTime;
import modelo.Tarifa;
import datos.ArchivoTarifa;
import modelo.Reserva;
import servicio.ReservaServicio;
import datos.ArchivoReserva;
import datos.ArchivoPago;
import modelo.Pago;
import servicio.PagoServicio;
import java.time.LocalDateTime;
import servicio.TarifaServicio;
/**
 *
 * @author hurta
 */
public class CanchasPRO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("SISTEMA CANCHAPRO");
        System.out.println("Complejo Deportivo La 10");
        System.out.println("---------------------------");

        // Crear una lista de clientes
        ArrayList<Cliente> clientes = new ArrayList<>();

        // Crear un cliente de prueba
        Cliente cliente1 = new Cliente(
                "C001",
                "Andrea",
                "12345678",
                "andrea@gmail.com",
                LocalDate.now()
        );

        // Agregar el cliente a la lista
        clientes.add(cliente1);

        // Crear objeto para manejar el archivo
        ArchivoCliente archivo = new ArchivoCliente();

        // Guardar los clientes en el archivo
        archivo.guardarClientes(clientes);

        // Leer los clientes guardados
        ArrayList<Cliente> listaClientes = archivo.leerClientes();

        // Mostrar los clientes recuperados
        System.out.println("CLIENTES REGISTRADOS");
        System.out.println("---------------------------");

        for (Cliente cliente : listaClientes) {

            cliente.mostrarDatos();
            System.out.println("---------------------------");
        
        // Registrar las canchas del complejo deportivo
        ArrayList<Cancha> canchas = new ArrayList<>();

        Cancha cancha1 = new Cancha(
                "C01", "La Bombonera", "Futbol 5",
                10, 150, "DISPONIBLE",
                LocalTime.of(8, 0), LocalTime.of(23, 0));

        Cancha cancha2 = new Cancha(
                "C02", "El Maracana", "Futbol 5",
                10, 150, "DISPONIBLE",
                LocalTime.of(8, 0), LocalTime.of(23, 0));

        Cancha cancha3 = new Cancha(
                "C03", "El Monumental", "Futbol 7",
                14, 150, "DISPONIBLE",
                LocalTime.of(8, 0), LocalTime.of(23, 0));

        canchas.add(cancha1);
        canchas.add(cancha2);
        canchas.add(cancha3);

        ArchivoCancha archivoCancha = new ArchivoCancha();
        archivoCancha.guardarCanchas(canchas);

        System.out.println("CANCHAS REGISTRADAS");

        ArrayList<Cancha> listaCanchas =
                archivoCancha.leerCanchas();

        for (Cancha cancha : listaCanchas) {
            cancha.mostrarDatos();
            System.out.println("---------------------------");
            
        // Crear las tarifas del complejo
        ArrayList<Tarifa> tarifas = new ArrayList<>();

        Tarifa tarifa1 = new Tarifa(
                "T01", "Horario de dia", "ENTRE_SEMANA",
                LocalTime.of(8, 0),
                LocalTime.of(17, 0), 150);

        Tarifa tarifa2 = new Tarifa(
                "T02", "Horario de noche", "ENTRE_SEMANA",
                LocalTime.of(17, 0),
                LocalTime.of(23, 0), 225);

        Tarifa tarifa3 = new Tarifa(
                "T03", "Fin de semana", "FIN_SEMANA",
                LocalTime.of(8, 0),
                LocalTime.of(23, 0), 250);

        tarifas.add(tarifa1);
        tarifas.add(tarifa2);
        tarifas.add(tarifa3);

        ArchivoTarifa archivoTarifa = new ArchivoTarifa();

        archivoTarifa.guardarTarifas(tarifas);

        System.out.println("TARIFAS REGISTRADAS");
        System.out.println("---------------------------");

        ArrayList<Tarifa> listaTarifas =
                archivoTarifa.leerTarifas();

        for (Tarifa tarifa : listaTarifas) {
            tarifa.mostrarDatos();
            System.out.println("---------------------------");
            
        // PRUEBA DE RESERVAS
        System.out.println("PRUEBA DE RESERVAS");
        System.out.println("---------------------------");

        ReservaServicio reservaServicio = new ReservaServicio();

        // Primera reserva: de 9 a 11 de la mañana
        Reserva reserva1 = new Reserva(
                "R001",
                cliente1,
                cancha1,
                LocalDate.of(2026, 11, 14),
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                500,
                "CONFIRMADA"
        );

        reservaServicio.registrarReserva(reserva1);

        // Segunda reserva: horario ocupado
        Reserva reserva2 = new Reserva(
                "R002",
                cliente1,
                cancha1,
                LocalDate.of(2026, 11, 14),
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                500,
                "PENDIENTE"
        );

        reservaServicio.registrarReserva(reserva2);

        // Tercera reserva: horario disponible
        Reserva reserva3 = new Reserva(
                "R003",
                cliente1,
                cancha1,
                LocalDate.of(2026, 11, 14),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                250,
                "CONFIRMADA"
        );

        reservaServicio.registrarReserva(reserva3);

        System.out.println("---------------------------");
        System.out.println("RESERVAS REGISTRADAS");

        for (Reserva reserva : reservaServicio.getReservas()) {
            reserva.mostrarDatos();
            System.out.println("---------------------------");
            
        // Guardar las reservas aceptadas
        ArchivoReserva archivoReserva = new ArchivoReserva();

        archivoReserva.guardarReservas(
                reservaServicio.getReservas());

        // Recuperar las reservas del archivo
        ArrayList<Reserva> reservasGuardadas =
                archivoReserva.leerReservas(
                        listaClientes, listaCanchas);
        
System.out.println("RESERVAS RECUPERADAS DEL ARCHIVO");

for (Reserva reservaGuardada : reservasGuardadas) {

    reservaGuardada.mostrarDatos();
    System.out.println("---------------------------");

}

        // Prueba de pagos
        PagoServicio pagoServicio = new PagoServicio();

        Pago pago1 = new Pago(
                "P001",
                "R001",
                LocalDateTime.now(),
                200,
                "EFECTIVO",
                "Anticipo de reserva"
        );

        pagoServicio.registrarPago(reserva1, pago1);

        Pago pago2 = new Pago(
                "P002",
                "R001",
                LocalDateTime.now(),
                400,
                "EFECTIVO",
                "Segundo pago"
        );

        // Este pago debe rechazarse porque supera el saldo
        pagoServicio.registrarPago(reserva1, pago2);

        // Guardar solamente los pagos aceptados
        ArchivoPago archivoPago = new ArchivoPago();
        archivoPago.guardarPagos(pagoServicio.getPagos());
        
        // Probar el calculo automatico de tarifas
TarifaServicio tarifaServicio = new TarifaServicio();

double totalReserva = tarifaServicio.calcularTotal(
        LocalDate.of(2026, 11, 13),
        LocalTime.of(16, 0),
        LocalTime.of(18, 0)
);

System.out.println("PRUEBA DE TARIFAS");
System.out.println("Total de reserva: Q" + totalReserva);

        }

        }

        }

        }

    }

}
