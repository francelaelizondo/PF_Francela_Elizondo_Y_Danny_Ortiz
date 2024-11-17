import java.sql.Connection;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Establecer la conexión con la base de datos
            Connection conexion = DatabaseConnection.getConnection();
            UsuarioModel usuarioModel = new UsuarioModel(conexion);

            // Inicializar la vista y el controlador para el inicio de sesión
            LoginView loginView = new LoginView();  // La vista que tiene los campos de usuario y contraseña
            LoginController loginController = new LoginController(loginView, usuarioModel);  // El controlador que maneja la lógica

            // Mostrar la ventana de inicio de sesión
            loginView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();  // Captura los errores
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
        }
    }
}
