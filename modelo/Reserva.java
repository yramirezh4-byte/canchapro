/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author hurta
 */
public class Reserva {

    // Atributos de la reserva
    private String codigo;
    private Cliente cliente;
    private Cancha cancha;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double total;
    private String estado;
    private ArrayList<Pago> pagos;

    // Constructor de la clase Reserva
    public Reserva(String codigo, Cliente cliente, Cancha cancha,
                   LocalDate fecha, LocalTime horaInicio,
                   LocalTime horaFin, double total, String estado) {

        this.codigo = codigo;
        this.cliente = cliente;
        this.cancha = cancha;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.total = total;
        this.estado = estado;
        this.pagos = new ArrayList<>();
    }

    // Obtener y modificar el codigo
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // Obtener y modificar el cliente
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Obtener y modificar la cancha
    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    // Obtener y modificar la fecha
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // Obtener y modificar la hora de inicio
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    // Obtener y modificar la hora final
    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    // Obtener y modificar el total
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // Obtener y modificar el estado
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Obtener la lista de pagos
    public ArrayList<Pago> getPagos() {
        return pagos;
    }

    // Agregar un pago a la reserva
    public void agregarPago(Pago pago) {
        pagos.add(pago);
    }

    // Calcular cuanto ha pagado el cliente
    public double calcularTotalPagado() {

        double totalPagado = 0;

        for (Pago pago : pagos) {
            totalPagado = totalPagado + pago.getMonto();
        }

        return totalPagado;
    }

    // Calcular el saldo pendiente
    public double calcularSaldoPendiente() {
        return total - calcularTotalPagado();
    }

    // Mostrar los datos de la reserva
    public void mostrarDatos() {

        System.out.println("Codigo: " + codigo);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Cancha: " + cancha.getNombre());
        System.out.println("Fecha: " + fecha);
        System.out.println("Horario: " + horaInicio + " a " + horaFin);
        System.out.println("Total: Q" + total);
        System.out.println("Total pagado: Q" + calcularTotalPagado());
        System.out.println("Saldo pendiente: Q" + calcularSaldoPendiente());
        System.out.println("Estado: " + estado);
    }
}
