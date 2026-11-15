
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Tarifa;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class ArchivoTarifa {

    private String nombreArchivo = "tarifas.csv";

    // Guardar tarifas
    public void guardarTarifas(ArrayList<Tarifa> tarifas) {

        try {
            PrintWriter escribir = new PrintWriter(
                    new FileWriter(nombreArchivo));

            for (Tarifa tarifa : tarifas) {

                escribir.println(
                        tarifa.getCodigo() + ";" +
                        tarifa.getDescripcion() + ";" +
                        tarifa.getTipoDia() + ";" +
                        tarifa.getHoraInicio() + ";" +
                        tarifa.getHoraFin() + ";" +
                        tarifa.getPrecioHora()
                );
            }

            escribir.close();
            System.out.println("Tarifas guardadas correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar tarifas: "
                    + e.getMessage());
        }
    }

    // Leer tarifas
    public ArrayList<Tarifa> leerTarifas() {

        ArrayList<Tarifa> tarifas = new ArrayList<>();

        try {
            BufferedReader leer = new BufferedReader(
                    new FileReader(nombreArchivo));

            String linea;

            while ((linea = leer.readLine()) != null) {

                try {
                    String[] datos = linea.split(";", -1);

                    if (datos.length == 6) {

                        Tarifa tarifa = new Tarifa(
                                datos[0],
                                datos[1],
                                datos[2],
                                LocalTime.parse(datos[3]),
                                LocalTime.parse(datos[4]),
                                Double.parseDouble(datos[5])
                        );

                        tarifas.add(tarifa);

                    } else {
                        System.out.println("Registro incompleto: " + linea);
                    }

                } catch (Exception e) {
                    System.out.println("Registro incorrecto: " + linea);
                }
            }

            leer.close();

        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo de tarifas.");

        } catch (IOException e) {
            System.out.println("Error al leer tarifas: "
                    + e.getMessage());
        }

        return tarifas;
    }
}
