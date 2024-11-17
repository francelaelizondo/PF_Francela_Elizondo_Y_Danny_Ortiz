import javax.swing.JOptionPane;

public class LoginController {
    private LoginView view;
    private UsuarioModel model;

    public LoginController(LoginView view, UsuarioModel model) {
        this.view = view;
        this.model = model;

        view.getBtnLogin().addActionListener(e -> autenticarUsuario());
    }

    private void autenticarUsuario() {
        String usuario = view.getUsuario();  
        String contrasena = view.getContrasena();

        if (model.verificarUsuario(usuario, contrasena)) {
            JOptionPane.showMessageDialog(view, "Inicio de sesión exitoso.");
            new MenuPrincipalView().setVisible(true);
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Usuario o contraseña incorrectos.");
        }
    }
}
