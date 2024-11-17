public class Reservacion {
        private int idReservacion;
        private int idCliente;
        private java.sql.Date fechaLlegada;
        private java.sql.Date fechaSalida;
    
        // Getters y setters
        public int getIdReservacion() {
            return idReservacion;
        }
    
        public void setIdReservacion(int idReservacion) {
            this.idReservacion = idReservacion;
        }
    
        public int getIdCliente() {
            return idCliente;
        }
    
        public void setIdCliente(int idCliente) {
            this.idCliente = idCliente;
        }
    
        public java.sql.Date getFechaLlegada() {
            return fechaLlegada;
        }
    
        public void setFechaLlegada(java.sql.Date fechaLlegada) {
            this.fechaLlegada = fechaLlegada;
        }
    
        public java.sql.Date getFechaSalida() {
            return fechaSalida;
        }
    
        public void setFechaSalida(java.sql.Date fechaSalida) {
            this.fechaSalida = fechaSalida;
        }
    }