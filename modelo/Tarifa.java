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
public class Tarifa {

    // Atributos de la tarifa
    private String codigo;
    private String descripcion;
    private String tipoDia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double precioHora;

    // Constructor de la clase Tarifa
    public Tarifa(String codigo, String descripcion, String tipoDia,
                  LocalTime horaInicio, LocalTime horaFin,
                  double precioHora) {

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.tipoDia = tipoDia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.precioHora = precioHora;
    }

    // Obtener y modificar el codigo
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // Obtener y modificar la descripcion
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Obtener y modificar el tipo de dia
    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    // Obtener y modificar la hora de inicio
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    // Obtener y modificar la hora de finalizacion
    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    // Obtener y modificar el precio
    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    // Mostrar informacion de la tarifa
    public void mostrarDatos() {

        System.out.println("Codigo: " + codigo);
        System.out.println("Descripcion: " + descripcion);
        System.out.println("Tipo de dia: " + tipoDia);
        System.out.println("Horario: " + horaInicio
                + " a " + horaFin);
        System.out.println("Precio por hora: Q" + precioHora);
    }
}
