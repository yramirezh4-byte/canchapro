/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Cancha;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class ArchivoCancha {

    // Nombre del archivo
    private String nombreArchivo = "canchas.csv";

    // Guardar las canchas
    public void guardarCanchas(ArrayList<Cancha> canchas) {

        try {
            FileWriter archivo = new FileWriter(nombreArchivo);
            PrintWriter escribir = new PrintWriter(archivo);

            for (Cancha cancha : canchas) {

                escribir.println(
                        cancha.getCodigo() + ";" +
                        cancha.getNombre() + ";" +
                        cancha.getTipo() + ";" +
                        cancha.getCapacidad() + ";" +
                        cancha.getPrecioBase() + ";" +
                        cancha.getEstado() + ";" +
                        cancha.getHoraApertura() + ";" +
                        cancha.getHoraCierre()
                );
            }

            escribir.close();

            System.out.println("Canchas guardadas correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar canchas: "
                    + e.getMessage());
        }
    }

    // Leer las canchas guardadas
    public ArrayList<Cancha> leerCanchas() {

        ArrayList<Cancha> canchas = new ArrayList<>();

        try {
            FileReader archivo = new FileReader(nombreArchivo);
            BufferedReader leer = new BufferedReader(archivo);

            String linea;

            while ((linea = leer.readLine()) != null) {

                try {
                    String[] datos = linea.split(";", -1);

                    if (datos.length == 8) {

                        Cancha cancha = new Cancha(
                                datos[0],
                                datos[1],
                                datos[2],
                                Integer.parseInt(datos[3]),
                                Double.parseDouble(datos[4]),
                                datos[5],
                                LocalTime.parse(datos[6]),
                                LocalTime.parse(datos[7])
                        );

                        canchas.add(cancha);

                    } else {
                        System.out.println("Registro incompleto: " + linea);
                    }

                } catch (Exception e) {
                    System.out.println("Registro incorrecto: " + linea);
                }
            }

            leer.close();

        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo de canchas.");

        } catch (IOException e) {
            System.out.println("Error al leer canchas: "
                    + e.getMessage());
        }

        return canchas;
    }
}
