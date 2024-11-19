public class Libro {
    private String titulo;
    private String autor;
    private String editorial;
    private String genero;
    private String idioma;
    private String fechaDePublicacion;
    private String isbn;
    private boolean disponible;

    public Libro(String titulo, String autor, String editorial, String genero, String idioma, String fechaDePublicacion, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.genero = genero;
        this.idioma = idioma;
        this.fechaDePublicacion = fechaDePublicacion;
        this.isbn = isbn;
        this.disponible = true; // Por defecto, el libro está disponible
    }

    public String obtenerDatosDelLibro() {
        return "Título: " + titulo + ", Autor: " + autor + ", ISBN: " + isbn + ", Disponible: " + (disponible ? "Sí" : "No");
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getISBN() {
        return isbn;
    }
}
