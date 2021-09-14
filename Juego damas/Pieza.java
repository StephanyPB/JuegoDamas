import java.util.ArrayList;

public class Pieza {
    
     //Las siguientes constantes representan el posible contenido de una casilla en el tablero

    public static final int EMPTY = 0, WHITE = 1, ReyBlanco = 2, BLACK = 3, ReyNegro = 4;

    int[][] tablero; // tablero[r][c] es el contenido de la fila f, columna c.

        //Constructor Crea el tablero y lo configura para una nueva partida 
    public Pieza() {
        tablero = new int[8][8];
        iniciarTablero();
    }

    /**
     Prepara el tablero con las fichas en posición para el comienzo de una partida. Tenga en cuenta
     * que las fichas sólo pueden encontrarse en casillas que satisfagan row % 2 == col % 2.
     * Al comienzo de la partida, todas las casillas de las tres primeras filas contienen
     * casillas negras y todas las casillas de las tres últimas filas contienen casillas blancas
     */
    public void iniciarTablero() {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                if (fila % 2 == col % 2) {
                    if (fila < 3)
                        tablero[fila][col] = BLACK;
                    else if (fila > 4)
                        tablero[fila][col] = WHITE;
                    else
                        tablero[fila][col] = EMPTY;
                } else {
                    tablero[fila][col] = EMPTY;
                }
            }
        }
    } 
        
    public int getPieza(int fila, int col) {
        return tablero[fila][col];
    }//Devuelve el contenido de la casilla en la fila y columna especificadas. 

    public void realizarMovimiento(MovimientoPieza mover) {
        realizarMovimiento(mover.DeFila, mover.DeColumna, mover.Afila, mover.toCol);
    }//desde donde va hasta donde

    /**
     Si el movimiento es un salto, la pieza saltada se retira del
     el tablero. Si una pieza se mueve a la última fila del lado del adversario del tablero, la pieza se convierte en rey.
     tablero, la pieza se convierte en un rey.
     */
    public void realizarMovimiento(int desdeFila, int desdeColum, int aFila, int aColum) {
        tablero[aFila][aColum] = tablero[desdeFila][desdeColum];
        tablero[desdeFila][desdeColum] = EMPTY;
        if (desdeFila - aFila == 2 || desdeFila - aFila == -2) {
            //  El movimiento es un salto. Retira del tablero la pieza que ha saltado.
            int jumpRow = (desdeFila + aFila) / 2; // Fila de la pieza saltada.
            int jumpCol = (desdeColum + aColum) / 2; // Columna de la pieza saltada.
            tablero[jumpRow][jumpCol] = EMPTY;
        }
        if (aFila == 0 && tablero[aFila][aColum] == WHITE)//se define cuando se convierte en reyes
            tablero[aFila][aColum] = ReyBlanco;
        if (aFila == 7 && tablero[aFila][aColum] == BLACK)
            tablero[aFila][aColum] = ReyNegro;
    }
    

    /**
    Devuelve un array que contiene todos los movimientos legales para el jugador especificado
     jugador especificado en el tablero actual. 
     */
    public MovimientoPieza[] getMovimiento(int jugador) {

        if (jugador != WHITE && jugador != BLACK)
            return null;

        int Rey; // La constante que representa un rey que pertenece al jugador. 
        
        if (jugador == WHITE)
            Rey = ReyBlanco;
        else
            Rey = ReyNegro;

        ArrayList<MovimientoPieza> moves = new ArrayList<MovimientoPieza>(); //Los movimientos serán blancos en esta lista.

        /*
          En primer lugar, comprueba los posibles saltos. Mira cada casilla del tablero. Si
         * esa casilla contiene una de las piezas del jugador, mira un posible salto en
         * cada una de las cuatro direcciones desde esa casilla. Si hay un salto legal en
         * esa dirección, ponlo en el ArrayList de movimientos.
         */

        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                if (tablero[fila][col] == jugador || tablero[fila][col] == Rey) {
                    if (puedeSaltar(jugador, fila, col, fila + 1, col + 1, fila + 2, col + 2))
                        moves.add(new MovimientoPieza(fila, col, fila + 2, col + 2));
                    if (puedeSaltar(jugador, fila, col, fila - 1, col + 1, fila - 2, col + 2))
                        moves.add(new MovimientoPieza(fila, col, fila - 2, col + 2));
                    if (puedeSaltar(jugador, fila, col, fila + 1, col - 1, fila + 2, col - 2))
                        moves.add(new MovimientoPieza(fila, col, fila + 2, col - 2));
                    if (puedeSaltar(jugador, fila, col, fila - 1, col - 1, fila - 2, col - 2))
                        moves.add(new MovimientoPieza(fila, col, fila - 2, col - 2));
                }
            }
        }
         
         // Na mueve la ficha donde si pueda mover
         
        if (moves.size() == 0) {
            for (int fila = 0; fila < 8; fila++) {
                for (int col = 0; col < 8; col++) {
                    if (tablero[fila][col] == jugador || tablero[fila][col] == Rey) {
                        if (puedeMoverse(jugador, fila, col, fila + 1, col + 1))
                            moves.add(new MovimientoPieza(fila, col, fila + 1, col + 1));
                        if (puedeMoverse(jugador, fila, col, fila - 1, col + 1))
                            moves.add(new MovimientoPieza(fila, col, fila - 1, col + 1));
                        if (puedeMoverse(jugador, fila, col, fila + 1, col - 1))
                            moves.add(new MovimientoPieza(fila, col, fila + 1, col - 1));
                        if (puedeMoverse(jugador, fila, col, fila - 1, col - 1))
                            moves.add(new MovimientoPieza(fila, col, fila - 1, col - 1));
                    }
                }
            }
        }

        //Le devuelve en un arreglos todos los posibles movimientos que puede hacer
        if (moves.size() == 0)
            return null;
        else {
            MovimientoPieza[] movimientosHacer = new MovimientoPieza[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                movimientosHacer[i] = moves.get(i);
            return movimientosHacer;
        }
    }

    /**
     *Devuelve una lista de los saltos legales que el jugador especificado puede realizar a partir
     * desde la fila y columna especificadas. Si no hay saltos posibles, se devuelve null.
     */
    public MovimientoPieza[] getSaltosLegalesDesde(int jugador, int fila, int col) {
        if (jugador != WHITE && jugador != BLACK)
            return null;
        int coronado; // La constante que representa un rey que pertenece al jugador.
        if (jugador == WHITE)
            coronado = ReyBlanco;
        else
            coronado = ReyNegro;

        ArrayList<MovimientoPieza> moves = new ArrayList<MovimientoPieza>();

        if (tablero[fila][col] == jugador || tablero[fila][col] == coronado) {
            if (puedeSaltar(jugador, fila, col, fila + 1, col + 1, fila + 2, col + 2))
                moves.add(new MovimientoPieza(fila, col, fila + 2, col + 2));
            if (puedeSaltar(jugador, fila, col, fila - 1, col + 1, fila - 2, col + 2))
                moves.add(new MovimientoPieza(fila, col, fila - 2, col + 2));
            if (puedeSaltar(jugador, fila, col, fila + 1, col - 1, fila + 2, col - 2))
                moves.add(new MovimientoPieza(fila, col, fila + 2, col - 2));
            if (puedeSaltar(jugador, fila, col, fila - 1, col - 1, fila - 2, col - 2))
                moves.add(new MovimientoPieza(fila, col, fila - 2, col - 2));
        }
        if (moves.size() == 0)
            return null;
        else {
            MovimientoPieza[] moveArray = new MovimientoPieza[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                moveArray[i] = moves.get(i);
            return moveArray;
        }
    } 

     //verifica si puede volver a comer las pobres damas
     
    public boolean puedeSaltar(int jugador, int r1, int c1, int r2, int c2, int r3, int c3) {

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
            return false; 

        if (tablero[r3][c3] != EMPTY)
            return false; // (r3,c3) ya contiene una pieza..

        if (jugador == WHITE) {
            if (tablero[r1][c1] == WHITE && r3 > r1)
                return false; //La pieza blanca normal sólo puede moverse hacia arriba
            if (tablero[r2][c2] != BLACK && tablero[r2][c2] != ReyNegro)
                return false; // No hay ninguna pieza negra que pueda saltar.
            return true; // El salto es legal.
        } else {
            if (tablero[r1][c1] == BLACK && r3 < r1)
                return false; // La pieza negra normal sólo puede moverse hacia abajo.
            if (tablero[r2][c2] != WHITE && tablero[r2][c2] != ReyBlanco)
                return false; // No hay pieza blanca para saltar
            return true; // El salto es legal.
        }
    } 

    public boolean puedeMoverse(int jugador, int r1, int c1, int r2, int c2) {

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
            return false; // (r2,c2) está fuera del tablero.

        if (tablero[r2][c2] != EMPTY)
            return false; // (r2,c2) ya contiene una pieza.

        if (jugador == WHITE) {
            if (tablero[r1][c1] == WHITE && r2 > r1)
                return false; 
            return true; 
        } else {
            if (tablero[r1][c1] == BLACK && r2 < r1)
                return false; 
            return true; 
        } 
    }
}
