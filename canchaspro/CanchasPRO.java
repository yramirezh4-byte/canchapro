
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
        }

        }

    }

}
