import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class RegistroReservacionView extends JFrame {
    private JTextField txtIdCliente;
    private JSpinner txtFechaLlegada, txtFechaSalida; 
    private JButton btnRegistrarReservacion;
    private Connection conexion;
    private ReservacionModel reservacionModel; // Instancia de la clase ReservacionModel

    // Constructor que recibe la conexión a la base de datos
    public RegistroReservacionView(Connection conexion) {
        this.conexion = conexion;
        this.reservacionModel = new ReservacionModel(conexion);

        setTitle("Registrar Reservación");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        setLocationRelativeTo(null);

        
        setLayout(null);

      
        setContentPane(new JLabel(new ImageIcon("FONDOINICIO.jpg")));

        
        JLabel lblIdCliente = new JLabel("ID del Cliente:");
        lblIdCliente.setBounds(50, 40, 150, 25);
        lblIdCliente.setForeground(Color.WHITE); 
        lblIdCliente.setFont(new Font("Arial", Font.BOLD, 14)); 
        add(lblIdCliente);

        
        txtIdCliente = new JTextField();
        txtIdCliente.setBounds(200, 40, 200, 25);
        txtIdCliente.setBackground(new Color(255, 255, 255)); 
        txtIdCliente.setForeground(Color.BLACK); 
        add(txtIdCliente);

        
        JLabel lblFechaLlegada = new JLabel("Fecha de Llegada:");
        lblFechaLlegada.setBounds(50, 80, 150, 25);
        lblFechaLlegada.setForeground(Color.WHITE);
        lblFechaLlegada.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblFechaLlegada);

        
        txtFechaLlegada = new JSpinner(new SpinnerDateModel());
        txtFechaLlegada.setBounds(250, 80, 150, 25);
        JSpinner.DateEditor editorFechaLlegada = new JSpinner.DateEditor(txtFechaLlegada, "yyyy-MM-dd");
        txtFechaLlegada.setEditor(editorFechaLlegada);
        add(txtFechaLlegada);

        
        JLabel lblFechaSalida = new JLabel("Fecha de Salida:");
        lblFechaSalida.setBounds(50, 120, 150, 25);
        lblFechaSalida.setForeground(Color.WHITE);
        lblFechaSalida.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblFechaSalida);

        
        txtFechaSalida = new JSpinner(new SpinnerDateModel());
        txtFechaSalida.setBounds(250, 120, 150, 25);
        JSpinner.DateEditor editorFechaSalida = new JSpinner.DateEditor(txtFechaSalida, "yyyy-MM-dd");
        txtFechaSalida.setEditor(editorFechaSalida);
        add(txtFechaSalida);

        
        JLabel lblTipoHabitacion = new JLabel("Tipo de Habitación:");
        lblTipoHabitacion.setBounds(50, 160, 150, 25);
        lblTipoHabitacion.setForeground(Color.WHITE);
        lblTipoHabitacion.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTipoHabitacion);

        
        String[] tiposHabitacion = { "Suite", "Junior Suite", "Gran Suite", "Individuales", "Dobles", "Cuádruples" };
        JComboBox<String> comboTipoHabitacion = new JComboBox<>(tiposHabitacion);
        comboTipoHabitacion.setBounds(200, 160, 200, 25); 
        comboTipoHabitacion.setBackground(new Color(255, 255, 255)); 
        comboTipoHabitacion.setForeground(Color.BLACK); 
        add(comboTipoHabitacion);

       
        btnRegistrarReservacion = new JButton("Registrar Reservación");
        btnRegistrarReservacion.setBounds(150, 210, 200, 40);
        btnRegistrarReservacion.setBackground(new Color(100, 150, 255)); 
        btnRegistrarReservacion.setForeground(Color.WHITE); 
        btnRegistrarReservacion.setFont(new Font("Arial", Font.BOLD, 16)); 
        add(btnRegistrarReservacion);
        btnRegistrarReservacion.addActionListener(e -> registrarReservacion(comboTipoHabitacion));
    }

    
    private void registrarReservacion(JComboBox<String> comboTipoHabitacion) {
        
        String idClienteStr = txtIdCliente.getText().trim();
        String tipoHabitacion = (String) comboTipoHabitacion.getSelectedItem();

        
        java.util.Date fechaLlegada = (java.util.Date) txtFechaLlegada.getValue();
        java.util.Date fechaSalida = (java.util.Date) txtFechaSalida.getValue();

        
        if (idClienteStr.isEmpty() || tipoHabitacion == null || tipoHabitacion.isEmpty() || fechaLlegada == null || fechaSalida == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Validar que idCliente sea un número válido
            int idCliente = Integer.parseInt(idClienteStr);

            // Generar un ID para la reservación 
            int idReservacion = generarIdReservacion(); 

            
            // Llamada al método insertarReservacion
            boolean exito = reservacionModel.insertarReservacion(idReservacion, idCliente,
                    new java.sql.Date(fechaLlegada.getTime()),
                    new java.sql.Date(fechaSalida.getTime()),
                    tipoHabitacion);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Reservación registrada correctamente.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la reservación.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "El ID del cliente debe ser un número válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la reservación: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        txtIdCliente.setText("");
        txtFechaLlegada.setValue(new java.util.Date()); 
        txtFechaSalida.setValue(new java.util.Date()); 
    }

    // Método para generar el ID de la reservación 
    private int generarIdReservacion() {
        return (int) (Math.random() * 1000); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Simulación de conexión a la base de datos
            Connection conexion = null; 
            RegistroReservacionView registro = new RegistroReservacionView(conexion);
            registro.setVisible(true);
        });
    }
}
