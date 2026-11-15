/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import modelo.Cancha;
import datos.ArchivoCancha;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class CanchaServicio {

    private ArrayList<Cancha> canchas;
    private ArchivoCancha archivo;

    public CanchaServicio() {
        archivo = new ArchivoCancha();
        canchas = archivo.leerCanchas();
    }

    // Obtener las canchas
    public ArrayList<Cancha> getCanchas() {
        return canchas;
    }

    // Buscar una cancha por codigo
    public Cancha buscarCancha(String codigo) {

        for (Cancha cancha : canchas) {

            if (cancha.getCodigo().equalsIgnoreCase(codigo)) {
                return cancha;
            }
        }

        return null;
    }

    // Registrar una cancha
    public boolean registrarCancha(Cancha cancha) {

        if (cancha == null
                || cancha.getCodigo() == null
                || cancha.getCodigo().trim().isEmpty()
                || cancha.getNombre() == null
                || cancha.getNombre().trim().isEmpty()
                || cancha.getCapacidad() <= 0) {
            return false;
        }

        if (buscarCancha(cancha.getCodigo()) != null) {
            System.out.println("La cancha ya existe.");
            return false;
        }

        canchas.add(cancha);
        archivo.guardarCanchas(canchas);

        return true;
    }

    // Cambiar el estado de una cancha
    public boolean cambiarEstado(String codigo, String estado) {

        Cancha cancha = buscarCancha(codigo);

        if (cancha == null) {
            return false;
        }

        if (!estado.equals("DISPONIBLE")
                && !estado.equals("MANTENIMIENTO")
                && !estado.equals("INACTIVA")) {
            return false;
        }

        cancha.setEstado(estado);
        archivo.guardarCanchas(canchas);

        return true;
    }

    // Mostrar las canchas
    public void mostrarCanchas() {

        for (Cancha cancha : canchas) {
            cancha.mostrarDatos();
            System.out.println("--------------------");
        }
    }
}
