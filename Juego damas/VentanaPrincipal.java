import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    public static ArrayList<Jugador> listaJugadores;
    public static Jugador jugador1;
    public static Jugador jugador2;
    private JMenuBar menuBar;
    private JMenu mInicio;
    private JMenuItem miIniciarPartida;
    private JMenuItem miRegistrar;
    private JMenuItem miConsultarJugadores;
    public VentanaPrincipal() {
        init();
    }

    private void init() {

        listaJugadores = new ArrayList<Jugador>();
        listaJugadores = Archivo.obtenerInformacion();

        setLayout(null); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Damas Steph");

        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        mInicio = new JMenu("Inicio");
        menuBar.add(mInicio);

        miIniciarPartida = new JMenuItem("Iniciar Partida");
        miRegistrar = new JMenuItem("Registrar Jugadores");
        miConsultarJugadores = new JMenuItem("Consultar Jugadores");

        miIniciarPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IniciarPartida iniciar = new IniciarPartida();
            }
        });

        miRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroJugador rJugador = new RegistroJugador();
                Jugador jugador = rJugador.crearJugador();

                if (jugador != null && jugador.getId() > 0) {
                    listaJugadores.add(jugador);
                    Archivo.guardarInformacion(listaJugadores);
                }
            }
        });

        miConsultarJugadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultaJugador consulta = new ConsultaJugador();
            }
        });

        mInicio.add(miIniciarPartida);
        mInicio.add(miRegistrar);
        mInicio.add(miConsultarJugadores);

        setVisible(true);
    }
}