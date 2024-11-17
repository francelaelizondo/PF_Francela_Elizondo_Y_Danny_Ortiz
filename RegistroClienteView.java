import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class RegistroClienteView extends JFrame {
    private JTextField txtNombre, txtApellido, txtCedula, txtTelefono;
    private JButton btnGuardarCliente;
    private Connection conexion;
    private ClienteModel clienteModel;

    public RegistroClienteView(Connection conexion) {
        this.conexion = conexion;
        this.clienteModel = new ClienteModel(conexion);

        setTitle("Registro de Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null); 

        
        setContentPane(new JLabel(new ImageIcon("FONDOINICIO.jpg"))); 
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 80, 150, 25);
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(200, 80, 200, 25);
        txtNombre.setBackground(Color.WHITE);
        txtNombre.setForeground(Color.BLACK);
        add(txtNombre);

      
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(50, 130, 150, 25);
        lblApellido.setForeground(Color.WHITE);
        lblApellido.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(200, 130, 200, 25);
        txtApellido.setBackground(Color.WHITE);
        txtApellido.setForeground(Color.BLACK);
        add(txtApellido);

       
        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setBounds(50, 180, 150, 25);
        lblCedula.setForeground(Color.WHITE);
        lblCedula.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(200, 180, 200, 25);
        txtCedula.setBackground(Color.WHITE);
        txtCedula.setForeground(Color.BLACK);
        add(txtCedula);

        
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(50, 230, 150, 25);
        lblTelefono.setForeground(Color.WHITE);
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(200, 230, 200, 25);
        txtTelefono.setBackground(Color.WHITE);
        txtTelefono.setForeground(Color.BLACK);
        add(txtTelefono);

        
        btnGuardarCliente = new JButton("Guardar Cliente");
        btnGuardarCliente.setBounds(150, 300, 200, 30);
        btnGuardarCliente.setBackground(new Color(102, 51, 0));
        btnGuardarCliente.setForeground(Color.WHITE);
        btnGuardarCliente.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardarCliente.addActionListener(e -> guardarCliente());
        add(btnGuardarCliente);

        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
    }

    private int generarIdCliente() {
        return (int) (Math.random() * 9000) + 1000;  
    }

    private void guardarCliente() {
        try {
            // Generar un ID único para el cliente de forma aleatoria
            int idCliente = generarIdCliente(); 

            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String cedula = txtCedula.getText();
            String telefono = txtTelefono.getText();

            if (nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Llamamos al método insertarCliente para guardar los datos
            boolean exito = clienteModel.insertarCliente(idCliente, nombre, apellido, cedula, telefono);
            JOptionPane.showMessageDialog(this, exito ? "Cliente guardado correctamente." : "Error al guardar el cliente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}