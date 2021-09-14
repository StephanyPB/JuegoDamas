import java.io.Serializable;

public class Jugador implements Serializable {

    private int id;
    private String nombre;
    private int ganadas;
    private int perdidas;
    private int empates;
    private int color;

    public Jugador() {
        this.id = 0; 
        this.nombre = "";
        this.ganadas = 0;
        this.perdidas = 0;
        this.empates = 0;
        this.color = 0;
    }

    public Jugador(int id, String nombre, int ganadas, int perdidas, int empates,int color) {
        setId(id);
        setNombre(nombre);
        setGanadas(ganadas);
        setPerdidas(perdidas);
        setEmpates(empates);
        setColor(color);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGanadas(int ganadas) {
        this.ganadas = ganadas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public int getGanadas() {
        return ganadas;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public int getEmpates() {
        return empates;
    }
    public int getColor() {
        return color;
    }
}
