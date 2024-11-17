import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class ClienteModel {
    private Connection conexion;

    // Constructor que recibe la conexión a la base de datos
    public ClienteModel(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hotel";
        String usuario = "root"; 
        String contrasena = "Fe504550628"; 
        return DriverManager.getConnection(url, usuario, contrasena);
    }

    // Método para insertar un cliente en la base de datos
    public boolean insertarCliente(int idCliente, String nombre, String apellido, String cedula, String telefono) {
        String sql = "{ CALL sp_insertarCliente(?, ?, ?, ?, ?) }"; 

        try (Connection conn = this.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

           
            stmt.setInt(1, idCliente); 
            stmt.setString(2, nombre); 
            stmt.setString(3, apellido); 
            stmt.setString(4, cedula); 
            stmt.setString(5, telefono); 

            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }
   
}

