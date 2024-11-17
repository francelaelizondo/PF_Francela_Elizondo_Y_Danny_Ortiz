import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class MostrarClientesView extends JFrame {
    private Connection conexion;
    private JTable tableClientes;
    private DefaultTableModel tableModel;
    private JButton btnEliminar, btnEditar;

    public MostrarClientesView(Connection conexion) {
        this.conexion = conexion;
        setTitle("Lista de Clientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

     
        tableModel = new DefaultTableModel() {
         
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; 
            }
        };
        tableModel.addColumn("ID Cliente");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Cédula");
        tableModel.addColumn("Teléfono");

    
        tableClientes = new JTable(tableModel);

        tableClientes.setBackground(new Color(255, 255, 255));
        tableClientes.setForeground(new Color(0, 0, 0)); 
        tableClientes.setFont(new Font("Arial", Font.PLAIN, 14)); 
        tableClientes.setGridColor(new Color(200, 200, 200));
        tableClientes.setSelectionBackground(new Color(173, 216, 230)); 
        tableClientes.setSelectionForeground(Color.BLACK); 

   
        JScrollPane scrollPane = new JScrollPane(tableClientes);
        add(scrollPane, BorderLayout.CENTER);

    
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        btnEliminar = new JButton("Eliminar");
        btnEditar = new JButton("Editar");

        btnEliminar.setBackground(new Color(255, 99, 71)); 
        btnEliminar.setForeground(Color.WHITE); 
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));

        btnEditar.setBackground(new Color(70, 130, 180)); 
        btnEditar.setForeground(Color.WHITE); 
        btnEditar.setFont(new Font("Arial", Font.BOLD, 14)); 

        panelBotones.add(btnEliminar);
        panelBotones.add(btnEditar);

        add(panelBotones, BorderLayout.SOUTH);

        // Asignar acción a los botones
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnEditar.addActionListener(e -> editarCliente());

        cargarClientes();
    }

  // Método para cargar los clientes en la tabla
private void cargarClientes() {
    // Llamada al procedimiento almacenado para obtener los clientes
    String sql = "{ CALL sp_mostrarCliente() }";

    try (CallableStatement stmt = conexion.prepareCall(sql);
         ResultSet rs = stmt.executeQuery()) {

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Iterar sobre el resultado de la consulta y agregar cada cliente a la tabla
        while (rs.next()) {
            int idCliente = rs.getInt("Id_Cliente");
            String nombre = rs.getString("Nombre");
            String apellido = rs.getString("Apellido");
            String cedula = rs.getString("Cedula");
            String telefono = rs.getString("Telefono");

            // Agregar los datos del cliente como una nueva fila en el modelo de tabla
            tableModel.addRow(new Object[] { idCliente, nombre, apellido, cedula, telefono });
        }
    } catch (SQLException e) {
        // Mostrar mensaje de error en caso de fallo
        JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error",
                                      JOptionPane.ERROR_MESSAGE);
    }
}

// Método para eliminar cliente seleccionado
private void eliminarCliente() {
    int filaSeleccionada = tableClientes.getSelectedRow();

    if (filaSeleccionada != -1) {
        int idCliente = (int) tableModel.getValueAt(filaSeleccionada, 0);

        // Confirmación antes de eliminar
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?",
                                                     "Confirmación", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            String sql = "{ CALL sp_eliminarCliente(?) }";
try (CallableStatement stmt = conexion.prepareCall(sql)) {
    stmt.setInt(1, idCliente); // Aquí debes pasar el Id del cliente a eliminar
    int filasAfectadas = stmt.executeUpdate();
    
    if (filasAfectadas > 0) {
        JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente.");
        tableModel.removeRow(filaSeleccionada);
    } else {
        JOptionPane.showMessageDialog(this, "Error: Cliente no encontrado o no se pudo eliminar.");
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
        }        

    }
}
private void editarCliente() {
    int filaSeleccionada = tableClientes.getSelectedRow();

    if (filaSeleccionada != -1) {
        int idCliente = (int) tableModel.getValueAt(filaSeleccionada, 0);
        String nombre = (String) tableModel.getValueAt(filaSeleccionada, 1);
        String apellido = (String) tableModel.getValueAt(filaSeleccionada, 2);
        String cedula = (String) tableModel.getValueAt(filaSeleccionada, 3);
        String telefono = (String) tableModel.getValueAt(filaSeleccionada, 4);

        // Crear campos de texto con valores actuales para edición
        JTextField txtNombre = new JTextField(nombre);
        JTextField txtApellido = new JTextField(apellido);
        JTextField txtCedula = new JTextField(cedula);
        JTextField txtTelefono = new JTextField(telefono);

        // Configuración de diseño y estilo
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        // Agregar etiquetas y campos de texto al panel
        panel.add(new JLabel("Nombre:"), gbc);
        panel.add(txtNombre, gbc);
        panel.add(new JLabel("Apellido:"), gbc);
        panel.add(txtApellido, gbc);
        panel.add(new JLabel("Cédula:"), gbc);
        panel.add(txtCedula, gbc);
        panel.add(new JLabel("Teléfono:"), gbc);
        panel.add(txtTelefono, gbc);

        int opcion = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            // Usar el procedimiento almacenado para actualizar
            String sql = "{CALL sp_actualizarCliente(?, ?, ?, ?, ?)}";

            try (CallableStatement stmt = conexion.prepareCall(sql)) {
                stmt.setInt(1, idCliente);           // pIdCliente
                stmt.setString(2, txtNombre.getText());   // pNombre
                stmt.setString(3, txtApellido.getText()); // pApellido
                stmt.setString(4, txtCedula.getText());   // pCedula
                stmt.setString(5, txtTelefono.getText()); // pTelefono

                // Ejecutar el procedimiento almacenado
                stmt.executeUpdate();

                // Actualizar los datos en la tabla si el procedimiento se ejecutó correctamente
                tableModel.setValueAt(txtNombre.getText(), filaSeleccionada, 1);
                tableModel.setValueAt(txtApellido.getText(), filaSeleccionada, 2);
                tableModel.setValueAt(txtCedula.getText(), filaSeleccionada, 3);
                tableModel.setValueAt(txtTelefono.getText(), filaSeleccionada, 4);
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al editar cliente: " + ex.getMessage(), "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar.", "Advertencia",
                                      JOptionPane.WARNING_MESSAGE);
    }
}

// Método main para iniciar la aplicación
public static void main(String[] args) {
    try {
        // Configuración de conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/hotel";
        String usuario = "root";
        String contrasena = "Fe504550628";
        Connection conexion = DriverManager.getConnection(url, usuario, contrasena);

        // Crear instancia de MostrarClientesView y hacerla visible
        MostrarClientesView mostrarClientesView = new MostrarClientesView(conexion);
        mostrarClientesView.setVisible(true);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.", "Error",
                                      JOptionPane.ERROR_MESSAGE);
    }
}

}
