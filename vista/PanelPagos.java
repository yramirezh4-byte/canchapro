/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;
import modelo.Cliente;
import modelo.Cancha;
import modelo.Reserva;
import datos.ArchivoCliente;
import datos.ArchivoCancha;
import datos.ArchivoReserva;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hurta
 */
public class PanelPagos extends javax.swing.JPanel {

private final String ARCHIVO_PAGOS = "pagos.csv";

// OBTENER RESERVA
private Reserva buscarReserva(String codigo) {

    ArchivoCliente archivoCliente = new ArchivoCliente();
    ArchivoCancha archivoCancha = new ArchivoCancha();
    ArchivoReserva archivoReserva = new ArchivoReserva();

    ArrayList<Cliente> clientes = archivoCliente.leerClientes();
    ArrayList<Cancha> canchas = archivoCancha.leerCanchas();

    ArrayList<Reserva> reservas =
            archivoReserva.leerReservas(clientes, canchas);

    for (Reserva reserva : reservas) {
        if (reserva.getCodigo().equalsIgnoreCase(codigo)) {
            return reserva;
        }
    }

    return null;
}

// LEER PAGOS
private List<String[]> leerPagos() throws IOException {

    List<String[]> pagos = new ArrayList<>();
    Path ruta = Paths.get(ARCHIVO_PAGOS);

    if (!Files.exists(ruta)) {
        return pagos;
    }

    List<String> lineas =
            Files.readAllLines(ruta, StandardCharsets.UTF_8);

    for (String linea : lineas) {

        if (linea.trim().isEmpty()) {
            continue;
        }

        String[] datos = linea.split(";", -1);

        if (datos.length != 6) {
            throw new IOException(
                    "Formato incorrecto en pagos.csv");
        }

        pagos.add(datos);
    }

    return pagos;
}

// CALCULAR TOTAL PAGADO
private double calcularPagado(String codigo) throws IOException {

    double total = 0;

    for (String[] pago : leerPagos()) {

        if (pago[0].equalsIgnoreCase(codigo)) {

            try {
                total += Double.parseDouble(pago[3]);
            } catch (NumberFormatException e) {
                throw new IOException(
                        "Existe un monto inválido en pagos.csv", e);
            }
        }
    }

    return Math.round(total * 100.0) / 100.0;
}

// CONSULTAR SALDO
private void consultarSaldo() {

    try {

        String codigo = txtCodigoReserva.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese el código de reserva.");
            return;
        }

        Reserva reserva = buscarReserva(codigo);

        if (reserva == null) {
            txtSaldoPendiente.setText("");
            JOptionPane.showMessageDialog(this,
                    "No se encontró la reserva.");
            return;
        }

        double pagado = calcularPagado(codigo);

        double saldo = Math.max(0,
                reserva.getTotal() - pagado);

        txtSaldoPendiente.setText(
                String.format(Locale.US, "%.2f", saldo));

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Error al consultar saldo: " + e.getMessage());
    }
}

// MOSTRAR HISTORIAL
private void cargarPagos() {

    DefaultTableModel modelo = new DefaultTableModel(
            new String[]{
                "Reserva", "Fecha", "Monto Q", "Método"
            }, 0) {

        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    try {

        for (String[] pago : leerPagos()) {

            modelo.addRow(new Object[]{
                 pago[1],
                 pago[2],
                 pago[3],
                 pago[4]
            });
        }

        tblPagos.setModel(modelo);
        tblPagos.setRowHeight(25);

    } catch (IOException e) {

        JOptionPane.showMessageDialog(this,
                "Error al cargar pagos: " + e.getMessage());
    }
}

// REGISTRAR PAGO
private void registrarPago() {

    try {

        String codigo = txtCodigoReserva.getText().trim();
        String textoMonto = txtMontoPago.getText().trim();

        if (codigo.isEmpty() || textoMonto.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Complete el código y el monto.");
            return;
        }

        Reserva reserva = buscarReserva(codigo);

        if (reserva == null) {

            JOptionPane.showMessageDialog(this,
                    "La reserva no existe.");
            return;
        }

        if (reserva.getEstado().equalsIgnoreCase("CANCELADA")) {

            JOptionPane.showMessageDialog(this,
                    "No se puede pagar una reserva cancelada.");
            return;
        }

        double monto = Double.parseDouble(
                textoMonto.replace(",", "."));

        if (!Double.isFinite(monto) || monto <= 0) {

            JOptionPane.showMessageDialog(this,
                    "Ingrese un monto mayor que cero.");
            return;
        }

        monto = Math.round(monto * 100.0) / 100.0;

        if (monto <= 0) {
            JOptionPane.showMessageDialog(this,
                    "El monto mínimo es Q0.01.");
            return;
        }

        double pagado = calcularPagado(codigo);

        double saldo = Math.round(
                (reserva.getTotal() - pagado) * 100.0) / 100.0;

        if (saldo <= 0) {

            JOptionPane.showMessageDialog(this,
                    "Esta reserva ya está pagada.");
            return;
        }

        if (monto > saldo) {

            JOptionPane.showMessageDialog(this,
                    "El monto supera el saldo pendiente de Q"
                    + String.format(Locale.US, "%.2f", saldo));
            return;
        }

        String metodo =
                cmbMetodoPago.getSelectedItem().toString();

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Registrar pago de Q"
                + String.format(Locale.US, "%.2f", monto)
                + " para la reserva " + codigo + "?",
                "Confirmar pago",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

       String idPago = "P" + System.currentTimeMillis();

String registro =
        idPago + ";"
        + codigo + ";"
        + java.time.LocalDateTime.now() + ";"
        + String.format(Locale.US, "%.2f", monto) + ";"
        + metodo + ";"
        + "Pago de reserva"
        + System.lineSeparator();
        Files.write(
                Paths.get(ARCHIVO_PAGOS),
                registro.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

        JOptionPane.showMessageDialog(this,
                "Pago registrado correctamente.");

        txtMontoPago.setText("");

        cargarPagos();
        consultarSaldo();

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(this,
                "Ingrese un monto válido. Ejemplo: 100.00");

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Error al registrar pago: " + e.getMessage());
    }
}

    /**
     * Creates new form PanelPagos
     */
    public PanelPagos() {
        initComponents();
        txtSaldoPendiente.setEditable(false);
    cargarPagos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigoReserva = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbMetodoPago = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMontoPago = new javax.swing.JTextField();
        txtSaldoPendiente = new javax.swing.JTextField();
        btnConsultarSaldo = new javax.swing.JButton();
        btnRegistrarPago = new javax.swing.JButton();
        btnActualizarPagos = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPagos = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 0));
        jLabel1.setText("REGISTRO DE PAGOS");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Código de reserva");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Metodo de pago");

        cmbMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EFECTIVO", "TRANSFERENCIA", "TARJETA" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Monto a pagar (Q)");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Saldo pendiente (Q)");

        btnConsultarSaldo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnConsultarSaldo.setText("Consultar Saldos");
        btnConsultarSaldo.addActionListener(this::btnConsultarSaldoActionPerformed);

        btnRegistrarPago.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegistrarPago.setText("Registrar Pagos");
        btnRegistrarPago.addActionListener(this::btnRegistrarPagoActionPerformed);

        btnActualizarPagos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizarPagos.setText("Actualizar");
        btnActualizarPagos.addActionListener(this::btnActualizarPagosActionPerformed);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("HISTORIAL DE PAGOS");

        tblPagos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblPagos.setForeground(new java.awt.Color(0, 0, 255));
        tblPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Reserva", "Fecha", "Monto Q", "Método"
            }
        ));
        jScrollPane2.setViewportView(tblPagos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConsultarSaldo)
                        .addGap(126, 126, 126)
                        .addComponent(btnRegistrarPago)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizarPagos))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                                    .addComponent(txtCodigoReserva))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMontoPago))
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbMetodoPago, 0, 259, Short.MAX_VALUE)
                            .addComponent(txtSaldoPendiente)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(198, 198, 198))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(481, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigoReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMontoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaldoPendiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConsultarSaldo)
                    .addComponent(btnRegistrarPago)
                    .addComponent(btnActualizarPagos))
                .addGap(28, 28, 28)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(411, 411, 411))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarSaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarSaldoActionPerformed
        // TODO add your handling code here:
        consultarSaldo();
    }//GEN-LAST:event_btnConsultarSaldoActionPerformed

    private void btnRegistrarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarPagoActionPerformed
        // TODO add your handling code here:
        registrarPago();
    }//GEN-LAST:event_btnRegistrarPagoActionPerformed

    private void btnActualizarPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarPagosActionPerformed
        // TODO add your handling code here:
        cargarPagos();
consultarSaldo();
    }//GEN-LAST:event_btnActualizarPagosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarPagos;
    private javax.swing.JButton btnConsultarSaldo;
    private javax.swing.JButton btnRegistrarPago;
    private javax.swing.JComboBox<String> cmbMetodoPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblPagos;
    private javax.swing.JTextField txtCodigoReserva;
    private javax.swing.JTextField txtMontoPago;
    private javax.swing.JTextField txtSaldoPendiente;
    // End of variables declaration//GEN-END:variables
}
