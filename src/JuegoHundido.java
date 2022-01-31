
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
    private static Barco b;
    private static int codBarco=0;

    //Arrays de clase para objetos
    private static Barco barcos[]=new Barco[4]; //Array para meter los objetos barco.
    private  static Jugador jugadores[]=new Jugador[2]; //Arra y para almacenar objetos jugador.


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
    public static void nuevoJugador() {
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
        System.out.println(ANSI_BLUE+"**Posición de los barcos**"+ANSI_RESET);

        for(int i=0;i<2;i++) {
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


    /**
     * Funcion para pedir la fila de la casilla
     * @return coor1 - entero válido introducido por teclado.
     * */
    public static int pedirFila(){
        System.out.println(ANSI_BLUE + "**Introduce las coordenadas**" + ANSI_RESET);
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

    private static Tablero tablero1, tablero2; //MOVER

    public static void tablero1(){
        tablero1=new Tablero(4,4,2); //Se instancia un objeto tablero para cada jugador.
//        tablero1=jugadores[0].getTablero();
        int p1=barcos[0].getPosicion();
        Barco b1=barcos[0];
        int cod1=barcos[0].getCodBarco();
        int p2=barcos[1].getPosicion();
        Barco b2=barcos[1];
        int cod2=barcos[1].getCodBarco();
        tablero1.gestionTablero(p1, p2, b1, b2, cod1, cod2, jugadores[0]);    //Se llama a la funcion matriz para crear ambos tableros.
    }
    public static void tablero2(){
        tablero2=new Tablero(4,4,2); //Se instancia un objeto tablero para cada jugador.
//        tablero2=jugadores[1].getTablero();
        int p1=barcos[2].getPosicion();
        Barco b1=barcos[2];
        int cod1=barcos[2].getCodBarco();
        int p2=barcos[3].getPosicion();
        Barco b2=barcos[3];
        int cod2=barcos[3].getCodBarco();
        tablero2.gestionTablero(p1, p2, b1, b2, cod1, cod2, jugadores[1]);    //Se llama a la funcion matriz para crear ambos tableros.
    }

    /**
     * Funcion que gestiona el turno del jugador 1.
     * */
    public static void turnoJ1(){
        int intentos= jugadores[0].getIntentos();
        int barcosRestantes=jugadores[1].getBarcosRestantes(); //Se necesita saber los barcos que le quedan al contrincante
        Barco b1=barcos[2]; //Los barcos también se tienen que invertir porque se les resta una casilla cuando son tocados por un disparo.
        Barco b2=barcos[3];
        int coor1, coor2;

        char matrizV[][]=jugadores[1].getTableroV(); //Tablero visible del jugador 2
        int matriz[][]=jugadores[1].getTablero(); //Tablero oculto del jugador 2, lo necesito para las condiciones

        System.out.println(ANSI_YELLOW+"**Turno jugador 1**"+ANSI_RESET);
        /*Los tableros visibles se tienen que intercambiar para poder descubrir las posiciones del contrincante,
         si no, se estaría descubriendo el tablero del propio jugador. Los tableros ocultos siguen guardados con su jugador.*/
        GestionArray.mostrarMatrizCaracter(matrizV);

        do{
            coor1=JuegoHundido.pedirFila();
            coor2=JuegoHundido.pedirColumna();
            if(coor1>matriz.length | coor2>matriz.length){
                System.out.println(ANSI_RED + "El valor esta fuera de rango. Vuelve a intentarlo." + ANSI_RESET);
            }
        }while (coor1>matriz.length | coor2>matriz.length);

        boolean control= tablero1.comprobarDisparo(b1, b2, coor1, coor2, jugadores[1]);


        if (control == true && matrizV[coor1][coor2]=='*' && matrizV[coor1][coor2]!='T') {
            intentos++;
            jugadores[0].setIntentos(intentos);
            if (matriz[coor1][coor2]==b1.getCodBarco()) {
                int longitud=b1.getLongitud();
                longitud--;
                b1.setLongitud(longitud);
                System.out.println(b1.getLongitud());
            }else{
                int longitud=b2.getLongitud();
                longitud--;
                b2.setLongitud(longitud);
                System.out.println(b2.getLongitud());
            }
            //Si la longitud del barco llega a 0 y las coordenadas contienen su codigo, las casillas que ocupa cambian a Hundido, se resta un barco y se informa al jugador.
            if (b1.getLongitud()==0 && matriz[coor1][coor2]==b1.getCodBarco()){
                barcosRestantes--;
                jugadores[1].setBarcosRestantes(barcosRestantes); //Se le resta un barco al contrincante.
                System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
                matrizV[b1.getC1row()][b1.getC1col()]='H';
                matrizV[b1.getC2row()][b1.getC2col()]='H';
            }else if (b2.getLongitud()==0 && matriz[coor1][coor2]==b2.getCodBarco()){
                barcosRestantes--;
                jugadores[1].setBarcosRestantes(barcosRestantes); //Se le resta un barco al contrincante.
                System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
                matrizV[b2.getC1row()][b2.getC1col()]='H';
                matrizV[b2.getC2row()][b2.getC2col()]='H';
            }else {
                System.out.println(ANSI_YELLOW + "¡Barco tocado!" + ANSI_RESET);
                matrizV[coor1][coor2] = 'T';
            }

        } else if (control == false && matrizV[coor1][coor2]=='*'){
            intentos++; //Entiendo que el número de intentos suma cada vez que fallas, no cuando aciertas.
            jugadores[0].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "¡Agua! Llevas "+intentos+" intentos."+ ANSI_RESET);
            matrizV[coor1][coor2]='A';
        } else if (control == true && matrizV[coor1][coor2]=='T'){
            intentos++;
            jugadores[0].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "¡Hey! Fíjate bien, ¡esa casilla ya esta descubierta! Llevas "+intentos+" intentos."+ ANSI_RESET);
        } else if (control == false && matrizV[coor1][coor2]=='A' && intentos>27) {  //Pequeño Easter Egg. Ni caso.
            intentos++;
            jugadores[0].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "Esta casilla ya esta descubierta y encima vacía...¿Necesitas gafas? Llevas "+intentos+" intentos."+ ANSI_RESET);
        }else{
            intentos++;
            jugadores[0].setIntentos(intentos);
            System.out.println(ANSI_YELLOW + "Ya has descubierto esta casilla. Llevas "+intentos+" intentos."+ ANSI_RESET);
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

        System.out.println(ANSI_YELLOW+"**Turno jugador 2**"+ANSI_RESET);
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
                System.out.println(b1.getLongitud());
            }else{
                int longitud=b2.getLongitud();
                longitud--;
                b2.setLongitud(longitud);
                System.out.println(b2.getLongitud());
            }
            //Si la longitud del barco llega a 0 y las coordenadas contienen su codigo, las casillas que ocupa cambian a Hundido, se resta un barco y se informa al jugador.
            if (b1.getLongitud()==0 && matriz[coor1][coor2]==b1.getCodBarco()){
                barcosRestantes--;
                jugadores[0].setBarcosRestantes(barcosRestantes); //Se le resta un barco al contrincante.
                System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
                matriz[b1.getC1row()][b1.getC1col()]='H';
                matriz[b1.getC2row()][b1.getC2col()]='H';
            }else if (b2.getLongitud()==0 && matriz[coor1][coor2]==b2.getCodBarco()){
                barcosRestantes--;
                jugadores[0].setBarcosRestantes(barcosRestantes); //Se le resta un barco al contrincante.
                System.out.println(ANSI_YELLOW + "¡Barco tocado y hundido! " + "Restantes: " + barcosRestantes + ANSI_RESET);
                matriz[b2.getC1row()][b2.getC1col()]='H';
                matriz[b2.getC2row()][b2.getC2col()]='H';
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

        System.out.println(ANSI_BLUE+"                                     # #  ( )\n" +
                "                                  ___#_#___|__\n" +
                "                              _  |____________|  _\n" +
                "                       _=====| | |            | | |==== _\n" +
                "                 =====| |.---------------------------. | |====\n" +
                "   <--------------------'   .  .  .  .  .  .  .  .   '--------------/\n" +
                "     \\                           F5 HUNDIDO                        /\n" +
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
                    System.out.println(ANSI_BLUE+"NIVEL 1 -- Hunde los 2 barcos"+ANSI_RESET);
                    for(int x=0;x<2;x++) {
                        nuevoJugador(); //Se piden los datos de ambos jugadores
                        nuevoBarco();   //Además de la posicion de los barcos
                    }

                    tablero1();
                    tablero2();
                    //TURNOS
                    do {
                        turnoJ1();
                        if (jugadores[1].getBarcosRestantes()==0 | jugadores[0].getBarcosRestantes()==0){
                            break;
                        }
                        turnoJ2();
                        if (jugadores[0].getBarcosRestantes()==0){
                            break;
                        }

                    }while (true);

                    if (jugadores[1].getBarcosRestantes()==0 ){
                        int total=jugadores[0].getIntentos()-tablero2.barcos;
                        System.out.println(ANSI_GREEN+"¡Enhorabuena, "+jugadores[0].getNombre()+" ha superado el nivel 1! Con "+total+" intentos."+ANSI_RESET);
                        break;
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

