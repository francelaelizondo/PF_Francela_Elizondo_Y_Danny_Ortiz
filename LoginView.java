import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUsuario;  
    private JPasswordField txtContrasena;  
    private JButton btnLogin; 

    public LoginView() {
        setTitle("Inicio de Sesión");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        setLocationRelativeTo(null);

        // Usamos null layout para tener control total sobre los componentes
        setLayout(null);

     
        setContentPane(new JLabel(new ImageIcon("FONDOINICIO.jpg"))); 

       
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(150, 80, 100, 25);  
        lblUsuario.setForeground(Color.WHITE);  // Cambiar color del texto
        add(lblUsuario);

        // Campo de texto para ingresar el usuario
        txtUsuario = new JTextField();
        txtUsuario.setBounds(150, 110, 200, 25);  
        txtUsuario.setBackground(new Color(255, 255, 255, 200));  
        txtUsuario.setForeground(Color.BLACK);  
        add(txtUsuario);

        // Etiqueta de "Contraseña"
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setBounds(150, 170, 100, 25);  
        lblContraseña.setForeground(Color.WHITE); 
        add(lblContraseña);

        // Campo de texto para ingresar la contraseña
        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(150, 200, 200, 25);  
        txtContrasena.setBackground(new Color(255, 255, 255, 200));  
        txtContrasena.setForeground(Color.BLACK);  
        add(txtContrasena);

        // Botón de login
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(150, 250, 200, 40);  
        btnLogin.setBackground(new Color(200, 150, 100));  
        btnLogin.setForeground(Color.WHITE);  
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));  
        add(btnLogin);
    }

    // Obtener el usuario
    public String getUsuario() {
        return txtUsuario.getText();
    }

    // Obtener la contraseña
    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    // Obtener el botón de login
    public JButton getBtnLogin() {
        return btnLogin;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
        });
    }
}
