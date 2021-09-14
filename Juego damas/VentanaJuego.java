import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaJuego extends JPanel {

    public VentanaJuego() {
        init();
    }

    //Abre una ventana mostrando un panel de Checkers; el programa termina cuando el usuario cierra la ventana.

    public static JButton jbJuegoNuevo; // Boton para iniciar juego
    public static JButton jbRendirse; // Boton que el jugador puede precionar para rendirse
    public static JButton jbEmpatar; //Declarar empate en caso que no se tenga mas movimientos

    private JLabel jlJugador1;
    private JLabel jlJugador2;
    public static JTextArea infoTextArea;
    private Tablero tablero;
    public static JFrame contenedor;

    /*
     El constructor crea el tablero (que a su vez crea y gestiona los botones y la etiqueta
     del mensaje), añade todos los componentes y establece los límites de los componentes.
     */
    public void init() {
        contenedor = new JFrame("Damas Steph"); // Nombre de la ventana
        infoTextArea = new JTextArea();//dice todo lo que va pasando en el juego

        setLayout(null); //Hago el dise;o de la pantalla
        setPreferredSize(new Dimension(1000, 1000));
        setBackground(new Color(128, 64, 0)); // Fondo de un color madera

        //Cree los componentes y añádo los al panel. 

        tablero = new Tablero(); //constructor del tablero también crea los botones y el labe.
        add(tablero);

        jbJuegoNuevo = new JButton("Juego Nuevo");
        jbJuegoNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tablero.nuevoJuego();
                jbJuegoNuevo.setEnabled(false);
                jbRendirse.setEnabled(true);
                jbEmpatar.setEnabled(true);
            }
        });
        jbJuegoNuevo.setEnabled(false);

        jbRendirse = new JButton("Rendirse");
        jbRendirse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null,
                        "Estas seguro que deseas rendirte?") == JOptionPane.YES_OPTION) {
                    String nombre = tablero.rendirse();
                    jbJuegoNuevo.setEnabled(true);
                    jbRendirse.setEnabled(false);
                    jbEmpatar.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "El jugador " + nombre + "se rindió.");
                    contenedor.setVisible(false);
                }
            }
        });

        jbEmpatar = new JButton("Declarar Empate");
        jbEmpatar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (JOptionPane.showConfirmDialog(null, "Estas seguro que deseas empatar?") == JOptionPane.YES_OPTION) {
                    VentanaPrincipal.jugador1.setEmpates(VentanaPrincipal.jugador1.getEmpates() + 1);
                    VentanaPrincipal.jugador2.setEmpates(VentanaPrincipal.jugador2.getEmpates() + 1);
                    appendInfoTextArea("Se declaro un empate");
                    Archivo.guardarInformacion(VentanaPrincipal.listaJugadores);
                    VentanaPrincipal.listaJugadores = Archivo.obtenerInformacion();

                    VentanaPrincipal.jugador1 = new Jugador();
                    VentanaPrincipal.jugador2 = new Jugador();
                    contenedor.setVisible(false);
                }

            }
        });
        jlJugador1 = new JLabel("Jugador 1");
        jlJugador2 = new JLabel("Jugador 2");

        jlJugador1.setForeground(Color.WHITE);
        jlJugador2.setForeground(Color.WHITE);

        jlJugador1.setFont(new Font("Verdana", Font.PLAIN, 18));
        jlJugador2.setFont(new Font("Verdana", Font.PLAIN, 18));

        jlJugador1.setText(VentanaPrincipal.jugador1.getNombre());
        jlJugador2.setText(VentanaPrincipal.jugador2.getNombre());

        tablero.setBounds(40, 40, 644, 644);
        jbJuegoNuevo.setBounds(700, 300, 120, 30);
        jbRendirse.setBounds(700, 360, 120, 30);
        jbEmpatar.setBounds(700, 420, 120, 30);
        jlJugador1.setBounds(700, 600, 120, 30);
        jlJugador2.setBounds(700, 60, 120, 30);
        
        contenedor.setContentPane(this);
        contenedor.pack();
        Dimension tamanioPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        
        infoTextArea.setBackground(Color.CYAN);
        infoTextArea.setBounds(0, 690, tamanioPantalla.width, (int) (tamanioPantalla.height * 0.20)); // solo quiero el
                                                                                                      // 20 de abajo

        add(jbJuegoNuevo);
        add(jbRendirse);
        add(jbEmpatar);
        add(infoTextArea);
        add(jlJugador1);
        add(jlJugador2);
       
        contenedor.setLocation((tamanioPantalla.width - contenedor.getWidth()) / 2,
                (tamanioPantalla.height - contenedor.getHeight()) / 2);
        
        contenedor.setResizable(false);
        contenedor.setVisible(true);
    } 

    public static void appendInfoTextArea(String texto) {
        infoTextArea.append("\n" + texto);
    }

    //Esta clase hace el trabajo de permitir a los usuarios jugar a las damas, muestra el tablero de damas.
}
