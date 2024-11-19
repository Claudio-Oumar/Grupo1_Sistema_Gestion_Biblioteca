import java.util.ArrayList;
import java.util.List;

public class GestorDeInventario {
    private List<Libro> libros = new ArrayList<>();

    public void agregarLibro(Libro libro) {
        if (buscarLibroPorISBN(libro.getISBN()) == null) {
            libros.add(libro);
            System.out.println("Libro agregado al inventario: " + libro.obtenerDatosDelLibro());
        } else {
            System.out.println("Error: Libro con ISBN " + libro.getISBN() + " ya está registrado.");
        }
    }

    public Libro buscarLibroPorISBN(String isbn) {
        for (Libro libro : libros) {
            if (libro.getISBN().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    public boolean consultarDisponibilidad(Libro libro) {
        return libro.isDisponible();
    }

    public void actualizarInventario(Libro libro, boolean disponibilidad) {
        libro.setDisponible(disponibilidad);
        System.out.println("Inventario actualizado para el libro: " + libro.getISBN() + ", Disponible: " + disponibilidad);
    }

    public List<Libro> obtenerLibros() {
        return libros;
    }
}
