import java.util.ArrayList;
import java.util.List;

public class GestorDeMulta {
    private List<Multa> multas = new ArrayList<>();
    private GestorDeUsuario gestorDeUsuario;

    public GestorDeMulta(GestorDeUsuario gestorDeUsuario) {
        this.gestorDeUsuario = gestorDeUsuario;
    }

    public void agregarNuevaMulta(Usuario usuario, double valor, String tipo) {
        if (usuario != null && !usuario.tieneMulta()) {
            Multa multa = new Multa(usuario, valor, tipo);
            multas.add(multa);
            System.out.println("Multa asignada a " + usuario.obtenerDatosDelUsuario());
        } else {
            System.out.println("Error: Usuario no válido o ya tiene una multa.");
        }
    }

}
