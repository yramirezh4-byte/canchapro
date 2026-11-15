/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Cliente;
import modelo.Cancha;
import modelo.Reserva;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class ArchivoReserva {

    private String nombreArchivo = "reservas.csv";

    // Guardar todas las reservas
    public void guardarReservas(ArrayList<Reserva> reservas) {

        try {
            PrintWriter escribir = new PrintWriter(
                    new FileWriter(nombreArchivo));

            for (Reserva reserva : reservas) {

                escribir.println(
                        reserva.getCodigo() + ";" +
                        reserva.getCliente().getCodigo() + ";" +
                        reserva.getCancha().getCodigo() + ";" +
                        reserva.getFecha() + ";" +
                        reserva.getHoraInicio() + ";" +
                        reserva.getHoraFin() + ";" +
                        reserva.getTotal() + ";" +
                        reserva.getEstado()
                );
            }

            escribir.close();
            System.out.println("Reservas guardadas correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar reservas: "
                    + e.getMessage());
        }
    }

    // Leer reservas y relacionarlas con clientes y canchas
    public ArrayList<Reserva> leerReservas(
            ArrayList<Cliente> clientes,
            ArrayList<Cancha> canchas) {

        ArrayList<Reserva> reservas = new ArrayList<>();

        try {
            BufferedReader leer = new BufferedReader(
                    new FileReader(nombreArchivo));

            String linea;

            while ((linea = leer.readLine()) != null) {

                try {
                    String[] datos = linea.split(";", -1);

                    if (datos.length != 8) {
                        System.out.println("Registro incompleto: " + linea);
                        continue;
                    }

                    Cliente clienteEncontrado = null;
                    Cancha canchaEncontrada = null;

                    // Buscar el cliente de la reserva
                    for (Cliente cliente : clientes) {
                        if (cliente.getCodigo().equals(datos[1])) {
                            clienteEncontrado = cliente;
                            break;
                        }
                    }

                    // Buscar la cancha de la reserva
                    for (Cancha cancha : canchas) {
                        if (cancha.getCodigo().equals(datos[2])) {
                            canchaEncontrada = cancha;
                            break;
                        }
                    }

                    if (clienteEncontrado != null
                            && canchaEncontrada != null) {

                        Reserva reserva = new Reserva(
                                datos[0],
                                clienteEncontrado,
                                canchaEncontrada,
                                LocalDate.parse(datos[3]),
                                LocalTime.parse(datos[4]),
                                LocalTime.parse(datos[5]),
                                Double.parseDouble(datos[6]),
                                datos[7]
                        );

                        reservas.add(reserva);

                    } else {
                        System.out.println(
                                "Cliente o cancha no encontrada: " + linea);
                    }

                } catch (Exception e) {
                    System.out.println("Registro incorrecto: " + linea);
                }
            }

            leer.close();

        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo de reservas.");

        } catch (IOException e) {
            System.out.println("Error al leer reservas: "
                    + e.getMessage());
        }

        return reservas;
    }
}

