import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaPrestarLibro extends JFrame {
    private JTextField txtISBN;
    private JTextField txtCedulaUsuario;
    private JButton btnPrestar;
    private JTextArea areaInventario;

    private GestorDeUsuario gestorDeUsuario;
    private GestorDePrestamos gestorDePrestamos;
    private GestorDeInventario gestorDeInventario;

    public VentanaPrestarLibro(GestorDeUsuario gestorDeUsuario, GestorDePrestamos gestorDePrestamos, GestorDeInventario gestorDeInventario) {
        this.gestorDeUsuario = gestorDeUsuario;
        this.gestorDePrestamos = gestorDePrestamos;
        this.gestorDeInventario = gestorDeInventario;

        inicializarVentana();
        configurarInteraccion();
    }

    private void inicializarVentana() {
        setTitle("Prestar Libro");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(202, 210, 197));

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBackground(new Color(202, 210, 197));

        txtISBN = new JTextField(15);
        txtCedulaUsuario = new JTextField(15);

        panelFormulario.add(new JLabel("ISBN del Libro:"));
        panelFormulario.add(txtISBN);
        panelFormulario.add(new JLabel("Cédula del Usuario:"));
        panelFormulario.add(txtCedulaUsuario);

        btnPrestar = new JButton("Prestar Libro");
        btnPrestar.setFont(new Font("Arial", Font.BOLD, 14));
        btnPrestar.setBackground(new Color(83, 121, 111));
        btnPrestar.setForeground(Color.WHITE);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 10)));
        panelFormulario.add(btnPrestar);

        areaInventario = new JTextArea(10, 20);
        areaInventario.setEditable(false);
        areaInventario.setBackground(new Color(240, 248, 255));
        areaInventario.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(areaInventario);

        StringBuilder inventarioInfo = new StringBuilder("Inventario de Libros:\n\n");
        for (Libro libro : gestorDeInventario.obtenerLibros()) {
            inventarioInfo.append(libro.obtenerDatosDelLibro()).append("\n");
        }

        areaInventario.setText(inventarioInfo.toString());

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private void configurarInteraccion() {
        // Configurar la acción del botón prestar
        btnPrestar.addActionListener((ActionEvent e) -> {
            String isbn = txtISBN.getText();
            String cedulaUsuario = txtCedulaUsuario.getText();

            // Verificar si el usuario existe
            Usuario usuario = gestorDeUsuario.obtenerUsuario(cedulaUsuario);
            if (usuario == null) {
                JOptionPane.showMessageDialog(this,
                        "Usuario no encontrado.",
                        "Error de Usuario",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si el usuario tiene multas pendientes antes de prestar el libro
            if (usuario.tieneMulta()) {
                JOptionPane.showMessageDialog(this,
                        "No se puede prestar el libro debido a que el usuario tiene multas pendientes.",
                        "Multa Pendiente",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si el libro existe en el inventario
            Libro libro = gestorDeInventario.buscarLibroPorISBN(isbn);
            if (libro == null) {
                JOptionPane.showMessageDialog(this,
                        "Libro no encontrado en el inventario.",
                        "Error de Libro",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificar si el libro está disponible
            if (!gestorDeInventario.consultarDisponibilidad(libro)) {
                JOptionPane.showMessageDialog(this,
                        "El libro no está disponible para préstamo.",
                        "Libro No Disponible",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Intentar agregar el préstamo utilizando el gestor de préstamos
            gestorDePrestamos.agregarPrestamo(isbn, cedulaUsuario);
            JOptionPane.showMessageDialog(this,
                    "Préstamo realizado con éxito.",
                    "Préstamo Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerrar la ventana después del mensaje
        });
    }
}
