import java.util.Date;
import java.util.Calendar;

public class Prestamo {
    private Libro libro;
    private Usuario usuario;
    private Date fechaPrestamo;
    private Date fechaLimiteDelPrestamo;
    private String identificadorDelPrestamo;
    private String estado; // Nuevo atributo para representar el estado del préstamo ("activo" o "terminado")

    public Prestamo(Libro libro, Usuario usuario) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = new Date(); // Fecha actual como fecha de préstamo
        this.identificadorDelPrestamo = "PREST-" + System.currentTimeMillis();
        this.estado = "activo"; // El préstamo comienza en estado activo

        // Calcular la fecha límite del préstamo (2 semanas después del préstamo)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaPrestamo);
        calendar.add(Calendar.DAY_OF_MONTH, 14); // Añadir 14 días
        this.fechaLimiteDelPrestamo = calendar.getTime();
    }

    // Obtener los datos del préstamo
    public String obtenerDatosDePrestamo() {
        return "Préstamo ID: " + identificadorDelPrestamo +
                ", Libro: " + libro.obtenerDatosDelLibro() +
                ", Usuario: " + usuario.obtenerDatosDelUsuario() +
                ", Fecha de Préstamo: " + fechaPrestamo +
                ", Fecha Límite de Devolución: " + fechaLimiteDelPrestamo +
                ", Estado: " + estado;
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void finalizarPrestamo() {
        this.estado = "terminado";
        System.out.println("Préstamo finalizado: " + identificadorDelPrestamo);
    }

    public String getIdentificadorDelPrestamo() {
        return identificadorDelPrestamo;
    }

    public Date getFechaLimiteDelPrestamo() {
        return fechaLimiteDelPrestamo;
    }
}
