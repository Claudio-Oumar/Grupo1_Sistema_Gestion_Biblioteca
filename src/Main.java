import javax.swing.*;

public class Main {

    // Atributo para la ventana principal
    private static VentanaPrincipal ventanaPrincipal;

    public static void main(String[] args) {
        iniciarAplicacion(); // Método para iniciar la aplicación
    }

    // Método para iniciar la aplicación
    private static void iniciarAplicacion() {
        // Inicia la aplicación en el hilo de eventos de Swing para asegurar una experiencia de UI fluida.
        SwingUtilities.invokeLater(() -> {
            ventanaPrincipal = new VentanaPrincipal();  // Inicializa la ventana principal como atributo
            ventanaPrincipal.setVisible(true);          // Hacer visible la ventana
        });
    }
}
