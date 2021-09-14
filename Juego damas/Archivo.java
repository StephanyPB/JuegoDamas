import java.io.*;
import java.util.ArrayList;;

public class Archivo {

    private static final String RUTA = "db.txt";
        //Guardamos la informacion de los jugadores
    public static void guardarInformacion(ArrayList<Jugador> Lista) {

        try {
            ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream(RUTA));

            escribiendoFichero.writeObject(Lista);
            escribiendoFichero.close();

        } catch (Exception e) {
           
        }
    }

    public static ArrayList<Jugador> obtenerInformacion() {
        ArrayList<Jugador> Lista = new ArrayList<Jugador>();
        try {
            ObjectInputStream leyendoFichero = new ObjectInputStream(new FileInputStream(RUTA));
            Lista = (ArrayList<Jugador>) leyendoFichero.readObject();
            leyendoFichero.close();

        } catch (Exception e) {
        
        }
        return Lista;
    }
}
