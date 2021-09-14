import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tablero extends JPanel implements MouseListener {
    // MouseListener para que el tablero reconozca cuandp se le da un tap a una pieza

    Pieza piezas;
    boolean juegoEnProceso;
    boolean isEmpate;

    // Las siguientes tres variables son válidas sólo cuando la partida está en curso.
    int jugadorActual;
    int seleccionaFila, seleccionaColum;
    MovimientoPieza[] buenMovi;

    /**
     Constructor. Crea los botones y la etiqueta. Escucha los clics del ratón y los  clics en los botones. 
     Crea el tablero e inicia el primer juego.
     */
    public Tablero() {
        setBackground(Color.BLACK);
        addMouseListener(this);// Le indica a java que esta clase es la que contiene el mouseListener
        piezas = new Pieza();
        isEmpate = false;
        nuevoJuego();
    }

    // Empieza el juego

    public void nuevoJuego() {
        if (juegoEnProceso == true) {
            VentanaJuego.appendInfoTextArea("¡Termina la partida actual primero!");
            return;
        }
        piezas.iniciarTablero(); // Coloca las piezas.
        jugadorActual = VentanaPrincipal.jugador1.getColor(); // Las blancas mueven primero.
        buenMovi = piezas.getMovimiento(Pieza.WHITE); // obtiene los movientos posibles
        seleccionaFila = -1; // Las blancas aún no han seleccionado una pieza para mover.
        // Esto es para cuando inicie, no debe haber nada seleccionado
        VentanaJuego.appendInfoTextArea("Las blancas:  Haga su movimiento.");
        juegoEnProceso = true;

        repaint();// pintamos
    }
 
     // El jugador actual renuncia. La partida termina. El oponente gana.
     
    public String rendirse() {
        if (juegoEnProceso == false) {
            VentanaJuego.appendInfoTextArea("No hay juego en curso!");
            return "";
        }
        if (jugadorActual == Pieza.WHITE) {
            VentanaPrincipal.jugador1.setPerdidas(VentanaPrincipal.jugador1.getPerdidas() + 1);

            VentanaJuego.appendInfoTextArea("Blanco se rindio. Negro gana.");
            Archivo.guardarInformacion(VentanaPrincipal.listaJugadores);
            VentanaPrincipal.listaJugadores = Archivo.obtenerInformacion();

            gameOver();
            return VentanaPrincipal.jugador1.getNombre();
        } else {
            VentanaPrincipal.jugador2.setPerdidas(VentanaPrincipal.jugador2.getPerdidas() + 1);

            VentanaJuego.appendInfoTextArea("Negro se rindio. blanco gana.");
            Archivo.guardarInformacion(VentanaPrincipal.listaJugadores);
            VentanaPrincipal.listaJugadores = Archivo.obtenerInformacion();

            gameOver();
            return VentanaPrincipal.jugador2.getNombre();
        }
    }

    /*
     * Los estados de los botones se ajustan para que los jugadores puedan empezar
     * una nueva partida. Este método método es llamado cuando el juego termina en
     * cualquier punto de esta clase.
     */
    public void gameOver() {
        juegoEnProceso = false;
    }

    public void hizoClickCuadro(int fila, int col) {

        for (int i = 0; i < buenMovi.length; i++)
            if (buenMovi[i].DeFila == fila && buenMovi[i].DeColumna == col) {
                seleccionaFila = fila;
                seleccionaColum = col;
                if (jugadorActual == Pieza.WHITE)
                    VentanaJuego.appendInfoTextArea("Blanco:  Hazo su movimiento.");
                else
                    VentanaJuego.appendInfoTextArea("Negro:  Hazo su movimiento..");
                repaint();// pinta
                return;
            }

        // Si el jugador no tiene una pieza seleccionada para mover, le indicamos que
        // debe seleccionar una

        if (seleccionaFila < 0) {
            VentanaJuego.appendInfoTextArea("Haz click en la pieza que desea mover.");
            return;
        }
        
        /*
        Si el usuario hizo clic en una casilla donde la pieza seleccionada puede 
        hacer el movimiento, entonces haz el movimiento y vuelve.
         */

        for (int i = 0; i < buenMovi.length; i++)
            if (buenMovi[i].DeFila == seleccionaFila && buenMovi[i].DeColumna == seleccionaColum
                    && buenMovi[i].Afila == fila && buenMovi[i].toCol == col) {
                puedeRealizarMovimiento(buenMovi[i]);
                return;
            }

        // Si el jugador selecciona una pieza y no puede mover a ese lugar, el juego no
        // le permite jugarla

        VentanaJuego.appendInfoTextArea("Haz clic en la casilla a la que quieres mover.");
    }

    // Le dice si puede seguir comiendo despues de haber comido previamente
    public void puedeRealizarMovimiento(MovimientoPieza mover) {

        piezas.realizarMovimiento(mover);

        // Debe continuar brincando hasta que ya no pueda comer a otro

        if (mover.puedeComer()) {
            buenMovi = piezas.getSaltosLegalesDesde(jugadorActual, mover.Afila, mover.toCol);
            if (buenMovi != null) {
                if (jugadorActual == Pieza.WHITE)
                    VentanaJuego.appendInfoTextArea("Blanco:  Debe continuar saltando");
                else
                    VentanaJuego.appendInfoTextArea("Negro:  Debe continuar saltando");
                seleccionaFila = mover.Afila; // sólo se puede mover una pieza, selecciónala
                seleccionaColum = mover.toCol;
                repaint();
                return;
            }
        }

        // Si el jugador no pose mas movientos, el juego termina
        if (jugadorActual == Pieza.WHITE) {
            jugadorActual = Pieza.BLACK;
            buenMovi = piezas.getMovimiento(jugadorActual);
            if (buenMovi == null) {
                VentanaJuego.appendInfoTextArea(" Las negras no tienen movimientos.  Las blancas ganan. ");
                VentanaPrincipal.jugador1.setGanadas(VentanaPrincipal.jugador1.getGanadas() + 1);
                VentanaPrincipal.jugador2.setPerdidas(VentanaPrincipal.jugador2.getPerdidas() + 1);
                Archivo.guardarInformacion(VentanaPrincipal.listaJugadores);
                VentanaPrincipal.listaJugadores = Archivo.obtenerInformacion();
                JOptionPane.showMessageDialog(null, "El jugador " + VentanaPrincipal.jugador1.getNombre() + " Gano");
                gameOver();
                IniciarPartida.juego.contenedor.setVisible(false);

            } else if (buenMovi[0].puedeComer())
                VentanaJuego.appendInfoTextArea("NEGRO: Haz tu movimiento.  Debes saltar.");
            else
                VentanaJuego.appendInfoTextArea("NEGRO: Haz tu movimiento.");
        } else {
            jugadorActual = Pieza.WHITE;
            buenMovi = piezas.getMovimiento(jugadorActual);
            if (buenMovi == null) {
                VentanaJuego.appendInfoTextArea("Las blancas no tienen movimientos.  Las negras ganan.");
                VentanaPrincipal.jugador2.setGanadas(VentanaPrincipal.jugador2.getGanadas() + 1);
                VentanaPrincipal.jugador1.setPerdidas(VentanaPrincipal.jugador1.getPerdidas() + 1);
                Archivo.guardarInformacion(VentanaPrincipal.listaJugadores);
                VentanaPrincipal.listaJugadores = Archivo.obtenerInformacion();
                JOptionPane.showMessageDialog(null, "El jugador " + VentanaPrincipal.jugador2.getNombre() + "Gano");
                gameOver();
                IniciarPartida.juego.contenedor.setVisible(false);
            } else if (buenMovi[0].puedeComer())
                VentanaJuego.appendInfoTextArea("Blanco: Haz tu movimiento.  Debes saltar.");
            else
                VentanaJuego.appendInfoTextArea("Blanco: Haz tu movimiento");
        }

        seleccionaFila = -1;
        // seleccionaFila = -1 para registrar que el jugador aún no ha seleccionado una pieza para mover.

       //si puede comer con una solo pieza la selecciona y avanza

        if (buenMovi != null) {
            boolean mismaPieza = true;
            for (int i = 1; i < buenMovi.length; i++)
                if (buenMovi[i].DeFila != buenMovi[0].DeFila || buenMovi[i].DeColumna != buenMovi[0].DeColumna) {
                    mismaPieza = false;
                    break;
                }
            if (mismaPieza) {
                seleccionaFila = buenMovi[0].DeFila;
                seleccionaColum = buenMovi[0].DeColumna;
            }
        }

        repaint();// pintamos otra vez
    } 

    int WIDTH = 80;
    int HEIGHT = 80;
    int X = 80;
    int Y = 80;

    @Override
    public void paintComponent(Graphics g) { 

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//para que se vea mas nitido y menos pixelado

        //Dibuja un borde negro de dos píxeles alrededor de los bordes del lienzo

        g.setColor(Color.black);
        g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);// bordes
        g.drawRect(1, 1, getSize().width - 3, getSize().height - 3);

        //Dibuja los cuadrados del tablero y las fichas.

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Pinta el tablero
                if (row % 2 == col % 2)
                    g.setColor(Color.LIGHT_GRAY);
                else
                    g.setColor(Color.GRAY);
                g.fillRect(2 + col * X, 2 + row * Y, WIDTH, HEIGHT);
                // Pinta las fichas
                switch (piezas.getPieza(row, col)) {
                    case Pieza.WHITE:
                        g.setColor(Color.WHITE);
                        g.fillOval(4 + col * X, 4 + row * Y, WIDTH - 5, HEIGHT - 5);
                        break;
                    case Pieza.BLACK:
                        g.setColor(Color.BLACK);
                        g.fillOval(4 + col * X, 4 + row * Y, WIDTH - 5, HEIGHT - 5);
                        break;
                    case Pieza.ReyBlanco:
                        g.setColor(Color.WHITE);
                        g.fillOval(4 + col * X, 4 + row * Y, WIDTH - 15, HEIGHT - 15);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                        g.drawString("K", 7 + col * X + 20, 16 + row * Y + 20);
                        break;
                    case Pieza.ReyNegro:
                        g.setColor(Color.BLACK);
                        g.fillOval(4 + col * X, 4 + row * Y, WIDTH - 15, HEIGHT - 15);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                        g.drawString("K", 7 + col * X + 20, 16 + row * Y + 20);
                        break;
                    default:
                }
            }
        }

        //si hay una partida en proceso no puede empezar otra
        if (juegoEnProceso) {
            //Primero, dibuja un borde cian de 2 píxeles alrededor de las piezas que se pueden mover.
            g.setColor(Color.cyan);
            for (int i = 0; i < buenMovi.length; i++) {
                g.drawRect(2 + buenMovi[i].DeColumna * X, 2 + buenMovi[i].DeFila * Y, WIDTH - 1, HEIGHT - 1);
                g.drawRect(3 + buenMovi[i].DeColumna * X, 3 + buenMovi[i].DeFila * Y, WIDTH - 3, HEIGHT - 3);
            }
            // se le dibuja bordes verdes a los lugares donde puede mover la pieza seleccionada
            if (seleccionaFila >= 0) {
                g.setColor(Color.white);
                g.drawRect(2 + seleccionaColum * X, 2 + seleccionaFila * Y, WIDTH - 1, HEIGHT - 1);
                g.drawRect(3 + seleccionaColum * X, 3 + seleccionaFila * Y, WIDTH - 3, HEIGHT - 3);
                g.setColor(Color.green);
                for (int i = 0; i < buenMovi.length; i++) {
                    if (buenMovi[i].DeColumna == seleccionaColum && buenMovi[i].DeFila == seleccionaFila) {
                        g.drawRect(2 + buenMovi[i].toCol * X, 2 + buenMovi[i].Afila * Y, WIDTH - 1, HEIGHT - 1);
                        g.drawRect(3 + buenMovi[i].toCol * X, 3 + buenMovi[i].Afila * Y, WIDTH - 3, HEIGHT - 3);
                    }
                }
            }
        }
    } 

    /**
     Responder a un clic del usuario en el tablero. Si no hay ningún juego en curso, mostrar un
      mensaje de error. De lo contrario, encontrar la fila y la columna que el usuario hizo clic y
     *llamar a hizoClickCuadro() para manejarlo.
     */
    public void mousePressed(MouseEvent evento) {
        if (juegoEnProceso == false)
            VentanaJuego.appendInfoTextArea("Click \"New Game\" to start a new game.");
        else {
            int col = (evento.getX() - 2) / 80;
            int row = (evento.getY() - 2) / 80;
            if (col >= 0 && col < 8 && row >= 0 && row < 8)
                hizoClickCuadro(row, col);
        }
    }

    //metodos que no fueron implementados.
    @Override
    public void mouseReleased(MouseEvent evento) {
    }

    @Override
    public void mouseClicked(MouseEvent evento) {
    }

    @Override
    public void mouseEntered(MouseEvent evento) {
    }

    @Override
    public void mouseExited(MouseEvent evento) {
    }
} 

/*
 Un objeto de esta clase contiene datos sobre un juego de damas. Sabe qué
 tipo de pieza está en cada casilla del tablero de damas. Tenga en cuenta que las blancas se mueven
 "arriba" del tablero (es decir, el número de filas disminuye) mientras que el NEGRO se mueve "abajo" del tablero
 (es decir, el número de filas aumenta). Se proporcionan métodos para devolver listas de movimientos legales disponibles.
 */
