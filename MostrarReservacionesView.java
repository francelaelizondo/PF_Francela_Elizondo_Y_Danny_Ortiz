import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableModel;

public class MostrarReservacionesView extends JFrame {
    private Connection conexion;
    private JTable tableReservaciones;
    private DefaultTableModel tableModel;
    private JButton btnEliminar, btnEditar;

    public MostrarReservacionesView(Connection conexion) {
        this.conexion = conexion;
        setTitle("Lista de Reservaciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        ImageIcon fondo = new ImageIcon("FONDOINICIO.jpg"); 
        JLabel fondoLabel = new JLabel(fondo);
        fondoLabel.setLayout(new BorderLayout());
        setContentPane(fondoLabel);

        
        tableModel = new DefaultTableModel() {
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; 
            }
        };

     
        tableModel.addColumn("ID Reservación");
        tableModel.addColumn("ID Cliente");
        tableModel.addColumn("Nombre Cliente");
        tableModel.addColumn("Apellido Cliente");
        tableModel.addColumn("Fecha de Llegada");
        tableModel.addColumn("Fecha de Salida");
        tableModel.addColumn("Tipo de Habitación");

   
        tableReservaciones = new JTable(tableModel);

   
        JScrollPane scrollPane = new JScrollPane(tableReservaciones);
        add(scrollPane, BorderLayout.CENTER);


        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        btnEliminar = new JButton("Eliminar");
        btnEditar = new JButton("Editar");

       
        btnEliminar.setBackground(new Color(139, 69, 19));  
        btnEliminar.setForeground(Color.WHITE);  
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 16)); 

        btnEditar.setBackground(new Color(139, 69, 19));  
        btnEditar.setForeground(Color.WHITE);  
        btnEditar.setFont(new Font("Arial", Font.BOLD, 16));  

     
        panelBotones.add(btnEliminar);
        panelBotones.add(btnEditar);

        add(panelBotones, BorderLayout.SOUTH);

       
        btnEliminar.addActionListener(e -> eliminarReservacion());
        btnEditar.addActionListener(e -> editarReservacion());

        cargarReservaciones();
    }

/**
 * Método para cargar todas las reservaciones desde la base de datos y mostrarlas en la tabla.
 */
private void cargarReservaciones() {
    String sql = "{ CALL sp_mostrarReservaciones() }";
    
    try (CallableStatement stmt = conexion.prepareCall(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        tableModel.setRowCount(0); 
        
        while (rs.next()) {
            int idReservacion = rs.getInt("Id_Reservacion");
            int idCliente = rs.getInt("Id_Cliente");
            String nombreCliente = rs.getString("Nombre");  
            String apellidoCliente = rs.getString("Apellido"); 
            String fechaLlegada = rs.getString("Fecha_Llegada");
            String fechaSalida = rs.getString("Fecha_Salida");
            String tipoHabitacion = rs.getString("Tipo_Habitacion");


            // Agregar los datos de la fila a la tabla
            tableModel.addRow(new Object[]{
                idReservacion, 
                idCliente, 
                nombreCliente, 
                apellidoCliente, 
                fechaLlegada, 
                fechaSalida, 
                tipoHabitacion
            });
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar reservaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


private void eliminarReservacion() {
    int filaSeleccionada = tableReservaciones.getSelectedRow();

    if (filaSeleccionada != -1) {
        int idReservacion = (int) tableModel.getValueAt(filaSeleccionada, 0);
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta reservación?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            String sql = "{ CALL sp_eliminarReservacion(?) }";

            try (CallableStatement stmt = conexion.prepareCall(sql)) {
                stmt.setInt(1, idReservacion);
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Reservación eliminada exitosamente.");
                    tableModel.removeRow(filaSeleccionada);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar reservación.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar reservación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una reservación para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}

private void editarReservacion() {
    int filaSeleccionada = tableReservaciones.getSelectedRow();

    if (filaSeleccionada != -1) {
        int idReservacion = (int) tableModel.getValueAt(filaSeleccionada, 0);
        String tipoHabitacionActual = (String) tableModel.getValueAt(filaSeleccionada, 6);
        
        String[] tiposHabitacion = { "Suite", "Junior Suite", "Gran Suite", "Individuales", "Dobles", "Cuádruples" };
        JComboBox<String> comboTipoHabitacion = new JComboBox<>(tiposHabitacion);
        comboTipoHabitacion.setSelectedItem(tipoHabitacionActual);

        JSpinner spinnerFechaLlegada = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorFechaLlegada = new JSpinner.DateEditor(spinnerFechaLlegada, "yyyy-MM-dd");
        spinnerFechaLlegada.setEditor(editorFechaLlegada);

        JSpinner spinnerFechaSalida = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorFechaSalida = new JSpinner.DateEditor(spinnerFechaSalida, "yyyy-MM-dd");
        spinnerFechaSalida.setEditor(editorFechaSalida);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            spinnerFechaLlegada.setValue(sdf.parse((String) tableModel.getValueAt(filaSeleccionada, 4)));
            spinnerFechaSalida.setValue(sdf.parse((String) tableModel.getValueAt(filaSeleccionada, 5)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Fecha de Llegada:"));
        panel.add(spinnerFechaLlegada);
        panel.add(new JLabel("Fecha de Salida:"));
        panel.add(spinnerFechaSalida);
        panel.add(new JLabel("Tipo de Habitación:"));
        panel.add(comboTipoHabitacion);

        int opcion = JOptionPane.showConfirmDialog(this, panel, "Editar Reservación", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoTipoHabitacion = (String) comboTipoHabitacion.getSelectedItem();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String nuevaFechaLlegada = sdf.format(spinnerFechaLlegada.getValue());
            String nuevaFechaSalida = sdf.format(spinnerFechaSalida.getValue());

            String sql = "{ CALL sp_actualizarReservacion(?, ?, ?, ?) }";

            try (CallableStatement stmt = conexion.prepareCall(sql)) {
                stmt.setInt(1, idReservacion);
                stmt.setString(2, nuevaFechaLlegada);
                stmt.setString(3, nuevaFechaSalida);
                stmt.setString(4, nuevoTipoHabitacion);

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    tableModel.setValueAt(nuevaFechaLlegada, filaSeleccionada, 4);
                    tableModel.setValueAt(nuevaFechaSalida, filaSeleccionada, 5);
                    tableModel.setValueAt(nuevoTipoHabitacion, filaSeleccionada, 6);
                    JOptionPane.showMessageDialog(this, "Reservación actualizada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar reservación.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al editar reservación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una reservación para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}

/**
 * Método principal para ejecutar la aplicación de reservaciones.
 */
public static void main(String[] args) {
    try {
        // Configuración de la conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/hotel";
        String usuario = "root";
        String contrasena = "Fe504550628";
        Connection conexion = DriverManager.getConnection(url, usuario, contrasena);

        // Inicializar la vista de reservaciones y mostrarla
        MostrarReservacionesView mostrarReservacionesView = new MostrarReservacionesView(conexion);
        mostrarReservacionesView.setVisible(true);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
