/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;
import datos.ArchivoCliente;
import datos.ArchivoCancha;
import datos.ArchivoReserva;
import modelo.Cliente;
import modelo.Cancha;
import modelo.Reserva;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Locale;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author hurta
 */
public class PanelInicio extends javax.swing.JPanel {

private JLabel lblClientes;
private JLabel lblCanchas;
private JLabel lblReservas;
private JLabel lblIngresos;

private final Color AZUL = new Color(25, 42, 58);
private final Color CELESTE = new Color(0, 153, 219);
private final Color FONDO = new Color(239, 244, 249);

private void crearInicio() {

    setLayout(new BorderLayout());
    setBackground(FONDO);

    JPanel contenido = new JPanel(new BorderLayout(20, 25));
    contenido.setBackground(FONDO);
    contenido.setBorder(new EmptyBorder(30, 35, 30, 35));

    // ENCABEZADO
    JPanel encabezado = new JPanel();
    encabezado.setLayout(new BoxLayout(
            encabezado, BoxLayout.Y_AXIS));
    encabezado.setBackground(FONDO);

    JLabel titulo = new JLabel("BIENVENIDO A CANCHASPRO");
    titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
    titulo.setForeground(AZUL);
    titulo.setAlignmentX(LEFT_ALIGNMENT);

    JLabel subtitulo = new JLabel(
            "Complejo Deportivo La 10");
    subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
    subtitulo.setForeground(CELESTE);
    subtitulo.setAlignmentX(LEFT_ALIGNMENT);

    JLabel descripcion = new JLabel(
            "Sistema de gestion de reservas y pagos");
    descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    descripcion.setForeground(new Color(90, 105, 120));
    descripcion.setAlignmentX(LEFT_ALIGNMENT);

    encabezado.add(titulo);
    encabezado.add(Box.createVerticalStrut(10));
    encabezado.add(subtitulo);
    encabezado.add(Box.createVerticalStrut(8));
    encabezado.add(descripcion);

    contenido.add(encabezado, BorderLayout.NORTH);

    // TARJETAS
    JPanel tarjetas = new JPanel(new GridLayout(2, 2, 20, 20));
    tarjetas.setOpaque(false);

    lblClientes = new JLabel("0");
    lblCanchas = new JLabel("0");
    lblReservas = new JLabel("0");
    lblIngresos = new JLabel("Q0.00");

    tarjetas.add(crearTarjeta("CLIENTES REGISTRADOS",
            lblClientes, new Color(0, 125, 190)));

    tarjetas.add(crearTarjeta("CANCHAS REGISTRADAS",
            lblCanchas, new Color(0, 150, 130)));

    tarjetas.add(crearTarjeta("TOTAL DE RESERVAS",
            lblReservas, new Color(110, 85, 190)));

    tarjetas.add(crearTarjeta("INGRESOS RECIBIDOS",
            lblIngresos, new Color(210, 125, 25)));

    JPanel centro = new JPanel(new BorderLayout(15, 20));
    centro.setOpaque(false);
    centro.add(tarjetas, BorderLayout.CENTER);

    // MENSAJE INFERIOR
    JPanel mensaje = new JPanel(new FlowLayout(
            FlowLayout.CENTER, 10, 15));
    mensaje.setBackground(AZUL);

    JLabel texto = new JLabel(
            "Gestiona tus canchas, reservas y pagos "
            + "de forma sencilla y rapida.");
    texto.setFont(new Font("Segoe UI", Font.BOLD, 14));
    texto.setForeground(Color.WHITE);

    mensaje.add(texto);
    centro.add(mensaje, BorderLayout.SOUTH);

    contenido.add(centro, BorderLayout.CENTER);

    // BOTON ACTUALIZAR
    JButton btnActualizar = new JButton("ACTUALIZAR DATOS");
    btnActualizar.setFont(new Font(
            "Segoe UI", Font.BOLD, 13));
    btnActualizar.setBackground(CELESTE);
    btnActualizar.setForeground(Color.WHITE);
    btnActualizar.setFocusPainted(false);
    btnActualizar.setPreferredSize(new Dimension(180, 38));

    btnActualizar.addActionListener(e -> cargarDatos());

    JPanel pie = new JPanel(new FlowLayout(
            FlowLayout.RIGHT));
    pie.setOpaque(false);
    pie.add(btnActualizar);

    contenido.add(pie, BorderLayout.SOUTH);

    add(contenido, BorderLayout.CENTER);

    cargarDatos();
}

private JPanel crearTarjeta(String titulo, JLabel valor,
        Color color) {

    JPanel tarjeta = new JPanel();
    tarjeta.setLayout(new BoxLayout(
            tarjeta, BoxLayout.Y_AXIS));

    tarjeta.setBackground(Color.WHITE);
    tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                    new Color(215, 225, 235), 1),
            new EmptyBorder(25, 20, 25, 20)
    ));

    JLabel lblTitulo = new JLabel(titulo);
    lblTitulo.setFont(new Font(
            "Segoe UI", Font.BOLD, 14));
    lblTitulo.setForeground(color);
    lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

    valor.setFont(new Font(
            "Segoe UI", Font.BOLD, 34));
    valor.setForeground(AZUL);
    valor.setAlignmentX(CENTER_ALIGNMENT);

    tarjeta.add(Box.createVerticalGlue());
    tarjeta.add(lblTitulo);
    tarjeta.add(Box.createVerticalStrut(20));
    tarjeta.add(valor);
    tarjeta.add(Box.createVerticalGlue());

    return tarjeta;
}

private void cargarDatos() {

    try {

        ArrayList<Cliente> clientes =
                new ArchivoCliente().leerClientes();

        ArrayList<Cancha> canchas =
                new ArchivoCancha().leerCanchas();

        ArrayList<Reserva> reservas =
                new ArchivoReserva().leerReservas(
                        clientes, canchas);

        lblClientes.setText(
                String.valueOf(clientes.size()));

        lblCanchas.setText(
                String.valueOf(canchas.size()));

        lblReservas.setText(
                String.valueOf(reservas.size()));

        // SUMAR PAGOS REGISTRADOS
        double ingresos = 0;

        java.nio.file.Path archivo =
                Paths.get("pagos.csv");

        if (Files.exists(archivo)) {

            for (String linea : Files.readAllLines(
                    archivo, StandardCharsets.UTF_8)) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(";", -1);

                // Formato:
                // ID;RESERVA;FECHA;MONTO;METODO;DESCRIPCION
                if (datos.length != 6) {
                    throw new IllegalArgumentException(
                            "Registro incorrecto en pagos.csv");
                }

                ingresos += Double.parseDouble(datos[3]);
            }
        }

        lblIngresos.setText(
                String.format(Locale.US,
                        "Q%,.2f", ingresos));

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "No se pudieron cargar los datos: "
                + e.getMessage());
    }
}

    /**
     * Creates new form PanelInicio
     */
    public PanelInicio() {
        initComponents();
        crearInicio();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
