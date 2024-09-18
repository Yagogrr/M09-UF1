/*
 * Clase de cifrado con dos funciones:
 *  private void xifraRot13(String cadena): cifrara una cadena de texto substituyendo una letra por la que ocupa la 
 *  posición 13 despues de esta.
 *  private void desxifraRot13(String cadena): descifra a la inversa que xifraRot13
 */
public class Rot13{
    private final char[] abecedarioMayus = {'A', 'B', 'C', 'Ç','D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N','Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private final char[] abecedarioMinus = {'a', 'b', 'c', 'ç','d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n','ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    //función para cifrar
    private void xifraRot13(String cadena){
        if(cadena.isBlank()){ return;} //si la cadena esta vacia no hace nada

        String cadenaCifrada = "";
        for(int letraCadenaINT = 0;letraCadenaINT<cadena.length();letraCadenaINT++){ //bucle que recorre la cadena que se introduce en el parametro
            char letraCadenaCHR = cadena.charAt(letraCadenaINT);
            for(int letraAbecedarioINT = 0;letraAbecedarioINT<abecedarioMayus.length;letraAbecedarioINT++){ //bucle que itera en el abecedario mayusculas
                char letraAbecedarioCHR = abecedarioMayus[letraAbecedarioINT];
                if(letraAbecedarioCHR!=letraCadenaCHR){ //si no es la misma letra, continua con la siguiente letra
                    continue;
                }
            }
        }
    }
}