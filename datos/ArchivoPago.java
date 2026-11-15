
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Pago;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class ArchivoPago {

    private String nombreArchivo = "pagos.csv";

    // Guardar pagos
    public void guardarPagos(ArrayList<Pago> pagos) {

        try {
            PrintWriter escribir = new PrintWriter(
                    new FileWriter(nombreArchivo));

            for (Pago pago : pagos) {

                escribir.println(
                        pago.getCodigo() + ";" +
                        pago.getCodigoReserva() + ";" +
                        pago.getFechaPago() + ";" +
                        pago.getMonto() + ";" +
                        pago.getMetodoPago() + ";" +
                        pago.getObservacion()
                );
            }

            escribir.close();
            System.out.println("Pagos guardados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar pagos: "
                    + e.getMessage());
        }
    }

    // Leer pagos
    public ArrayList<Pago> leerPagos() {

        ArrayList<Pago> pagos = new ArrayList<>();

        try {
            BufferedReader leer = new BufferedReader(
                    new FileReader(nombreArchivo));

            String linea;

            while ((linea = leer.readLine()) != null) {

                try {
                    String[] datos = linea.split(";", -1);

                    if (datos.length == 6) {

                        Pago pago = new Pago(
                                datos[0],
                                datos[1],
                                LocalDateTime.parse(datos[2]),
                                Double.parseDouble(datos[3]),
                                datos[4],
                                datos[5]
                        );

                        pagos.add(pago);

                    } else {
                        System.out.println("Registro incompleto: " + linea);
                    }

                } catch (Exception e) {
                    System.out.println("Registro incorrecto: " + linea);
                }
            }

            leer.close();

        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo de pagos.");

        } catch (IOException e) {
            System.out.println("Error al leer pagos: "
                    + e.getMessage());
        }

        return pagos;
    }
}
