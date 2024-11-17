import java.sql.*;

public class ReservacionModel {

    private Connection conexion;

    
    public ReservacionModel(Connection conexion) {
        this.conexion = conexion;
    }

   // Método para insertar una nueva reservación en la base de datos
public boolean insertarReservacion(int idReservacion, int idCliente, java.sql.Date fechaLlegada, java.sql.Date fechaSalida, String tipoHabitacion) {
    String sql = "{CALL sp_insertarReservacion(?, ?, ?, ?, ?)}";  
    try (CallableStatement stmt = conexion.prepareCall(sql)) {
        stmt.setInt(1, idReservacion);          
        stmt.setInt(2, idCliente);              
        stmt.setDate(3, fechaLlegada);         
        stmt.setDate(4, fechaSalida);           
        stmt.setString(5, tipoHabitacion);    
        
        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false; 
    }
}

}
