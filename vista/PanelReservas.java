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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hurta
 */

public class PanelReservas extends javax.swing.JPanel {

private void cargarReservas() {

    DefaultTableModel modelo = new DefaultTableModel(
            new String[]{
                "Código", "Cliente", "Cancha", "Fecha",
                "Inicio", "Fin", "Total", "Estado"
            }, 0) {

        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    ArchivoCliente archivoCliente = new ArchivoCliente();
    ArchivoCancha archivoCancha = new ArchivoCancha();
    ArchivoReserva archivoReserva = new ArchivoReserva();

    ArrayList<Cliente> clientes =
            archivoCliente.leerClientes();

    ArrayList<Cancha> canchas =
            archivoCancha.leerCanchas();

    ArrayList<Reserva> reservas =
            archivoReserva.leerReservas(clientes, canchas);

    for (Reserva reserva : reservas) {

        modelo.addRow(new Object[]{
            reserva.getCodigo(),
            reserva.getCliente().getNombre(),
            reserva.getCancha().getNombre(),
            reserva.getFecha(),
            reserva.getHoraInicio(),
            reserva.getHoraFin(),
            reserva.getTotal(),
            reserva.getEstado()
        });
    }

    btlReservas.setModel(modelo);
    btlReservas.setRowHeight(25);
    btlReservas.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION);
}

private void buscarReserva() {

    String codigo = txtBuscarReserva.getText().trim();

    cargarReservas();

    if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Ingrese un código de reserva.");
        return;
    }

    DefaultTableModel modelo =
            (DefaultTableModel) btlReservas.getModel();

    for (int i = modelo.getRowCount() - 1; i >= 0; i--) {

        String codigoFila =
                modelo.getValueAt(i, 0).toString();

        if (!codigoFila.equalsIgnoreCase(codigo)) {
            modelo.removeRow(i);
        }
    }

    if (modelo.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this,
                "No se encontró la reserva.");
    }
}

private void cambiarEstado(String nuevoEstado) {

    int fila = btlReservas.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(this,
                "Seleccione una reserva de la tabla.");
        return;
    }

    String codigo = btlReservas.getValueAt(
            fila, 0).toString();

    ArchivoCliente archivoCliente = new ArchivoCliente();
    ArchivoCancha archivoCancha = new ArchivoCancha();
    ArchivoReserva archivoReserva = new ArchivoReserva();

    ArrayList<Cliente> clientes =
            archivoCliente.leerClientes();

    ArrayList<Cancha> canchas =
            archivoCancha.leerCanchas();

    ArrayList<Reserva> reservas =
            archivoReserva.leerReservas(clientes, canchas);

    for (Reserva reserva : reservas) {

        if (reserva.getCodigo().equalsIgnoreCase(codigo)) {

            String actual = reserva.getEstado();

            if (actual.equalsIgnoreCase("CANCELADA")
                    || actual.equalsIgnoreCase("FINALIZADA")) {

                JOptionPane.showMessageDialog(this,
                        "No se puede modificar una reserva cerrada.");
                return;
            }

            if (actual.equalsIgnoreCase(nuevoEstado)) {

                JOptionPane.showMessageDialog(this,
                        "La reserva ya tiene ese estado.");
                return;
            }

            if (nuevoEstado.equals("CONFIRMADA")
                    && !actual.equalsIgnoreCase("PENDIENTE")) {

                JOptionPane.showMessageDialog(this,
                        "Solo se pueden confirmar reservas pendientes.");
                return;
            }

            if (nuevoEstado.equals("FINALIZADA")
                    && !actual.equalsIgnoreCase("CONFIRMADA")
                    && !actual.equalsIgnoreCase("EN_CURSO")) {

                JOptionPane.showMessageDialog(this,
                        "Primero debe confirmar la reserva.");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Cambiar reserva " + codigo
                    + " a " + nuevoEstado + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            reserva.setEstado(nuevoEstado);

            archivoReserva.guardarReservas(reservas);

            cargarReservas();

            JOptionPane.showMessageDialog(this,
                    "Reserva actualizada a: " + nuevoEstado);

            return;
        }
    }

    JOptionPane.showMessageDialog(this,
            "No se encontró la reserva.");
}

    /**
     * Creates new form PanelReservas
     */
    
    public PanelReservas()
    
    {
        
        initComponents();
        cargarReservas();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscarReserva = new javax.swing.JTextField();
        btnBuscarReserva = new javax.swing.JButton();
        btnMostrarReservas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        btlReservas = new javax.swing.JTable();
        btnConfirmarReserva = new javax.swing.JButton();
        btnFinalizarReserva = new javax.swing.JButton();
        btnCancelarReserva = new javax.swing.JButton();

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 0, 255));
        lblTitulo.setText("ADMINISTRACIÓN DE RESERVAS");

        lblBuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBuscar.setText("Código de reserva");

        btnBuscarReserva.setText("Buscar");
        btnBuscarReserva.addActionListener(this::btnBuscarReservaActionPerformed);

        btnMostrarReservas.setText("Mostrar todas");
        btnMostrarReservas.addActionListener(this::btnMostrarReservasActionPerformed);

        btlReservas.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createCompoundBorder()));
        btlReservas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btlReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Cliente", "Cancha", "Fecha", "Inicio", "Fin", "Total", "Estado"
            }
        ));
        jScrollPane1.setViewportView(btlReservas);

        btnConfirmarReserva.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnConfirmarReserva.setForeground(new java.awt.Color(204, 0, 0));
        btnConfirmarReserva.setText("Confirmar");
        btnConfirmarReserva.addActionListener(this::btnConfirmarReservaActionPerformed);

        btnFinalizarReserva.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFinalizarReserva.setForeground(new java.awt.Color(204, 51, 0));
        btnFinalizarReserva.setText("Finalizar");
        btnFinalizarReserva.addActionListener(this::btnFinalizarReservaActionPerformed);

        btnCancelarReserva.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelarReserva.setForeground(new java.awt.Color(204, 51, 0));
        btnCancelarReserva.setText("Cancelar");
        btnCancelarReserva.addActionListener(this::btnCancelarReservaActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtBuscarReserva, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBuscar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                                .addGap(56, 56, 56)
                                .addComponent(btnBuscarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(btnMostrarReservas, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(btnConfirmarReserva)
                        .addGap(111, 111, 111)
                        .addComponent(btnFinalizarReserva)
                        .addGap(110, 110, 110)
                        .addComponent(btnCancelarReserva)))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarReserva)
                    .addComponent(btnMostrarReservas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmarReserva)
                    .addComponent(btnFinalizarReserva)
                    .addComponent(btnCancelarReserva))
                .addContainerGap(186, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFinalizarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarReservaActionPerformed
        // TODO add your handling code here:
        cambiarEstado("FINALIZADA");
    }//GEN-LAST:event_btnFinalizarReservaActionPerformed

    private void btnConfirmarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarReservaActionPerformed
        // TODO add your handling code here:
        cambiarEstado("CONFIRMADA");
    }//GEN-LAST:event_btnConfirmarReservaActionPerformed

    private void btnBuscarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarReservaActionPerformed
        // TODO add your handling code here:
        buscarReserva();
    }//GEN-LAST:event_btnBuscarReservaActionPerformed

    private void btnMostrarReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarReservasActionPerformed
        // TODO add your handling code here:
        cargarReservas();
    }//GEN-LAST:event_btnMostrarReservasActionPerformed

    private void btnCancelarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarReservaActionPerformed
        // TODO add your handling code here:
        cambiarEstado("CANCELADA");
    }//GEN-LAST:event_btnCancelarReservaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable btlReservas;
    private javax.swing.JButton btnBuscarReserva;
    private javax.swing.JButton btnCancelarReserva;
    private javax.swing.JButton btnConfirmarReserva;
    private javax.swing.JButton btnFinalizarReserva;
    private javax.swing.JButton btnMostrarReservas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtBuscarReserva;
    // End of variables declaration//GEN-END:variables
}
