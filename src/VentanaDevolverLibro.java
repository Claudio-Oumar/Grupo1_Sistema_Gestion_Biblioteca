import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class VentanaDevolverLibro extends JFrame {
    private JTextField txtISBN;
    private JTextField txtCedulaUsuario;
    private JLabel lblFechaLimite;
    private JRadioButton rbBuenEstadoSi;
    private JRadioButton rbBuenEstadoNo;
    private JSpinner fechaDevolucionSpinner;
    private JButton btnRecuperarFechaLimite;
    private JButton btnDevolver;

    private GestorDeUsuario gestorDeUsuario;
    private GestorDeDevoluciones gestorDeDevoluciones;
    private GestorDePrestamos gestorDePrestamos;
    private GestorDeMulta gestorDeMulta;

    public VentanaDevolverLibro(GestorDeUsuario gestorDeUsuario,
                                GestorDeDevoluciones gestorDeDevoluciones, GestorDePrestamos gestorDePrestamos,
                                GestorDeMulta gestorDeMulta) {
        this.gestorDeUsuario = gestorDeUsuario;
        this.gestorDeDevoluciones = gestorDeDevoluciones;
        this.gestorDePrestamos = gestorDePrestamos;
        this.gestorDeMulta = gestorDeMulta;

        inicializarVentana();
        configurarInteraccion();
    }

    private void inicializarVentana() {
        setTitle("Devolver Libro");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(202, 210, 197));

        txtISBN = new JTextField(20);
        txtCedulaUsuario = new JTextField(20);
        lblFechaLimite = new JLabel("Fecha Límite: No recuperada");

        rbBuenEstadoSi = new JRadioButton("Sí");
        rbBuenEstadoNo = new JRadioButton("No");
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(rbBuenEstadoSi);
        grupoEstado.add(rbBuenEstadoNo);

        panel.add(new JLabel("Cédula del Usuario:"));
        panel.add(txtCedulaUsuario);
        panel.add(new JLabel("ISBN del Libro:"));
        panel.add(txtISBN);

        btnRecuperarFechaLimite = new JButton("Recuperar Fecha Límite");
        btnRecuperarFechaLimite.setFont(new Font("Arial", Font.BOLD, 14));
        btnRecuperarFechaLimite.setBackground(new Color(96, 138, 89));
        btnRecuperarFechaLimite.setForeground(Color.WHITE);
        panel.add(btnRecuperarFechaLimite);
        panel.add(lblFechaLimite);

        panel.add(new JLabel("¿El libro está en buen estado?"));
        panel.add(rbBuenEstadoSi);
        panel.add(rbBuenEstadoNo);

        panel.add(new JLabel("Seleccione la fecha de devolución:"));
        SpinnerDateModel dateModel = new SpinnerDateModel();
        fechaDevolucionSpinner = new JSpinner(dateModel);
        fechaDevolucionSpinner.setEditor(new JSpinner.DateEditor(fechaDevolucionSpinner, "dd/MM/yyyy"));
        panel.add(fechaDevolucionSpinner);

        btnDevolver = new JButton("Devolver");
        btnDevolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnDevolver.setBackground(new Color(96, 138, 89));
        btnDevolver.setForeground(Color.WHITE);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnDevolver);

        add(panel);
    }

    private void configurarInteraccion() {
        // Acción del botón "Recuperar Fecha Límite"
        btnRecuperarFechaLimite.addActionListener((ActionEvent e) -> {
            String isbn = txtISBN.getText();
            String cedulaUsuario = txtCedulaUsuario.getText();

            Prestamo prestamo = gestorDePrestamos.obtenerPrestamoActivo(isbn);
            if (prestamo != null && prestamo.getUsuario().getCedula().equals(cedulaUsuario)) {
                lblFechaLimite.setText("Fecha Límite: " + prestamo.getFechaLimiteDelPrestamo().toString());
            } else {
                lblFechaLimite.setText("No se encontró un préstamo activo para este libro.");
                JOptionPane.showMessageDialog(this,
                        "No existe un préstamo del libro con el ISBN ingresado para este usuario.",
                        "Préstamo No Encontrado",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Acción del botón "Devolver"
        btnDevolver.addActionListener((ActionEvent e) -> {
            String isbn = txtISBN.getText();
            String cedulaUsuario = txtCedulaUsuario.getText();
            boolean buenEstado = rbBuenEstadoSi.isSelected();

            // Verificar si hay un préstamo activo para el libro ingresado y si coincide con el usuario
            Prestamo prestamo = gestorDePrestamos.obtenerPrestamoActivo(isbn);
            if (prestamo == null || !prestamo.getUsuario().getCedula().equals(cedulaUsuario)) {
                JOptionPane.showMessageDialog(this,
                        "No existe un préstamo del libro con el ISBN ingresado para este usuario.",
                        "Préstamo No Encontrado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Obtener la fecha de devolución seleccionada
            Date fechaDevolucion = (Date) fechaDevolucionSpinner.getValue();
            Date fechaLimite = prestamo.getFechaLimiteDelPrestamo();

            // Verificar si la fecha de devolución es mayor que la fecha límite
            if (fechaDevolucion.after(fechaLimite)) {
                int opcion = JOptionPane.showConfirmDialog(this,
                        "Se ha excedido la fecha límite de devolución. ¿Desea aplicar una multa al usuario?",
                        "Fecha Excedida",
                        JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    Usuario usuario = gestorDeUsuario.obtenerUsuario(cedulaUsuario);
                    if (usuario != null) {
                        VentanaAsignarMulta ventanaAsignarMulta = new VentanaAsignarMulta(gestorDeUsuario, gestorDeMulta);
                        ventanaAsignarMulta.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
                    }
                }
                return;
            }

            // Proceder con la devolución si no hay problemas
            if (buenEstado) {
                gestorDeDevoluciones.agregarDevolucion(isbn, cedulaUsuario, buenEstado);
                JOptionPane.showMessageDialog(this, "Devolución realizada con éxito.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo realizar la devolución. El libro no está en buen estado.",
                        "Error de Devolución",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
