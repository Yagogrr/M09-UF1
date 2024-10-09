/*
 * permutaAlfabet(): permuta el alfabeto en base a objeto RANDOM
 * xifraPoliAlfabet(): despues de comprobar que la letra no es una letra, invoca a permutaAlfabet
 * desxifraPoliAlfa(): lo mismo que xifraPoliAlfabet
 * initRandom(): inicializa la variable Random
 * 
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class Polialfabetic {
    public static final char[] ABECEDARIO_MAYUS = {'A','Á', 'À', 'Ä', 'B', 'C', 'Ç', 'D', 'É', 'È', 'Ë', 'E', 'F', 'G', 'H', 'Í', 'Ì', 'Ï', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'Ó', 'Ò', 'Ö', 'O', 'P', 'Q', 'R', 'S', 'T', 'Ú', 'Ù', 'Ü', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static char[] ABECEDARIO_PERMUTADO;
    public static Random RANDOM;

    public static void permutaAlfabet(){
        //creamos una lista y la completamos con el abecedario normal
        List<Character> abecedarioList = new ArrayList<>(); 
        for(int i = 0;i<ABECEDARIO_MAYUS.length;i++){
            abecedarioList.add(ABECEDARIO_MAYUS[i]);
        }

        //lo ponemos random
        Collections.shuffle(abecedarioList,RANDOM);

        //inicializamos el abecedario permutado
        ABECEDARIO_PERMUTADO = new char[abecedarioList.size()];
        for (int i = 0; i < abecedarioList.size(); i++) {
            ABECEDARIO_PERMUTADO[i] = abecedarioList.get(i);
        }
    }
    public static String xifraPoliAlfa(String cadena){
        StringBuffer cadenaCifrada = new StringBuffer();

        //bucle que recorre la cadena
        for(int i = 0;i<cadena.length();i++){
            char letra = cadena.charAt(i);

            //si es un espacio o un punto etc... simplemente lo añade 
            if(!Character.isLetter(letra)){
                cadenaCifrada.append(letra);
                continue;
            }

            //permutamos el alfabeto
            permutaAlfabet();

            //bucle que recorre el abecedario
            for(int j = 0;j<ABECEDARIO_MAYUS.length;j++){

                //si la letra coincide, añade su letra permutada
                if(letra==Character.toLowerCase(ABECEDARIO_MAYUS[j])||letra==ABECEDARIO_MAYUS[j]){
                    char letraP = ABECEDARIO_PERMUTADO[j];

                    //distingue entre mayusculas y minusculas
                    letraP = Character.isLowerCase(letra) ? Character.toLowerCase(letraP) : letraP;
                    cadenaCifrada.append(letraP);
                    break;
                }
            }
        }
        return cadenaCifrada.toString();
    }
    public static String desxifraPoliAlfa(String cadena){
        StringBuffer cadenaDescifrada = new StringBuffer();

        //bucle que recorre la cadena
        for(int i = 0;i<cadena.length();i++){
            char letra = cadena.charAt(i);

            //si es un espacio o un punto etc... simplemente lo añade 
            if(!Character.isLetter(letra)){
                cadenaDescifrada.append(letra);
                continue;
            }

            //permuta el alfabeto
            permutaAlfabet();

            //bucle que recorre el abecedario permutado
            for(int j = 0;j<ABECEDARIO_PERMUTADO.length;j++){

                //si la letra coincide, añade su letra permutada
                if(letra==Character.toLowerCase(ABECEDARIO_PERMUTADO[j])||letra==ABECEDARIO_PERMUTADO[j]){
                    char letraP = ABECEDARIO_MAYUS[j];

                    //distingue entre mayusculas y minusculas
                    letraP = Character.isLowerCase(letra) ? Character.toLowerCase(letraP) : letraP;
                    cadenaDescifrada.append(letraP);
                    break;
                }
            }
        }
        return cadenaDescifrada.toString();
    }
    public static void initRandom(int contraseña){
        RANDOM = new Random(contraseña);
    }
    public static void main ( String [] args ) {
        String msgs[] = { "Test 01 àrbritre, coixí, Perímetre" ,
        "Test 02 Taüll, DÍA, año" ,
        "Test 03 Peça, Òrrius, Bòvila" };
        String msgsXifrats [] = new String [ msgs. length ];
        System . out . println ( "Xifratge: \n --------" );
        for ( int i = 0; i < msgs. length ; i ++) {
            initRandom ( 1234 );
            msgsXifrats [ i ] = xifraPoliAlfa ( msgs[ i ]);
            System . out . printf ( "%-34s -> %s%n", msgs[ i ], msgsXifrats [ i ]);
        }
        System . out . println ( "Desxifratge: \n -----------" );
        for ( int i = 0; i < msgs. length ; i ++) {
            initRandom ( 1234 );
            String msg = desxifraPoliAlfa ( msgsXifrats [ i ]);
            System . out . printf ( "%-34s -> %s%n", msgsXifrats [ i ], msg);
        }
    }
}