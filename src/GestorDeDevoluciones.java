import java.util.ArrayList;
import java.util.List;

public class GestorDeDevoluciones {
    private List<Devolucion> devoluciones = new ArrayList<>();
    private GestorDeInventario gestorDeInventario;
    private GestorDeUsuario gestorDeUsuario;
    private GestorDePrestamos gestorDePrestamos;

    public GestorDeDevoluciones(GestorDeInventario gestorDeInventario, GestorDeUsuario gestorDeUsuario, GestorDePrestamos gestorDePrestamos) {
        this.gestorDeInventario = gestorDeInventario;
        this.gestorDeUsuario = gestorDeUsuario;
        this.gestorDePrestamos = gestorDePrestamos;
    }

    public void agregarDevolucion(String isbn, String cedulaUsuario, boolean buenEstado) {
        Usuario usuario = gestorDeUsuario.obtenerUsuario(cedulaUsuario);
        if (usuario == null) {
            System.out.println("Error: Usuario no encontrado.");
            return;
        }

        Libro libro = gestorDeInventario.buscarLibroPorISBN(isbn);
        if (libro == null) {
            System.out.println("Error: Libro no encontrado en el inventario.");
            return;
        }

        Prestamo prestamo = gestorDePrestamos.obtenerPrestamoActivo(isbn);
        if (prestamo == null) {
            System.out.println("Error: No hay un préstamo activo para este libro.");
            return;
        }

        // Verificar si la fecha del préstamo ha sido excedida
        if (prestamo.getFechaLimiteDelPrestamo().before(new java.util.Date())) {
            System.out.println("Error: La fecha límite del préstamo ha sido excedida. No se puede devolver el libro.");
            return;
        }

        if (!buenEstado) {
            System.out.println("Error: El libro no se puede devolver porque no está en buen estado.");
            return;
        }

        // Proceder con la devolución
        Devolucion devolucion = new Devolucion(libro, usuario, buenEstado);
        devoluciones.add(devolucion);
        prestamo.finalizarPrestamo();
        gestorDeInventario.actualizarInventario(libro, true);
        System.out.println("Devolución agregada exitosamente: " + devolucion.getIdentificadorDeDevolucion());
    }
}
