/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalTime;

/**
 *
 * @author hurta
 */
public class Cancha {

    // Atributos de la cancha
    private String codigo;
    private String nombre;
    private String tipo;
    private int capacidad;
    private double precioBase;
    private String estado;
    private LocalTime horaApertura;
    private LocalTime horaCierre;

    // Constructor de la clase Cancha
    public Cancha(String codigo, String nombre, String tipo,
                  int capacidad, double precioBase, String estado,
                  LocalTime horaApertura, LocalTime horaCierre) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.estado = estado;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    // Obtener y modificar el codigo
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // Obtener y modificar el nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Obtener y modificar el tipo
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Obtener y modificar la capacidad
    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    // Obtener y modificar el precio base
    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    // Obtener y modificar el estado
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Obtener y modificar la hora de apertura
    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    // Obtener y modificar la hora de cierre
    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    // Mostrar la informacion de la cancha
    public void mostrarDatos() {

        System.out.println("Codigo: " + codigo);
        System.out.println("Nombre: " + nombre);
        System.out.println("Tipo: " + tipo);
        System.out.println("Capacidad: " + capacidad);
        System.out.println("Precio base: Q" + precioBase);
        System.out.println("Estado: " + estado);
        System.out.println("Horario: " + horaApertura
                + " a " + horaCierre);
    }
}
