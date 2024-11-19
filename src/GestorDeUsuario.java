import java.util.ArrayList;
import java.util.List;

public class GestorDeUsuario {
    private List<Usuario> usuarios = new ArrayList<>();

    // Método para registrar un nuevo usuario
    public void registrarUsuario(Usuario usuario) {
        if (obtenerUsuario(usuario.getCedula()) == null) {
            usuarios.add(usuario);
            System.out.println("Usuario registrado: " + usuario.obtenerDatosDelUsuario());
        } else {
            System.out.println("Error: Usuario con cédula " + usuario.getCedula() + " ya está registrado.");
        }
    }

    // Método para obtener un usuario por cédula
    public Usuario obtenerUsuario(String cedula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        return null;
    }
}
