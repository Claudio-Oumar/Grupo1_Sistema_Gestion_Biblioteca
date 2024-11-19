import javax.swing.*;

public class Main {

    private static VentanaPrincipal ventanaPrincipal;

    public static void main(String[] args) {
        iniciarAplicacion(); 
    }

    private static void iniciarAplicacion() {
        SwingUtilities.invokeLater(() -> {
            ventanaPrincipal = new VentanaPrincipal(); 
            ventanaPrincipal.setVisible(true);         
        });
    }
}
