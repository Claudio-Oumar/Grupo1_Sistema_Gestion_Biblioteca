import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;


public class VentanaPrincipal extends JFrame {
    private GestorDeUsuario gestorDeUsuario;
    private GestorDeInventario gestorDeInventario;
    private GestorDePrestamos gestorDePrestamos;
    private GestorDeDevoluciones gestorDeDevoluciones;
    private GestorDeMulta gestorDeMulta;

    public VentanaPrincipal() {
        inicializarGestores();
        inicializarVentana();
        configurarInteraccion();
    }

    private void inicializarGestores() {
        gestorDeUsuario = new GestorDeUsuario();
        gestorDeInventario = new GestorDeInventario();
        gestorDePrestamos = new GestorDePrestamos(gestorDeInventario, gestorDeUsuario);
        gestorDeDevoluciones = new GestorDeDevoluciones(gestorDeInventario, gestorDeUsuario, gestorDePrestamos);
        gestorDeMulta = new GestorDeMulta(gestorDeUsuario);
    }

    private void inicializarVentana() {
        setTitle("Sistema de Biblioteca");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(202, 210, 197));

        Font fuente = new Font("Arial", Font.BOLD, 16);

        JButton btnRegistrarUsuario = new JButton("Registrar Usuario", cargarIcono("https://img.icons8.com/ios/50/000000/add-user-male--v1.png"));
        configurarBoton(btnRegistrarUsuario, fuente, new Color(132, 169, 140));

        JButton btnRegistrarLibro = new JButton("Registrar Libro", cargarIcono("https://img.icons8.com/ios/50/000000/add-book.png"));
        configurarBoton(btnRegistrarLibro, fuente, new Color(132, 169, 140));

        JButton btnPrestarLibro = new JButton("Prestar Libro", cargarIcono("https://img.icons8.com/ios/50/000000/borrow-book.png"));
        configurarBoton(btnPrestarLibro, fuente, new Color(83, 121, 111));

        JButton btnDevolverLibro = new JButton("Devolver Libro", cargarIcono("https://img.icons8.com/ios/50/000000/return-book.png"));
        configurarBoton(btnDevolverLibro, fuente, new Color(83, 121, 111));

        JButton btnAsignarMulta = new JButton("Asignar Multa", cargarIcono("https://img.icons8.com/ios/50/000000/fine.png"));
        configurarBoton(btnAsignarMulta, fuente, new Color(53, 79, 82));


        panel.add(btnRegistrarUsuario);
        panel.add(btnRegistrarLibro);
        panel.add(btnPrestarLibro);
        panel.add(btnDevolverLibro);
        panel.add(btnAsignarMulta);

        add(panel);
    }

    private void configurarInteraccion() {
        JButton btnRegistrarUsuario = (JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(0);
        JButton btnRegistrarLibro = (JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(1);
        JButton btnPrestarLibro = (JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(2);
        JButton btnDevolverLibro = (JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(3);
        JButton btnAsignarMulta = (JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(4);

        btnRegistrarUsuario.addActionListener((ActionEvent e) -> new VentanaRegistrarUsuario(gestorDeUsuario).setVisible(true));
        btnRegistrarLibro.addActionListener((ActionEvent e) -> new VentanaRegistrarLibro(gestorDeInventario).setVisible(true));
        btnPrestarLibro.addActionListener((ActionEvent e) -> new VentanaPrestarLibro(gestorDeUsuario, gestorDePrestamos,gestorDeInventario).setVisible(true));
        btnDevolverLibro.addActionListener((ActionEvent e) -> new VentanaDevolverLibro(gestorDeUsuario, gestorDeDevoluciones, gestorDePrestamos, gestorDeMulta).setVisible(true));
        btnAsignarMulta.addActionListener((ActionEvent e) -> new VentanaAsignarMulta(gestorDeUsuario, gestorDeMulta).setVisible(true));
    }

    private void configurarBoton(JButton boton, Font fuente, Color color) {
        boton.setFont(fuente);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    private ImageIcon cargarIcono(String url) {
        try {
            return new ImageIcon(new URL(url));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono desde la URL: " + url);
            return null;
        }
    }
}
