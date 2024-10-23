package iticbcn.xifratge;
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

public class XifradorPolialfabetic implements Xifrador{
    public static final char[] ABECEDARIO_MAYUS = {'A','Á', 'À', 'Ä', 'B', 'C', 'Ç', 'D', 'É', 'È', 'Ë', 'E', 'F', 'G', 'H', 'Í', 'Ì', 'Ï', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'Ó', 'Ò', 'Ö', 'O', 'P', 'Q', 'R', 'S', 'T', 'Ú', 'Ù', 'Ü', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public char[] abecedario_permutado;
    public Random random;

    public TextXifrat xifra(String msg, String clau) throws  ClauNoSuportada{
        try {
            initRandom(Integer.parseInt(clau));
            String resultado = xifraPoliAlfa(msg);
            return new TextXifrat(resultado.getBytes());
        } catch ( NumberFormatException e){
            System.out.println("La clau de Polialfabètic ha de ser un String convertible a long");
            return null;
        }
        
    }
    public String desxifra(TextXifrat xifrat, String clau)  throws ClauNoSuportada{

    }

    public void permutaAlfabet(){
        //creamos una lista y la completamos con el abecedario normal
        List<Character> abecedarioList = new ArrayList<>(); 
        for(int i = 0;i<ABECEDARIO_MAYUS.length;i++){
            abecedarioList.add(ABECEDARIO_MAYUS[i]);
        }

        //lo ponemos random
        Collections.shuffle(abecedarioList,random);

        //inicializamos el abecedario permutado
        abecedario_permutado = new char[abecedarioList.size()];
        for (int i = 0; i < abecedarioList.size(); i++) {
            abecedario_permutado[i] = abecedarioList.get(i);
        }
    }
    public String xifraPoliAlfa(String cadena){
        return xifraDesxifra(cadena, true);
    }
    public String desxifraPoliAlfa(String cadena){
        return xifraDesxifra(cadena, false);
    }
    public void initRandom(int contraseña){
        random = new Random(contraseña);
    }
    public String xifraDesxifra(String cadena,boolean xifra){
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
                if(xifra){
                    //si la letra coincide, añade su letra permutada
                    if(letra==Character.toLowerCase(ABECEDARIO_MAYUS[j])||letra==ABECEDARIO_MAYUS[j]){
                        char letraP = abecedario_permutado[j];

                        //distingue entre mayusculas y minusculas
                        letraP = Character.isLowerCase(letra) ? Character.toLowerCase(letraP) : letraP;
                        cadenaCifrada.append(letraP);
                        break;
                    }
                } 
                else{
                    //si la letra coincide, añade su letra permutada
                    if(letra==Character.toLowerCase(abecedario_permutado[j])||letra==abecedario_permutado[j]){
                        char letraP = ABECEDARIO_MAYUS[j];

                        //distingue entre mayusculas y minusculas
                        letraP = Character.isLowerCase(letra) ? Character.toLowerCase(letraP) : letraP;
                        cadenaCifrada.append(letraP);
                        break;
                    }
                }
            }
        }
        return cadenaCifrada.toString();
    }
    public void main ( String [] args ) {
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