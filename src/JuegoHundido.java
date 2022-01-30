
import utilidades.GestionArray;
import utilidades.PeticionDatos;

public class JuegoHundido {
    /**
     * @autor Juan Fco Cirera
     * */

    //Aquí uso variables para guardar el color ANSI (color de texto de la terminal) para poder diferenciar mejor el texto.
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    //Atributos de clase, solo se van a usar en esta clase por eso son static
    private static int opcion;
    private static int filas;
    private static int col;
    private static int posicion;
//    private static int posicion2;
    private static Barco b;
//    private static Barco b2;
    private static int codBarco=0;

    //Arrays de clase para objetos
    private static Barco barcos[]=new Barco[4]; //Array para meter los objetos barco.
    private  static Jugador jugadores[]=new Jugador[2]; //Arra y para almacenar objetos jugador.
//    private static Tablero tableros[]=new Tablero[2]; //Array para almacenar los datos de los tableros


    /**
     * Muestra el menu de seleccion de niveles. Es la 'pantalla' principal del juego.
     * */
    public static void mostrarMenu(){
        System.out.println("\n"+ANSI_BLUE+"╠═══════════F5 HUNDIDO══════════╣"+ANSI_RESET); //Titulo del juego

        opcion=PeticionDatos.pedirEnteroPositivo(true,"""   
                ║ 1-> Nivel 1    (2 barcos)     ║
                ║ 2-> Nivel 2    Coming soon    ║
                ║ 3-> Nivel 3    In development ║
                ║ 0-> Salir                     ║
                ╚ Elige un nivel: """);   //Aqui llamo a la funcion pedirEnteroPositivo y le paso el menu que quiero que muestre.

    }

    /**
     * Esta funcion crea un objeto jugador y lo guarda en el array de objetos jugador.
     * */
    //El unico motivo, por ahora, de que exista el objeto jugador es que guardo su nombre para mostrar al final si gana.
    public static void nuevoJugador() {
        String nombre = PeticionDatos.pedirCadena("Nombre jugador "+(Jugador.getTotalJugadores()+1)+": ");
        if (Jugador.getTotalJugadores() < jugadores.length) {
            Jugador nuevoJugador = new Jugador(nombre);
            jugadores[nuevoJugador.getTotalJugadores()-1] = nuevoJugador;
        }
    }

    /**
     * Esta funcion crea un nuevo objeto barco y lo guarda en el array de objetos barco.
     * */
    //Pregunta y guarda la longitud y posicion de cada barco
    public static void nuevoBarco(){
        System.out.println(ANSI_BLUE+"**Posición de los barcos**"+ANSI_RESET);
        //Barco 1
        for(int i=0;i<2;i++) {
//            if (Barco.getTotalBarcos() < 1) {
//                System.out.println("Posición 1er barco: 0-Horizontal, 1-Vertical");
//            } else {
//                System.out.println("Posición 2º barco: 0-Horizontal, 1-Vertical");
//            }
            do {
                posicion = 2; //posicion tiene que tener un valor por defecto antes de realizar todo lo de abajo, si no
                //cuando se pide el segundo jugador posicion aun tiene los valores del jugador anterior y fallan las condiciones.
                int p = PeticionDatos.pedirEnteroPositivo(true, "0-Horizontal, 1-Vertical"+"\n"+"> ");
                if (p == 0 | p == 1) {
                    posicion = p;
                } else {
                    System.out.println(ANSI_RED + "Error. La posición debe ser 0 o 1." + ANSI_RESET);
                }
            } while (posicion != 0 && posicion != 1);
            if (Barco.getTotalBarcos() < barcos.length && codBarco < 4) {
                codBarco++;
                b = new Barco(2, posicion, codBarco);
                barcos[b.getTotalBarcos()-1] = b;
            }
        }
    }

    //Funcion para pedir la fila de casilla
    public static int pedirFila(){
        System.out.println(ANSI_BLUE + "**Introduce las coordenadas**" + ANSI_RESET);
        //Llamo a las funciones de peticion para enteros y guardo el valor en coor1.
        int coor1 = PeticionDatos.pedirEnteroPositivo(true, ">fila: ");
        return coor1;
    }

    //Funcion para pedir la columna de la casilla
    public static int pedirColumna(){
        //Para que devuelva ambos valores por separado tengo que separar las peticiones en dos funciones.
        int coor2 = PeticionDatos.pedirEnteroPositivo(true, ">columna: ");
        return coor2;
    }

    private static Tablero tablero1, tablero2; //MOVER

    public static void tablero1(){
        tablero1=new Tablero(4,4,2); //Se instancia un objeto tablero para cada jugador.
        int p1=barcos[0].getPosicion();
        Barco b1=barcos[0];
        int cod1=barcos[0].getCodBarco();
        int p2=barcos[1].getPosicion();
        Barco b2=barcos[1];
        int cod2=barcos[1].getCodBarco();
        tablero1.gestionTablero(p1, p2, b1, b2, cod1, cod2);    //Se llama a la funcion matriz para crear ambos tableros.
    }
    public static void tablero2(){
        tablero2=new Tablero(4,4,2); //Se instancia un objeto tablero para cada jugador.
        int p1=barcos[2].getPosicion();
        Barco b1=barcos[2];
        int cod1=barcos[2].getCodBarco();
        int p2=barcos[3].getPosicion();
        Barco b2=barcos[3];
        int cod2=barcos[3].getCodBarco();
        tablero2.gestionTablero(p1, p2, b1, b2, cod1, cod2);    //Se llama a la funcion matriz para crear ambos tableros.
    }


    public static void main(String[] args) {

        char respuesta=' ';

        do {
            mostrarMenu();
            //Llamadas a funciones segun la opcion del menu elegida.
            switch (opcion) {
                case 0:
                    System.out.println(ANSI_YELLOW+"Saliendo del juego..."+ANSI_RESET);
                    break;
                case 1:
                    System.out.println(ANSI_BLUE+"NIVEL 1 -- Hunde los 2 barcos"+ANSI_RESET);
                    for(int x=0;x<2;x++) {
                        nuevoJugador(); //Se piden los datos de ambos jugadores
                        nuevoBarco();   //Además de la posicion de los barcos
//                        nuevoTablero();
                    }
                    //Jugador1
//                    Tablero tablero1=new Tablero(4,4,2); //Se instancia un objeto tablero para cada jugador.
//                    int p1=barcos[0].getPosicion();
//                    Barco b1=barcos[0];
//                    int cod1=barcos[0].getCodBarco();
//                    int p2=barcos[1].getPosicion();
//                    Barco b2=barcos[1];
//                    int cod2=barcos[1].getCodBarco();
//                    tablero1.gestionTablero(p1, p2, b1, b2, cod1, cod2);    //Se llama a la funcion matriz para crear ambos tableros.
//                    //Jugador 2
//                    Tablero tablero2=new Tablero(4,4,2);
//                    int p1=barcos[0].getPosicion();
//                    Barco b1=barcos[0];
//                    int cod1=barcos[0].getCodBarco();
//                    int p2=barcos[1].getPosicion();
//                    Barco b2=barcos[1];
//                    int cod2=barcos[1].getCodBarco();
//                    tablero2.gestionTablero(p1, p2, b1, b2, cod1, cod2);    //Se llama a la funcion matriz para crear ambos tableros.
                    tablero1();
                    tablero2();
                    if (tablero1.barcosRestantes==0){
                        int total=tablero1.intentos-tablero1.barcos;
                        System.out.println(ANSI_GREEN+"¡Enhorabuena, "+jugadores[0].getNombre()+" ha superado el nivel 1! Con "+total+" intentos."+ANSI_RESET);
                        break;
                    }else if (tablero2.barcosRestantes==0){
                        System.out.println(ANSI_GREEN+"¡Enhorabuena, "+jugadores[1].getNombre()+" ha superado el nivel 1! Con "+tablero2.intentos+" intentos."+ANSI_RESET);
                        break;
                    }
                case 2:
                    System.out.println(ANSI_RED+"NIVEL NO DISPONIBLE."+ANSI_RESET);
                    break;
                case 3:
                    System.out.println(ANSI_RED+"NIVEL NO DISPONIBLE."+ANSI_RESET);
                    break;
                case 4:
                    System.out.println(ANSI_RED+"NIVEL NO DISPONIBLE."+ANSI_RESET);
                    break;
                default:
                    System.out.println(ANSI_RED + "Error, debe elegir una de las opciones indicadas." + ANSI_RESET);
            }
            if (opcion!=0) {    //Este if es para que no aparezca esto al salir directamente desde el menu con la opcion 0
                do {
                    respuesta=Character.toUpperCase(PeticionDatos.pedirCaracter("¿Quieres volver a jugar? S/N: "));
                    //Se pasa lo que se introduzca a mayúscula para permitir que se responda con minúsculas.
                    if (respuesta == 'N') { //Este if solo contempla la opcion N, salir, porque la opcion S no hace realmente nada, sigue el flujo del do-while.
                        System.out.println(ANSI_YELLOW + "Saliendo del juego..." + ANSI_RESET);
                        opcion=0;  //Para salir del do-while padre.
                        break; //Uso un break para salir del do-while anidado(éste). El break tiene que ser el último siempre.
                    }else if (respuesta == 'S'){break;}
                }while(respuesta != 'N' | respuesta != 'S'); //Esto do-while seguirá pidiendo continuar o no si el usuario responde con otro caracter que no sea S o N.

            }
        }while (opcion!=0); //El do while seguira mientas que la opcion no valga 0

    }
}

