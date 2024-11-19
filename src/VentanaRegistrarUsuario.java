import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaRegistrarUsuario extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCedula;
    private JTextField txtEdad;
    private JTextField txtDireccion;
    private GestorDeUsuario gestorDeUsuario;

    public VentanaRegistrarUsuario(GestorDeUsuario gestorDeUsuario) {
        this.gestorDeUsuario = gestorDeUsuario;

        inicializarVentana();
        configurarInteraccion();
    }

    private void inicializarVentana() {
        setTitle("Registrar Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(202, 210, 197));

        Font fuente = new Font("Arial", Font.BOLD, 14);

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCedula = new JTextField();
        txtEdad = new JTextField();
        txtDireccion = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Cédula:"));
        panel.add(txtCedula);
        panel.add(new JLabel("Edad:"));
        panel.add(txtEdad);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(fuente);
        btnRegistrar.setBackground(new Color(132, 169, 140));
        btnRegistrar.setForeground(Color.WHITE);
        panel.add(new JLabel()); // Espacio vacío
        panel.add(btnRegistrar);

        add(panel);
    }

    private void configurarInteraccion() {
        JButton btnRegistrar = (JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(11);

        btnRegistrar.addActionListener((ActionEvent e) -> {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String cedula = txtCedula.getText();

            // Verificar si ya existe un usuario con la misma cédula
            Usuario usuarioExistente = gestorDeUsuario.obtenerUsuario(cedula);
            if (usuarioExistente != null) {
                JOptionPane.showMessageDialog(this,
                        "El usuario con la cédula ingresada ya está registrado.",
                        "Usuario Ya Registrado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int edad;
            try {
                edad = Integer.parseInt(txtEdad.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Por favor ingrese una edad válida.",
                        "Edad No Válida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String direccion = txtDireccion.getText();

            Usuario usuario = new Usuario(nombre, apellido, cedula, edad, direccion);
            gestorDeUsuario.registrarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            dispose();
        });
    }
}
