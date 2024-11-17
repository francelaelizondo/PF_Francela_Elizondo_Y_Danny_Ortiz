import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioModel {
    private Connection conexion;

    public UsuarioModel(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para verificar el usuario y la contraseña
    public boolean verificarUsuario(String usuario, String contrasena) {
        String sql = "{CALL verificar_Usuario(?, ?)}"; 
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setString(1, usuario);   
            stmt.setString(2, contrasena); 
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("usuario_valido");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Si no se encuentra el usuario o hay un error
    }
}
