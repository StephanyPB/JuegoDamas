/*
  Un objeto MovimientoPieza representa un movimiento en el juego de las Damas. Contiene la
 fila y columna de la pieza que se va a mover y la fila y columna de la
  a la que se va a mover. 
 */
public  class MovimientoPieza {
    int DeFila, DeColumna; //Posición de la pieza a mover.
    int Afila, toCol; // Plaza a la que se va a mover.

    public MovimientoPieza(int r1, int c1, int r2, int c2) {
      
        DeFila = r1;
        DeColumna = c1;
        Afila = r2;
        toCol = c2;
    }

    public boolean puedeComer() {
       //Comprobamos si puede volver a comer 
        return (DeFila - Afila == 2 || DeFila - Afila == -2);
    }
} 