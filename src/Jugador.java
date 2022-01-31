
public class Jugador {

    //Atributos de clase
    private static String nombre;
    private static int totalJugadores=0;
    private static int barcos;

    //Atributos de instancia
    private int intentos=0;
    private int barcosRestantes=2;
    private int BarcosHundidos;
//    private boolean turno;
    private int tablero[][];
    private char tableroV[][];

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

    public int getIntentos() {
        return intentos;
    }

    public static int getBarcos() {
        return barcos;
    }

    public int getBarcosRestantes() {
        return barcosRestantes;
    }

    public int getBarcosHundidos() {
        return BarcosHundidos;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public char[][] getTableroV() {
        return tableroV;
    }

    //SETTERS
    public void setNombre(String nombre) {
        Jugador.nombre = nombre;
    }

    public static void setBarcos(int barcos) {
        Jugador.barcos = barcos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public void setBarcosRestantes(int nBarcos) {
        this.barcosRestantes = nBarcos;
    }

    public void setBarcosHundidos(int nBarcosHundidos) {
        this.BarcosHundidos = nBarcosHundidos;
    }

    public void setTablero(int tablero[][]) {
        this.tablero = tablero;
    }

    public static void setTotalJugadores(int totalJugadores) {
        Jugador.totalJugadores = totalJugadores;
    }

    public void setTableroV(char tableroV[][]) {
        this.tableroV = tableroV;
    }
}
