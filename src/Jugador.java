
public class Jugador {

    //Atributos
    private static String nombre;
    private static int totalJugadores=0;
    private static int barcos;

    //Constructor principal
    public Jugador(String nombre){
        this.nombre=nombre;
        totalJugadores++;
    }

    //Constructor sin argumentos
    public Jugador(){
        totalJugadores++;
    }

    //GETTERS
    public String getNombre() {
        return nombre;
    }

    public static int getTotalJugadores() {
        return totalJugadores;
    }

    //SETTERS
    public void setNombre(String nombre) {
        Jugador.nombre = nombre;
    }
}
