
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import modelo.Cliente;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class ArchivoCliente {

    // Nombre del archivo donde se guardaran los clientes
    private String nombreArchivo = "clientes.csv";

    // Guardar la lista de clientes en el archivo
    public void guardarClientes(ArrayList<Cliente> clientes) {

        try {
            FileWriter archivo = new FileWriter(nombreArchivo);
            PrintWriter escribir = new PrintWriter(archivo);

            for (Cliente cliente : clientes) {

                escribir.println(
                        cliente.getCodigo() + ";" +
                        cliente.getNombre() + ";" +
                        cliente.getTelefono() + ";" +
                        cliente.getCorreo() + ";" +
                        cliente.getFechaRegistro()
                );
            }

            escribir.close();

            System.out.println("Clientes guardados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar clientes: "
                    + e.getMessage());
        }
    }

    // Leer los clientes guardados en el archivo
    public ArrayList<Cliente> leerClientes() {

        ArrayList<Cliente> clientes = new ArrayList<>();

        try {
            FileReader archivo = new FileReader(nombreArchivo);
            BufferedReader leer = new BufferedReader(archivo);

            String linea;

            while ((linea = leer.readLine()) != null) {

                try {
                    String[] datos = linea.split(";", -1);

                    if (datos.length == 5) {

                        Cliente cliente = new Cliente(
                                datos[0],
                                datos[1],
                                datos[2],
                                datos[3],
                                LocalDate.parse(datos[4])
                        );

                        clientes.add(cliente);
                    } else {
                        System.out.println("Registro incompleto: " + linea);
                    }

                } catch (Exception e) {
                    System.out.println("Registro incorrecto: " + linea);
                }
            }

            leer.close();

        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo de clientes.");
        } catch (IOException e) {
            System.out.println("Error al leer clientes: "
                    + e.getMessage());
        }

        return clientes;
    }
}

