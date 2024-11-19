import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaAsignarMulta extends JFrame {
    private JTextField txtCedula;
    private JTextField txtValor;
    private JTextField txtTipo;

    private GestorDeUsuario gestorDeUsuario;
    private GestorDeMulta gestorDeMulta;

    public VentanaAsignarMulta(GestorDeUsuario gestorDeUsuario, GestorDeMulta gestorDeMulta) {
        this.gestorDeUsuario = gestorDeUsuario;
        this.gestorDeMulta = gestorDeMulta;

        inicializarVentana();
        configurarInteraccion();
    }

    private void inicializarVentana() {
        setTitle("Asignar o Cobrar Multa");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(202, 210, 197));

        txtCedula = new JTextField(15);
        txtValor = new JTextField(15);
        txtTipo = new JTextField(15);

        panel.add(new JLabel("Cédula del Usuario:"));
        panel.add(txtCedula);
        panel.add(new JLabel("Valor de la Multa:"));
        panel.add(txtValor);
        panel.add(new JLabel("Tipo de Multa:"));
        panel.add(txtTipo);

        JButton btnAsignar = new JButton("Asignar Multa");

        btnAsignar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAsignar.setBackground(new Color(132, 169, 140));
        btnAsignar.setForeground(Color.WHITE);

        panel.add(btnAsignar);

        add(panel);
    }

    private void configurarInteraccion() {
        JButton btnAsignar = (JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(6);

        btnAsignar.addActionListener((ActionEvent e) -> {
            String cedula = txtCedula.getText();
            double valor;
            try {
                valor = Double.parseDouble(txtValor.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Valor de la multa no válido.");
                return;
            }

            String tipo = txtTipo.getText();
            Usuario usuario = gestorDeUsuario.obtenerUsuario(cedula);

            if (usuario != null) {
                if (!usuario.tieneMulta()) {
                    gestorDeMulta.agregarNuevaMulta(usuario, valor, tipo);

                    // Actualizar el estado del usuario a tener multa
                    usuario.setTieneMulta(true);

                    JOptionPane.showMessageDialog(this, "Multa asignada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: El usuario ya tiene una multa asignada.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error: Usuario no encontrado.");
            }
        });
    }
}
