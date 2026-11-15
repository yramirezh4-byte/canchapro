/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import modelo.Cliente;
import datos.ArchivoCliente;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class ClienteServicio {

    private ArrayList<Cliente> clientes;
    private ArchivoCliente archivo;

    public ClienteServicio() {
        archivo = new ArchivoCliente();
        clientes = archivo.leerClientes();
    }

    // Obtener la lista de clientes
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    // Buscar un cliente por codigo
    public Cliente buscarCliente(String codigo) {

        for (Cliente cliente : clientes) {

            if (cliente.getCodigo().equalsIgnoreCase(codigo)) {
                return cliente;
            }
        }

        return null;
    }

    // Registrar un nuevo cliente
    public boolean registrarCliente(Cliente cliente) {

        if (cliente == null
                || cliente.getCodigo() == null
                || cliente.getCodigo().trim().isEmpty()
                || cliente.getNombre() == null
                || cliente.getNombre().trim().isEmpty()) {
            return false;
        }

        if (buscarCliente(cliente.getCodigo()) != null) {
            System.out.println("El cliente ya existe.");
            return false;
        }

        clientes.add(cliente);
        archivo.guardarClientes(clientes);

        return true;
    }

    // Mostrar los clientes
    public void mostrarClientes() {

        for (Cliente cliente : clientes) {
            cliente.mostrarDatos();
            System.out.println("--------------------");
        }
    }
}
