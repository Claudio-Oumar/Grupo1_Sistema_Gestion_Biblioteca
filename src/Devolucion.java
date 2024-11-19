import java.util.Date;

public class Devolucion {
    private Libro libro;
    private Date fechaDeDevolucion;
    private String identificadorDeDevolucion;
    private String estado;
    private Usuario usuario;
    private boolean buenEstado;

    public Devolucion(Libro libro, Usuario usuario, boolean buenEstado) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaDeDevolucion = new Date();
        this.identificadorDeDevolucion = "DEV-" + System.currentTimeMillis();
        this.buenEstado = buenEstado;
        this.estado = buenEstado ? "Completado" : "Pendiente";
    }

    public Libro getLibro() {
        return libro;
    }

    public String getIdentificadorDeDevolucion() {
        return identificadorDeDevolucion;
    }


}
