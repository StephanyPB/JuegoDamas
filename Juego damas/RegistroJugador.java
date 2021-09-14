import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistroJugador extends JDialog {

    private JTextField jtxNombre;
    private JLabel jlNombre;
    private JButton jbOk;
    private JButton jbCancel;
    private Jugador jugador;

    public RegistroJugador() {

    }

    public Jugador crearJugador() {
        jugador = new Jugador();
        init();
        return jugador;
    }

    private void init() {
        setTitle("Registro de jugadores");
        setSize(200, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        jlNombre = new JLabel("Nombre");
        jtxNombre = new JTextField(10);
        jbOk = new JButton("OK");
        jbCancel = new JButton("Cancel");

        jbOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                jugador.setId(VentanaPrincipal.listaJugadores.size() + 1);
                jugador.setNombre(jtxNombre.getText());
                setVisible(false);
            }
        });
        jbCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        add(jlNombre);
        add(jtxNombre);
        add(jbOk);
        add(jbCancel);

        setModal(true);
        setVisible(true);
    }
}
