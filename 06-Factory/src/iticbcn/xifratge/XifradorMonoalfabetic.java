package iticbcn.xifratge;
/*
 * Constantes:
 *  -Abecedario estandart :)
 *  -Abecedario permutado: es un abecedario calculado por la funcion permutaAlfabet(alfabet) :)
 * 
 * Funciones: 
 *  -permutaAlfabet(alfabet): te devuelve el abecedario permutado :)
 *  -xifraMonoAlfa(cadena): Te cifra con el abecedario permutado :)
 *  -desxifraMonoAlfa(cadena): Te descifra con el abecedario pemutado
 *
 * 
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class XifradorMonoalfabetic implements Xifrador {
    public static final char[] ABECEDARIO_MAYUS = {'A','Á', 'À', 'Ä', 'B', 'C', 'Ç', 'D', 'É', 'È', 'Ë', 'E', 'F', 'G', 'H', 'Í', 'Ì', 'Ï', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'Ó', 'Ò', 'Ö', 'O', 'P', 'Q', 'R', 'S', 'T', 'Ú', 'Ù', 'Ü', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public final char[] ABECEDARIO_PERMUTADO = permutaAlfabet(ABECEDARIO_MAYUS);

    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada{
        String xifrat = xifraMonoAlfa(msg).toString();
        return new TextXifrat(xifrat.getBytes());
    }
    public String desxifra(TextXifrat xifrat, String clau)  throws ClauNoSuportada{
        String desxifrat = desxifraMonoAlfa(xifrat.toString()).toString();
        return desxifrat;
    }

    
    public char[] permutaAlfabet(char[] abecedarioChar){
        //creamos una lista y la completamos con el abecedario normal
        List<Character> abecedarioList = new ArrayList<>(); 
        for(int i = 0;i<ABECEDARIO_MAYUS.length;i++){
            abecedarioList.add(ABECEDARIO_MAYUS[i]);
        }

        //lo ponemos random
        Collections.shuffle(abecedarioList);

        //volvemos a convertirlo a un char[]
        char[] abecedarioPermutado = new char[abecedarioList.size()];
        for (int i = 0; i < abecedarioList.size(); i++) {
            abecedarioPermutado[i] = abecedarioList.get(i);
        }
        return abecedarioPermutado;
    }
    public StringBuffer xifraMonoAlfa(String cadena){
        StringBuffer cadenaCifrada = new StringBuffer();

        //bucle que recorre la cadena
        for(int i = 0;i<cadena.length();i++){
            char letra = cadena.charAt(i);

            //si es un espacio o un punto etc... simplemente lo añade 
            if(!Character.isLetter(letra)){
                cadenaCifrada.append(letra);
                continue;
            }
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
        return cadenaCifrada;
    }
    public StringBuffer desxifraMonoAlfa(String cadena){
        StringBuffer cadenaDescifrada = new StringBuffer();

        //bucle que recorre la cadena
        for(int i = 0;i<cadena.length();i++){
            char letra = cadena.charAt(i);

            //si es un espacio o un punto etc... simplemente lo añade 
            if(!Character.isLetter(letra)){
                cadenaDescifrada.append(letra);
                continue;
            }
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
        return cadenaDescifrada;
    }
}
