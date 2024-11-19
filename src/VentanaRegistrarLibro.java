import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaRegistrarLibro extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JTextField txtGenero;
    private JTextField txtIdioma;
    private JTextField txtFechaDePublicacion;
    private JTextField txtISBN;
    private JButton btnRegistrar;
    private GestorDeInventario gestorDeInventario;

    public VentanaRegistrarLibro(GestorDeInventario gestorDeInventario) {
        this.gestorDeInventario = gestorDeInventario;

        inicializarVentana();
        configurarInteraccion();
    }

    private void inicializarVentana() {
        setTitle("Registrar Libro");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));
        panel.setBackground(new Color(202, 210, 197)); // Fondo gris claro
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtTitulo = new JTextField(20);
        txtAutor = new JTextField(20);
        txtEditorial = new JTextField(20);
        txtGenero = new JTextField(20);
        txtIdioma = new JTextField(20);
        txtFechaDePublicacion = new JTextField(20);
        txtISBN = new JTextField(20);

        panel.add(new JLabel("Título del Libro:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Autor del Libro:"));
        panel.add(txtAutor);
        panel.add(new JLabel("Editorial del Libro:"));
        panel.add(txtEditorial);
        panel.add(new JLabel("Género del Libro:"));
        panel.add(txtGenero);
        panel.add(new JLabel("Idioma del Libro:"));
        panel.add(txtIdioma);
        panel.add(new JLabel("Fecha de Publicación del Libro:"));
        panel.add(txtFechaDePublicacion);
        panel.add(new JLabel("ISBN del Libro:"));
        panel.add(txtISBN);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setBackground(new Color(34, 139, 34)); // Verde
        btnRegistrar.setForeground(Color.WHITE);
        panel.add(btnRegistrar);

        add(panel);
    }

    private void configurarInteraccion() {
        // Configurar la acción del botón registrar
        btnRegistrar.addActionListener((ActionEvent e) -> {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            String editorial = txtEditorial.getText();
            String genero = txtGenero.getText();
            String idioma = txtIdioma.getText();
            String fechaDePublicacion = txtFechaDePublicacion.getText();
            String isbn = txtISBN.getText();

            // Verificar si ya existe un libro con el mismo ISBN en el inventario
            Libro libroExistente = gestorDeInventario.buscarLibroPorISBN(isbn);
            if (libroExistente != null) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe un libro con el ISBN ingresado.",
                        "Error de Registro",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Si el ISBN es único, agregar el nuevo libro al inventario
            Libro nuevoLibro = new Libro(titulo, autor, editorial, genero, idioma, fechaDePublicacion, isbn);
            gestorDeInventario.agregarLibro(nuevoLibro);
            JOptionPane.showMessageDialog(this,
                    "Libro registrado exitosamente.",
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Cerrar la ventana después del registro exitoso
        });
    }
}
