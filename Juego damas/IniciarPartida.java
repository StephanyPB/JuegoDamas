import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IniciarPartida extends JDialog {

    private JComboBox jcJugador1;
    private JLabel jlJugador1;
    private JComboBox jcJugador2;
    private JLabel jlJugador2;
    private JButton jbOk;
    private JButton jbCancel;
    public static VentanaJuego juego;

    public IniciarPartida() {
        init();
    }

    private void init() {
        setTitle("Iniciar Partida");
        setSize(200, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        jlJugador1 = new JLabel("Jugador 1");
        jcJugador1 = new JComboBox<Jugador>();
        jlJugador2 = new JLabel("Jugador 2");
        jcJugador2 = new JComboBox<Jugador>();

        for (Jugador item : VentanaPrincipal.listaJugadores) {
            jcJugador1.addItem(item.getNombre());
            jcJugador2.addItem(item.getNombre());
        }

        jbOk = new JButton("OK");
        jbCancel = new JButton("Cancel");

        jbOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index1 = jcJugador1.getSelectedIndex();
                int index2 = jcJugador2.getSelectedIndex();

                if(index1==index2)
                {
                    JOptionPane.showMessageDialog(null, "No puedes escoger el mismo jugador para ambos lados");
                }else
                {
                    VentanaPrincipal.jugador1 = VentanaPrincipal.listaJugadores.get(index1);
                    VentanaPrincipal.jugador2 = VentanaPrincipal.listaJugadores.get(index2);
                    
                    VentanaPrincipal.jugador1.setColor(Pieza.WHITE);
                    VentanaPrincipal.jugador2.setColor(Pieza.BLACK);
                    setVisible(false);
                    juego = new VentanaJuego();
                }

            }
        });
        jbCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        add(jlJugador1);
        add(jcJugador1);
        add(jlJugador2);
        add(jcJugador2);
        add(jbOk);
        add(jbCancel);

        setModal(true);
        setVisible(true);
    }

}
