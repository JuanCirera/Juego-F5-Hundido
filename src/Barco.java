
public class Barco {

    //Atributos
    private int longitud;   //Longitud del barco para saber cuando se ha hundido todo el barco
    private int posicion; // 1-Vertical, 2-Horizontal
    private int codBarco;
    //casilla 1
    private int c1row;  //Casilla 1 fila
    private int c1col;  //Casilla 2 columna. Eso significan estas variables, lo mismo para las del abajo.
    //casilla 2
    private int c2row;
    private int c2col;
    //Atributos de clase
    private static int totalBarcos=0;

    //Constructor principal
    public Barco(int longitud, int posicion, int codBarco){
        this.longitud=longitud;
        this.posicion=posicion;
        this.codBarco=codBarco;
        totalBarcos++;
    }

    //Constructor sin argumentos
    public Barco(){
        totalBarcos++;
    }

    //GETTERS
    public int getLongitud() {
        return longitud;
    }

    public int getPosicion() {
        return posicion;
    }

    public static int getTotalBarcos() {
        return totalBarcos;
    }

    public int getCodBarco() {
        return codBarco;
    }

    public int getC1row() {
        return c1row;
    }

    public int getC1col() {
        return c1col;
    }

    public int getC2row() {
        return c2row;
    }

    public int getC2col() {
        return c2col;
    }

    //SETTERS
    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void setCodBarco(int codBarco) {
        this.codBarco = codBarco;
    }

    public void setC1row(int c1row) {
        this.c1row = c1row;
    }

    public void setC1col(int c1col) {
        this.c1col = c1col;
    }

    public void setC2row(int c2row) {
        this.c2row = c2row;
    }

    public void setC2col(int c2col) {
        this.c2col = c2col;
    }
}
