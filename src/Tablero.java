
import utilidades.GestionArray;

import java.util.Random;

public class Tablero {

    //Aquí uso variables para guardar el color ANSI (color de texto de la terminal) para poder diferenciar mejor el texto.
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    //Atributos privados de instancia
    private int filas;     //Guarda el número de filas que va a tener el tablero
    private int col;       //Guarda el número de columnas que va a tener el tablero
    public static int barcos;       //Guarda el número maximo de barcos que hay en el nivel.
    public int barcosRestantes;
//    public int intentos=0;
    //Atributos privados de clase
    public static int x=0, x1=0; //Segunda casilla, horizontal
    public static int y=0, y1=0; //Segunda casilla, vertical
    public static int cols=0;
    public static int rows=0;
    public static int matriz[][];
    public static char matrizVisible[][];


    /**
     * Constructor principal
     * @param filas
     * @param col
     * @param barcos
     * */
    public Tablero(int filas, int col, int barcos){
        this.filas=filas;
        this.col=col;
        this.barcos=barcos;
        this.barcosRestantes=barcos;
    }


    /**
     * Funcion para gestionar el tablero visible y la matriz donde se guardan los barcos. Además se encarga
     * de mostrar el tablero y de mostrar los mensajes al jugador.
     * @param b1
     * @param b2
     * @return control - Si las coordenadas introducidas coinciden con una casilla que contenga algún código de barco devuelve true.
     * */
    public void gestionTablero(int pos1, int pos2, Barco b1, Barco b2, int cod1, int cod2, Jugador jugador){
//        intentos= jugador.getIntentos();
//        boolean control=false;  //Variable de control para el for

        //Inicializo el array definido a 0. Meto los arrays en variables para poder manejarlos mejor despues.
        matriz= GestionArray.InicializarMatrizEnteros(this.filas,this.col,0);
        jugador.setTablero(matriz); //Se guarda el tablero real con las posiciones en el objeto jugador

        //El array visible se inicializa parecido al original con la diferencia de que este almacena caracteres.
        matrizVisible=GestionArray.InicializarMatrizCaracter(this.filas,this.col,'*');
        jugador.setTableroV(matrizVisible); //Se guarda el tablero visible en el objeto jugador

        generarCoor(matriz, pos1);
        generarBarco(b1);
        esconderBarco(matriz, this.barcos, cod1);  //Se llama a la funcion esconderBarcos para posicionar el 1er barco.
        generarCoor(matriz, pos2);
        generarBarco(b2);
        esconderBarco(matriz, this.barcos, cod2);  //Se llama a la funcion esconderBarcos para posicionar el 2º barco.
        //Se le pasa por parametro "barcos" para que no pase de ese limite.


                //Si control vale true se decrementa en 1 los barcos restantes, se muestra y se asigna a la matriz "copia" la T para indicar
                //que hay un barco cuando se muestre toda la matriz. Hay un mensaje personalizado segun que casilla introduzcas repetidamente.
//                if (control == true && matrizVisible[coor1][coor2]=='*' && matrizVisible[coor1][coor2]!='T') {
//                    intentos++;
//                    if (matriz[coor1][coor2]==1) {
//                        int longitud=b1.getLongitud();
//                        b1.setLongitud(longitud-1);
//                        System.out.println(b1.getLongitud());
//                    }else{
//                        int longitud=b2.getLongitud();
//                        b2.setLongitud(longitud-1);
//                        System.out.println(b2.getLongitud());
//                    }
//                    //Si la longitud del barco llega a 0 y las coordenadas contienen su codigo, las casillas que ocupa cambian a Hundido, se resta un barco y se informa al jugador.
//                    if (b1.getLongitud()==0 && matriz[coor1][coor2]==1){
//                        barcosRestantes--;
//                        System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
//                        matrizVisible[b1.getC1row()][b1.getC1col()]='H';
//                        matrizVisible[b1.getC2row()][b1.getC2col()]='H';
//                    }else if (b2.getLongitud()==0 && matriz[coor1][coor2]==2){
//                        barcosRestantes--;
//                        System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
//                        matrizVisible[b2.getC1row()][b2.getC1col()]='H';
//                        matrizVisible[b2.getC2row()][b2.getC2col()]='H';
//                    }else {
//                        System.out.println(ANSI_YELLOW + "¡Barco tocado!" + ANSI_RESET);
//                        matrizVisible[coor1][coor2] = 'T';
//                    }
//
//                } else if (control == false && matrizVisible[coor1][coor2]=='*'){
//                    intentos++; //Entiendo que el número de intentos suma cada vez que fallas, no cuando aciertas.
//                    System.out.println(ANSI_YELLOW + "¡Agua! Llevas "+intentos+" intentos."+ ANSI_RESET);
//                    matrizVisible[coor1][coor2]='A';
//                } else if (control == true && matrizVisible[coor1][coor2]=='T'){
//                    intentos++;
//                    System.out.println(ANSI_YELLOW + "¡Hey! Fíjate bien, ¡esa casilla ya esta descubierta! Llevas "+intentos+" intentos."+ ANSI_RESET);
//                } else if (control == false && matrizVisible[coor1][coor2]=='A' && intentos>27) {  //Pequeño Easter Egg. Ni caso.
//                    intentos++;
//                    System.out.println(ANSI_YELLOW + "Esta casilla ya esta descubierta y encima vacía...¿Necesitas gafas? Llevas "+intentos+" intentos."+ ANSI_RESET);
//                }else{
//                    intentos++;
//                    System.out.println(ANSI_YELLOW + "Ya has descubierto esta casilla. Llevas "+intentos+" intentos."+ ANSI_RESET);
//                }
//            } while (barcosRestantes > 0);

//        }catch (ArrayIndexOutOfBoundsException e){  //Se captura esta excepcion que salta al introducir un valor que esta fuera del rango del array. Ej: arr5x5 y valor=5, falla.
//            System.out.println(ANSI_RED+"Error. El valor esta fuera de rango."+ANSI_RESET);
//        }catch (Exception e) {    //Captura excepciones genericas, no previstas.
//            System.out.println(ANSI_RED + "Error. Desconocido." + ANSI_RESET);
//        }
//        return control;
    }

    public boolean comprobarDisparo(Barco b1, Barco b2, int coor1, int coor2){
        boolean control=false;  //Variable de control para el for

        try {
            //En este for se comprueba que valor se guarda en la posicion del array a la que corresponde las coordenadas introducidas.
            for (int i = 0; i < matriz.length; i++) {
                for (int z = 0; z < matriz.length; z++) {
                    if (matriz[coor1][coor2] == b1.getCodBarco() | matriz[coor1][coor2] == b2.getCodBarco()) {
                        control = true; //Si las coordenadas corresponden a una posicion con barco, control vale true.
                        //He tenido que crear este control para poder sacar el if-else fuera del bucle.
                    } else if (matriz[coor1][coor2] == 0) {
                        control = false;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {  //Se captura esta excepcion que salta al introducir un valor que esta fuera del rango del array. Ej: arr5x5 y valor=5, falla.
            System.out.println(ANSI_RED + "Error. El valor esta fuera de rango." + ANSI_RESET);
        } catch (Exception e) {    //Captura excepciones genericas, no previstas.
            System.out.println(ANSI_RED + "Error. Desconocido." + ANSI_RESET);
        }
        return control;
    }


    /**
     * Funcion que muestra el tablero "falso" en pantalla.
     * @param matrizV tablero visible.
     * */
    public static void mostarTablero(char matrizV[][]){
        System.out.println(ANSI_YELLOW + "-------Tablero de juego-------" + ANSI_RESET);
        GestionArray.mostrarMatrizCaracter(matrizV);   //El array que se muestra no sería el auténtico definido, sino una copia en la
        //que se van sustituyendo valores segun el valor de las posiciones del array original.
    }


    /**
     * Funcion que genera de forma aleatoria la posicion de la primera casilla.
     * @param matriz tablero real, se necesita para las condiciones.
     * @param posicion horizontal/vertical (0/1). Segun la posicion se podra o no sacar una coordenada válida.
     * */
    public static void generarCoor(int matriz[][], int posicion){
        int limite=matriz.length-1;
        Random rnd=new Random();

        do {
            rows = rnd.nextInt(0, limite); //Se genera un entero aleatorio desde 0 hasta la longitud total del array.
            cols = rnd.nextInt(0, limite);  //Hay dos aleatorios para las filas y a2 para las columnas.

            //Horizontal
            if (posicion==0 && rows<matriz.length && cols<matriz.length){ //Si la posicion es horizontal y las coordenadas son menores que el limite, se suma una casilla.
                x=cols+1;
            }else if(posicion==0 && rows==limite | cols==limite){ //Si las coordenadas están justo en el límite, se resta una casilla.
                x=cols-1;
            }else{
                x=cols;
            }

            //Vertical
            if (posicion==1 && rows<matriz.length && cols<matriz.length){
                y=rows+1;
            }else if(posicion==1 && rows==limite | cols==limite){
                y=rows-1;
            }else{
                y=rows;
            }
        }while (matriz[rows][cols]==1 | matriz[y][x]==1);
    }


    /**
     * Esta funcion guarda las coordenadas aleatorias generadas antes, en el objeto barco pasado por parametro.
     * @param barco objeto barco instanciado antes de llamar a esta funcion.
     * */
    public static void generarBarco(Barco barco){
            barco.setC1row(rows);
            barco.setC1col(cols);
            barco.setC2row(y);
            barco.setC2col(x);
            System.out.println("Barco: "+barco.getC1row()+","+barco.getC1col()+"; "+barco.getC2row()+","+barco.getC2col());
    }


    /**
     * Funcion para guardar o colocar los barcos en el tablero de 'datos'
     * @param matriz datos(invisible)
     * @param maxBarcos maximo de barcos
     * //@param posicion posicion vertical/horizontal en el tablero
     * */
    public static void esconderBarco(int matriz[][], int maxBarcos, int codBarco){
        int barcos=0;  //Contador para controlar el número de barcos escondidos

        for(int i=0;i<matriz.length;i++){
            for (int z=0;z<matriz.length;z++) {
                //Si la posicion aleatoria es un cero se cambia por un 1
                if (matriz[rows][cols]==0 && barcos<maxBarcos) {   //Además el contador debe ser menor a 10, en este caso, para que no cambie mas de 10 casillas.
                    matriz[rows][cols]=codBarco;    //En vez de sustuir los 0 por 1, cada barco tiene un codigo(numero) que se introduce por parametro.
                    matriz[y][x]=codBarco;
                    System.out.println(codBarco);
                    barcos++;  //Cada vez que se esconda un barco incrementa el contador
                }
            }
        }
    }
}



