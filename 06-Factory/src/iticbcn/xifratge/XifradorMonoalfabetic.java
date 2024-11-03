package iticbcn.xifratge;

/*
 * Constantes:
 *  -Abecedario estándar :)
 *  -Abecedario permutado: es un abecedario calculado por la función permutaAlfabet(alfabet) :)
 * 
 * Funciones: 
 *  -permutaAlfabet(alfabet): te devuelve el abecedario permutado :)
 *  -xifraMonoAlfa(cadena): Te cifra con el abecedario permutado :)
 *  -desxifraMonoAlfa(cadena): Te descifra con el abecedario permutado
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class XifradorMonoalfabetic implements Xifrador {
    public static final char[] ABECEDARIO_MAYUS = {
        'A','Á', 'À', 'Ä', 'B', 'C', 'Ç', 'D', 'É', 'È', 'Ë', 'E',
        'F', 'G', 'H', 'Í', 'Ì', 'Ï', 'I', 'J', 'K', 'L', 'M', 'N',
        'Ñ', 'Ó', 'Ò', 'Ö', 'O', 'P', 'Q', 'R', 'S', 'T', 'Ú', 'Ù',
        'Ü', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    public final char[] ABECEDARIO_PERMUTADO;

    public XifradorMonoalfabetic(){
        this.ABECEDARIO_PERMUTADO = permutaAlfabet(ABECEDARIO_MAYUS);
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if(clau != null){
            throw new ClauNoSuportada("La clau de Monoalfabètic ha de ser null.");
        }
        String xifrat = xifraMonoAlfa(msg).toString();
        return new TextXifrat(xifrat.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if(clau != null){
            throw new ClauNoSuportada("La clau de Monoalfabètic ha de ser null.");
        }
        String desxifrat = desxifraMonoAlfa(new String(xifrat.getBytes())).toString();
        return desxifrat;
    }

    private char[] permutaAlfabet(char[] abecedarioChar){
        // Creamos una lista y la completamos con el abecedario normal
        List<Character> abecedarioList = new ArrayList<>(); 
        for(char c : abecedarioChar){
            abecedarioList.add(c);
        }

        // Lo ponemos en orden aleatorio
        Collections.shuffle(abecedarioList);

        // Volvemos a convertirlo a un char[]
        char[] abecedarioPermutado = new char[abecedarioList.size()];
        for (int i = 0; i < abecedarioList.size(); i++) {
            abecedarioPermutado[i] = abecedarioList.get(i);
        }
        return abecedarioPermutado;
    }

    private StringBuilder xifraMonoAlfa(String cadena){
        StringBuilder cadenaCifrada = new StringBuilder();

        // Bucle que recorre la cadena
        for(int i = 0; i < cadena.length(); i++){
            char letra = cadena.charAt(i);

            // Si no es una letra, simplemente la añade
            if(!Character.isLetter(letra)){
                cadenaCifrada.append(letra);
                continue;
            }

            // Bucle que recorre el abecedario
            boolean encontrada = false;
            for(int j = 0; j < ABECEDARIO_MAYUS.length; j++){
                if(letra == ABECEDARIO_MAYUS[j] || letra == Character.toLowerCase(ABECEDARIO_MAYUS[j])){
                    char letraP = ABECEDARIO_PERMUTADO[j];
                    // Distingue entre mayúsculas y minúsculas
                    letraP = Character.isLowerCase(letra) ? Character.toLowerCase(letraP) : letraP;
                    cadenaCifrada.append(letraP);
                    encontrada = true;
                    break;
                }
            }

            // Si no se encuentra la letra en el abecedario, se añade tal cual
            if(!encontrada){
                cadenaCifrada.append(letra);
            }
        }
        return cadenaCifrada;
    }

    private StringBuilder desxifraMonoAlfa(String cadena){
        StringBuilder cadenaDescifrada = new StringBuilder();

        // Bucle que recorre la cadena
        for(int i = 0; i < cadena.length(); i++){
            char letra = cadena.charAt(i);

            // Si no es una letra, simplemente la añade
            if(!Character.isLetter(letra)){
                cadenaDescifrada.append(letra);
                continue;
            }

            // Bucle que recorre el abecedario permutado
            boolean encontrada = false;
            for(int j = 0; j < ABECEDARIO_PERMUTADO.length; j++){
                if(letra == ABECEDARIO_PERMUTADO[j] || letra == Character.toLowerCase(ABECEDARIO_PERMUTADO[j])){
                    char letraP = ABECEDARIO_MAYUS[j];
                    // Distingue entre mayúsculas y minúsculas
                    letraP = Character.isLowerCase(letra) ? Character.toLowerCase(letraP) : letraP;
                    cadenaDescifrada.append(letraP);
                    encontrada = true;
                    break;
                }
            }

            // Si no se encuentra la letra en el abecedario permutado, se añade tal cual
            if(!encontrada){
                cadenaDescifrada.append(letra);
            }
        }
        return cadenaDescifrada;
    }
}
