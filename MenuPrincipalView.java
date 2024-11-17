import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MenuPrincipalView extends JFrame {
    private JButton btnAddClient, btnUpdateClient, btnConsultClient, btnDeleteClient;
    private JButton btnRegisterReservation, btnUpdateReservation, btnConsultReservation, btnDeleteReservation;
    private Connection conexion;
    private JButton btnViewReservations, btnViewClients;

    public MenuPrincipalView() {
        setTitle("Menú Principal");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Configuración de la imagen de fondo con efecto difuminado
        JLabel background = new JLabel(new ImageIcon("hotel.jpg")); // Usa una imagen ya difuminada
        background.setLayout(null); // Layout nulo para permitir posiciones absolutas
        setContentPane(background);

        // Etiqueta de bienvenida
        JLabel lblWelcome = new JLabel("Bienvenido al sistema de gestión del hotel", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBounds(100, 20, 400, 30); // Posición y tamaño de la etiqueta
        background.add(lblWelcome);

        // Configuración de las posiciones y tamaño de los botones
        int column1X = 100; // Posición X de la primera columna (Clientes)
        int column2X = 350; // Posición X de la segunda columna (Reservaciones)
        int buttonWidth = 150; // Ancho de los botones
        int buttonHeight = 30; // Altura de los botones
        int buttonSpacing = 50; // Espacio vertical entre botones

        // Colores personalizados para los botones
        Color buttonColor = new Color(106, 154, 118); // Un color azul claro para el fondo del botón
        Color textColor = Color.WHITE; // Blanco para el texto

        // Botones de clientes en la primera columna
        btnAddClient = new JButton("Añadir Cliente");
        configurarBoton(btnAddClient, column1X, 100, buttonWidth, buttonHeight, buttonColor, textColor);
        background.add(btnAddClient);



        btnViewClients = new JButton("Ver Lista de Clientes");
        configurarBoton(btnViewClients, column1X, 100 + buttonSpacing * 4, buttonWidth, buttonHeight, buttonColor,
                textColor);
        background.add(btnViewClients);

        // Botones de reservaciones en la segunda columna
        btnRegisterReservation = new JButton("Registrar Reservación");
        configurarBoton(btnRegisterReservation, column2X, 100, buttonWidth, buttonHeight, buttonColor, textColor);
        background.add(btnRegisterReservation);

      
        // Dentro del constructor MenuPrincipalView:
        btnViewReservations = new JButton("Ver Lista de Reservaciones");
        configurarBoton(btnViewReservations, column2X, 100 + buttonSpacing * 4, buttonWidth, buttonHeight, buttonColor,
                textColor);
        background.add(btnViewReservations);

        // Inicializar la conexión a la base de datos
        try {
            String url = "jdbc:mysql://localhost:3306/hotel";
            String user = "root";
            String password = "Fe504550628";
            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Agregar ActionListeners para cada botón
        btnAddClient.addActionListener(e -> {
            RegistroClienteView registroClienteView = new RegistroClienteView(conexion);
            registroClienteView.setVisible(true);
        });
   
            btnViewClients.addActionListener(e -> {
                MostrarClientesView mostrarClientesView = new MostrarClientesView(conexion);
                mostrarClientesView.setVisible(true);
            
        });
        btnRegisterReservation.addActionListener(e -> {
            RegistroReservacionView registroReservacionView = new RegistroReservacionView(conexion);
            registroReservacionView.setVisible(true);
        });
     
        // Acción de los botones
        btnViewReservations.addActionListener(e -> {
            MostrarReservacionesView mostrarReservacionesView = new MostrarReservacionesView(conexion);
            mostrarReservacionesView.setVisible(true);
        });

    }

    // Método para configurar el color y tamaño de los botones
    private void configurarBoton(JButton boton, int x, int y, int width, int height, Color backgroundColor,
            Color textColor) {
        boton.setBounds(x, y, width, height);
        boton.setBackground(backgroundColor); // Color de fondo
        boton.setForeground(textColor); // Color del texto
        boton.setFocusPainted(false); // Elimina el borde de enfoque
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipalView menuPrincipalView = new MenuPrincipalView();
            menuPrincipalView.setVisible(true);
        });
    }
}
