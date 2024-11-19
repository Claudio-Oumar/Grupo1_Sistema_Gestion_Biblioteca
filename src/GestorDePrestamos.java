import java.util.ArrayList;
import java.util.List;

public class GestorDePrestamos {
    private List<Prestamo> prestamos = new ArrayList<>();
    private GestorDeInventario gestorDeInventario;
    private GestorDeUsuario gestorDeUsuario;

    public GestorDePrestamos(GestorDeInventario gestorDeInventario, GestorDeUsuario gestorDeUsuario) {
        this.gestorDeInventario = gestorDeInventario;
        this.gestorDeUsuario = gestorDeUsuario;
    }

    public void agregarPrestamo(String isbn, String cedulaUsuario) {
        Usuario usuario = gestorDeUsuario.obtenerUsuario(cedulaUsuario);
        if (usuario == null) {
            System.out.println("Error: Usuario no encontrado.");
            return;
        }
        if (usuario.tieneMulta()) {
            System.out.println("Error: El usuario tiene multas pendientes y no puede realizar préstamos.");
            return;
        }

        Libro libro = gestorDeInventario.buscarLibroPorISBN(isbn);
        if (libro == null) {
            System.out.println("Error: Libro no encontrado.");
            return;
        }
        if (!gestorDeInventario.consultarDisponibilidad(libro)) {
            System.out.println("Error: El libro no está disponible para préstamo.");
            return;
        }

        Prestamo prestamo = new Prestamo(libro, usuario);
        prestamos.add(prestamo);
        gestorDeInventario.actualizarInventario(libro, false);
        System.out.println("Préstamo agregado exitosamente: " + prestamo.obtenerDatosDePrestamo());
    }
    public Prestamo obtenerPrestamoActivo(String isbn) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibro().getISBN().equals(isbn) && prestamo.getEstado().equals("activo")) {
                return prestamo;
            }
        }
        return null;
    }
}
