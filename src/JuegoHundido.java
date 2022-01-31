
import utilidades.GestionArray;
import utilidades.PeticionDatos;

public class JuegoHundido {
    /**
     * @autor Juan Fco Cirera
     * */

    //Aquí uso variables para guardar el color ANSI (color de texto de la terminal) para poder diferenciar mejor el texto.
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BGREEN = "\u001B[32;1m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BBLUE = "\u001B[34;1m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    //Atributos de clase, solo se van a usar en esta clase por eso son static
    private static int opcion;
    private static int filas;
    private static int col;
    private static int posicion;
    private static Barco b;
    private static int codBarco=0;
    private static Tablero tablero1, tablero2; //MOVER

    //Arrays de clase para objetos
    private static Barco barcos[]=new Barco[4]; //Array para meter los objetos barco.
    private  static Jugador jugadores[]=new Jugador[2]; //Arra y para almacenar objetos jugador.


    /**
     * Muestra el menu de seleccion de niveles. Es la 'pantalla' principal del juego.
     * */
    public static void mostrarMenu(){
        System.out.println("\n"+ANSI_BLUE+"╔═══════════F5 HUNDIDO══════════╗"+ANSI_RESET); //Titulo del juego

        opcion=PeticionDatos.pedirEnteroPositivo(true,"""   
                ║ 1-> Nivel 1    (2 barcos)     ║
                ║ 2-> Nivel 2    Coming soon    ║
                ║ 3-> Nivel 3    In development ║
                ║ 0-> Salir                     ║
                ╚ Elige un nivel: """);   //Aqui llamo a la funcion pedirEnteroPositivo y le paso el menu que quiero que muestre.

        System.out.println(); //Linea en blanco para separar el menu del resto de lineas.
    }

    /**
     * Esta funcion crea un objeto jugador y lo guarda en el array de objetos jugador.
     * */
    public static void nuevoJugador() {
        System.out.println(); //Espacio
        String nombre = PeticionDatos.pedirCadena("Nombre jugador "+(Jugador.getTotalJugadores()+1)+": ");
//        Tablero tablero=new Tablero(4,4,2);
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
        System.out.println(ANSI_BBLUE+"** Posición de los barcos **"+ANSI_RESET);

        for(int i=0;i<2;i++) {
            do {
                posicion = 2; //posicion tiene que tener un valor por defecto antes de realizar todo lo de abajo, si no
                //cuando se pide el segundo jugador posicion aun tiene los valores del jugador anterior y fallan las condiciones.
                int p = PeticionDatos.pedirEnteroPositivo(true, "0-Horizontal, 1-Vertical"+"\n"+ANSI_GREEN+"> "+ANSI_RESET);
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


    /**
     * Funcion para pedir la fila de la casilla
     * @return coor1 - entero válido introducido por teclado.
     * */
    public static int pedirFila(){
        System.out.println(ANSI_BBLUE + "** Introduce las coordenadas **" + ANSI_RESET);
        //Llamo a las funciones de peticion para enteros y guardo el valor en coor1.
        int coor1 = PeticionDatos.pedirEnteroPositivo(true, ">fila: ");
        return coor1;
    }

    /**
     * Funcion para pedir la columna de la casilla
     * @return coor2 - entero válido introducido por teclado.
     * */
    public static int pedirColumna(){

        //Para que devuelva ambos valores por separado tengo que separar las peticiones en dos funciones.
        int coor2 = PeticionDatos.pedirEnteroPositivo(true, ">columna: ");
        return coor2;

    }

    /**
     * Funcion que crea el tablero 1 y le pasa datos de objetos por los argumentos.
     * */
    public static void tablero1(){
        tablero1=new Tablero(4,4,2); //Se instancia un objeto tablero para cada jugador.
        int p1=barcos[0].getPosicion();
        Barco b1=barcos[0];
        int cod1=barcos[0].getCodBarco();
        int p2=barcos[1].getPosicion();
        Barco b2=barcos[1];
        int cod2=barcos[1].getCodBarco();
        tablero1.gestionTablero(p1, p2, b1, b2, cod1, cod2, jugadores[0]);    //Se llama a la funcion matriz para crear ambos tableros.
    }
    /**
     * Funcion que crea el tablero 2 y le pasa datos de objetos por los argumentos.
     * */
    public static void tablero2(){
        tablero2=new Tablero(4,4,2); //Se instancia un objeto tablero para cada jugador.
        int p1=barcos[2].getPosicion();
        Barco b1=barcos[2];
        int cod1=barcos[2].getCodBarco();
        int p2=barcos[3].getPosicion();
        Barco b2=barcos[3];
        int cod2=barcos[3].getCodBarco();
        tablero2.gestionTablero(p1, p2, b1, b2, cod1, cod2, jugadores[1]);    //Se llama a la funcion matriz para crear ambos tableros.
    }

    //He decidido dividir los turnos en dos funciones, creo que es la mejor manera (o la unica que he encontrado).

    /**
     * Funcion que gestiona el turno del jugador 1.
     * */
    public static void turnoJ1(){
        int intentos= jugadores[0].getIntentos(); //Se obtienen los intentos del jugador 1 se guardan en "intentos"
        int barcosRestantes=jugadores[1].getBarcosRestantes(); //Se necesita saber los barcos que le quedan al contrincante
        Barco b1=barcos[2]; //Los barcos también se tienen que invertir porque se les resta una casilla cuando son tocados por un disparo.
        Barco b2=barcos[3];
        int coor1, coor2;

        char matrizV[][]=jugadores[1].getTableroV(); //Tablero visible del jugador 2
        int matriz[][]=jugadores[1].getTablero(); //Tablero oculto del jugador 2, lo necesito para las condiciones

        System.out.println(); //Espacio
        System.out.println(ANSI_YELLOW+"** Turno jugador 1 **"+ANSI_RESET);
        System.out.println(); //Espacio
        System.out.println(ANSI_CYAN+"════════Tablero════════"+ANSI_RESET);
        /*Los tableros visibles se tienen que intercambiar para poder descubrir las posiciones del contrincante,
         si no, se estaría descubriendo el tablero del propio jugador. Los tableros ocultos siguen guardados con su jugador.*/
        GestionArray.mostrarMatrizCaracter(matrizV);    //Se imprime el tablero visible del contrincante

        do{
            coor1=JuegoHundido.pedirFila(); //Se llama a las funciones para pedir las coordenadas y se guarda el entero que devuelven en coor1 y coor2
            coor2=JuegoHundido.pedirColumna();
            if(coor1>matriz.length | coor2>matriz.length){  //Si se introduce un valor fuera de rango, se pedira hasta que este dentro del rango del tablero oculto.
                System.out.println(ANSI_RED + "El valor esta fuera de rango. Vuelve a intentarlo." + ANSI_RESET);
            }
        }while (coor1>matriz.length | coor2>matriz.length);

        //Se llama a comprobarDisparo para comprobar si las coord coinciden con la posicion de algun barco.
        boolean control= tablero1.comprobarDisparo(b1, b2, coor1, coor2, jugadores[1]);

        //CONDICIONES
        if (control == true && matrizV[coor1][coor2]=='*' && matrizV[coor1][coor2]!='T') {
            intentos++; //Los intentos van a incrementarse aciertes o falles.
            jugadores[0].setIntentos(intentos); //Para las variables necesito llamar a los setter para actualizarlas.
            if (matriz[coor1][coor2]==b1.getCodBarco()) {
                int longitud=b1.getLongitud();
                longitud--;                 //Si la condicion anterior se cumple se les resta 1 a la longitud total del barco 1.
                b1.setLongitud(longitud);
//                System.out.println(b1.getLongitud());
            }else{
                int longitud=b2.getLongitud();
                longitud--;                 //Si no, se entiende que es el barco 2.
                b2.setLongitud(longitud);
//                System.out.println(b2.getLongitud());
            }
            //Si la longitud del barco llega a 0 y las coordenadas contienen su codigo, las casillas que ocupa cambian a Hundido, se resta un barco y se informa al jugador.
            if (b1.getLongitud()==0 && matriz[coor1][coor2]==b1.getCodBarco()){
                barcosRestantes--;
                jugadores[1].setBarcosRestantes(barcosRestantes); //Se le resta un barco al contrincante.
                System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
                matrizV[b1.getC1row()][b1.getC1col()]='H';
                matrizV[b1.getC2row()][b1.getC2col()]='H'; //Se sustituyen las dos casillas que ocupa el barco enemigo por una H (hundido)
            }else if (b2.getLongitud()==0 && matriz[coor1][coor2]==b2.getCodBarco()){
                barcosRestantes--;
                jugadores[1].setBarcosRestantes(barcosRestantes); //Se le resta un barco al contrincante.
                System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
                matrizV[b2.getC1row()][b2.getC1col()]='H';
                matrizV[b2.getC2row()][b2.getC2col()]='H';
            }else { //Si la longitud aún es mayor que 0 se sustituye la casilla por un T (tocado).
                System.out.println(ANSI_YELLOW + "¡Barco tocado!" + ANSI_RESET);
                matrizV[coor1][coor2] = 'T';
            }

        } else if (control == false && matrizV[coor1][coor2]=='*'){
            intentos++;
            jugadores[0].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "¡Agua! Llevas "+intentos+" intentos."+ ANSI_RESET);
            matrizV[coor1][coor2]='A'; //En caso de no haber nada en las coordenadas introducidas, se sustituye la casilla por una A (agua)
        } else if (control == true && matrizV[coor1][coor2]=='T'){
            intentos++;
            jugadores[0].setIntentos(intentos);
            //Si ya se ha descubierto una casilla con parte de un barco se informa con este mensaje.
            System.out.println(ANSI_YELLOW + "¡Hey! Fíjate bien, ¡esa casilla ya esta descubierta! Llevas "+intentos+" intentos."+ ANSI_RESET);
        } else if (control == false && matrizV[coor1][coor2]=='A' && intentos>27) {  //Pequeño Easter Egg. Ni caso.
            intentos++;
            jugadores[0].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "Esta casilla ya esta descubierta y encima vacía...¿Necesitas gafas? Llevas "+intentos+" intentos."+ ANSI_RESET);
        }else{
            intentos++;
            jugadores[0].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "Ya has descubierto esta casilla. Llevas "+intentos+" intentos."+ ANSI_RESET);
            //Si ya se ha descubierto una casilla vacía se informa con este mensaje.
        }
    }

    /**
     * Funcion que gestiona el turno del jugador 2.
     * */
    public static void turnoJ2(){
        int intentos= jugadores[1].getIntentos();
        int barcosRestantes=jugadores[0].getBarcosRestantes(); //Se necesita saber los barcos que le quedan al contrincante.
        Barco b1=barcos[0]; //Los barcos también se tienen que invertir porque se les resta una casilla cuando son tocados por un disparo.
        Barco b2=barcos[1];
        int coor1, coor2;

        char matrizV[][]=jugadores[0].getTableroV(); //Tablero visible del jugador 1
        int matriz[][]=jugadores[0].getTablero(); //Tablero oculto del jugador 1

        System.out.println(); //Espacio
        System.out.println(ANSI_YELLOW+"** Turno jugador 2 **"+ANSI_RESET);
        System.out.println(); //Espacio
        System.out.println(ANSI_CYAN+"--------Tablero--------"+ANSI_RESET);
        GestionArray.mostrarMatrizCaracter(matrizV);

        do{
            coor1=JuegoHundido.pedirFila();
            coor2=JuegoHundido.pedirColumna();
            if(coor1>matriz.length | coor2>matriz.length){
                System.out.println(ANSI_RED + "El valor esta fuera de rango. Vuelve a intentarlo." + ANSI_RESET);
            }
        }while (coor1>matriz.length | coor2>matriz.length);
        boolean control= tablero2.comprobarDisparo(b1, b2, coor1, coor2, jugadores[0]);


        if (control == true && matrizV[coor1][coor2]=='*' && matrizV[coor1][coor2]!='T') {
            intentos++;
            jugadores[1].setIntentos(intentos);
            if (matriz[coor1][coor2]==b1.getCodBarco()) {
                int longitud=b1.getLongitud();
                longitud--;
                b1.setLongitud(longitud);
//                System.out.println(b1.getLongitud());
            }else{
                int longitud=b2.getLongitud();
                longitud--;
                b2.setLongitud(longitud);
//                System.out.println(b2.getLongitud());
            }
            //Si la longitud del barco llega a 0 y las coordenadas contienen su codigo, las casillas que ocupa cambian a Hundido, se resta un barco y se informa al jugador.
            if (b1.getLongitud()==0 && matriz[coor1][coor2]==b1.getCodBarco()){
                barcosRestantes--;
                jugadores[0].setBarcosRestantes(barcosRestantes); //Se le resta un barco al contrincante.
                System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
                matrizV[b1.getC1row()][b1.getC1col()]='H';
                matrizV[b1.getC2row()][b1.getC2col()]='H';
            }else if (b2.getLongitud()==0 && matriz[coor1][coor2]==b2.getCodBarco()){
                barcosRestantes--;
                jugadores[0].setBarcosRestantes(barcosRestantes); //Se le resta un barco al contrincante.
                System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
                matrizV[b2.getC1row()][b2.getC1col()]='H';
                matrizV[b2.getC2row()][b2.getC2col()]='H';
            }else {
                System.out.println(ANSI_YELLOW + "¡Barco tocado!" + ANSI_RESET);
                matrizV[coor1][coor2] = 'T';
            }

        } else if (control == false && matrizV[coor1][coor2]=='*'){
            intentos++; //Entiendo que el número de intentos suma cada vez que fallas, no cuando aciertas.
            jugadores[1].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "¡Agua! Llevas "+intentos+" intentos."+ ANSI_RESET);
            matrizV[coor1][coor2]='A';
        } else if (control == true && matrizV[coor1][coor2]=='T'){
            intentos++;
            jugadores[1].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "¡Hey! Fíjate bien, ¡esa casilla ya esta descubierta! Llevas "+intentos+" intentos."+ ANSI_RESET);
        } else if (control == false && matrizV[coor1][coor2]=='A' && intentos>27) {  //Pequeño Easter Egg. Ni caso.
            intentos++;
            jugadores[1].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "Esta casilla ya esta descubierta y encima vacía...¿Necesitas gafas? Llevas "+intentos+" intentos."+ ANSI_RESET);
        }else{
            intentos++;
            jugadores[1].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "Ya has descubierto esta casilla. Llevas "+intentos+" intentos."+ ANSI_RESET);
        }

    }


    public static void main(String[] args) {

        char respuesta=' ';

        System.out.println(ANSI_BLUE+
                "                                            \n"+
                "                                     # #  ( )\n" +
                "                                  ___#_#___|__\n" +
                "                              _  |____________|  _\n" +
                "                       _=====| | |            | | |==== _\n" +
                "                 =====| |.---------------------------. | |====\n" +
                "   <--------------------'   .  .  .  .  .  .  .  .   '--------------/\n" +
                "     \\                            F5 HUNDIDO                       /\n" +
                "      \\___________________________________________________________/\n" +
                "  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+ANSI_RESET);

        do {
            mostrarMenu();
            //Llamadas a funciones segun la opcion del menu elegida.
            switch (opcion) {
                case 0:
                    System.out.println(ANSI_YELLOW+"Saliendo del juego..."+ANSI_RESET);
                    break;
                case 1:
                    System.out.println(ANSI_BLUE+"NIVEL 1 -- Hunde los 2 barcos del contrincante"+ANSI_RESET);
                    for(int x=0;x<2;x++) {
                        nuevoJugador(); //Se piden los datos de ambos jugadores
                        nuevoBarco();   //Además de la posicion de los barcos
                    }
                    //Se crean ambos tableros
                    tablero1();
                    tablero2();
                    //Comienzan los turnos y no paran hasta que algun jugador hunda todos los barcos del otro.
                    do {
                        turnoJ1();
                        if (jugadores[1].getBarcosRestantes()==0 | jugadores[0].getBarcosRestantes()==0){
                            break;  //Si j1 hunde los 2 barcos de j2 se para el do-while
                        }
                        turnoJ2();
                        if (jugadores[0].getBarcosRestantes()==0){
                            break; //Si j2 hunde los 2 barcos de j1 se para el do-while
                        }
                    }while (true);

                    //Segun quien haya hundido los barcos se muestra un mensaje u otro
                    if (jugadores[1].getBarcosRestantes()==0 ){
                        int total=jugadores[0].getIntentos()-tablero2.barcos;
                        System.out.println(ANSI_GREEN+"¡Enhorabuena, "+jugadores[0].getNombre()+" ha superado el nivel 1! Con "+total+" intentos."+ANSI_RESET);
                        break;  //Una vez se muestre el mensaje se sale del switch
                    }else if (jugadores[0].getBarcosRestantes()==0 ){
                        int total=jugadores[1].getIntentos()-tablero1.barcos;
                        System.out.println(ANSI_GREEN+"¡Enhorabuena, "+jugadores[1].getNombre()+" ha superado el nivel 1! Con "+total+" intentos."+ANSI_RESET);
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

