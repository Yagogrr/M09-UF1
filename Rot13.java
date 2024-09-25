/*
 * Clase de cifrado con dos funciones:
 *  private void xifraRot13(String cadena): cifrara una cadena de texto substituyendo una letra por la que ocupa la 
 *  posición 13 despues de esta.
 *  private void desxifraRot13(String cadena): descifra a la inversa que xifraRot13
 */
import java.util.Scanner;

public class Rot13{
    public static final char[] abecedarioMayus = {'A', 'B', 'C', 'Ç','D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N','Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static final char[] abecedarioMinus = {'a', 'b', 'c', 'ç','d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n','ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    //función para cifrar
    public static String xifraRot13(String cadena){
        if(cadena.isBlank()){ return "";} //si la cadena esta vacia no hace nada
        String cadenaCifrada = "";
        for(int letraCadenaINT = 0;letraCadenaINT<cadena.length();letraCadenaINT++){ //bucle que recorre la cadena que se introduce en el parametro
            char letraCadenaCHR = cadena.charAt(letraCadenaINT);
            for(int letraAbecedarioINT = 0;letraAbecedarioINT<abecedarioMayus.length;letraAbecedarioINT++){ //bucle que itera en el abecedario mayusculas
                char letraAbecedarioMayusCHR = abecedarioMayus[letraAbecedarioINT];
                char letraAbecedarioMinusCHR = abecedarioMinus[letraAbecedarioINT];
                if(letraAbecedarioMayusCHR!=letraCadenaCHR){ //si no es la misma letra en mayusculas, lo comprueba con las minusculas
                    if(letraAbecedarioMinusCHR!=letraCadenaCHR){
                        continue;
                    }
                }
                boolean sePasa = letraAbecedarioINT + 13 >= abecedarioMayus.length; // boolean que indica si se pasa o no
                if(sePasa){
                    if(Character.isUpperCase(letraCadenaCHR)){
                        cadenaCifrada += abecedarioMayus[(letraAbecedarioINT + 13) - abecedarioMayus.length];
                        continue;
                    }
                    cadenaCifrada += abecedarioMinus[(letraAbecedarioINT + 13) - abecedarioMayus.length];
                    continue;
                }
                if(Character.isUpperCase(letraCadenaCHR)){
                    cadenaCifrada += abecedarioMayus[letraAbecedarioINT + 13];
                    continue;
                }
                cadenaCifrada += abecedarioMinus[letraAbecedarioINT + 13];

            }
        }
        return cadenaCifrada;
    }
    public static String desxifraRot13(String cadena){
        if(cadena.isBlank()){ return "";} //si la cadena esta vacia no hace nada
        String cadenaDescifrada = "";
        for(int letraCadenaINT = 0;letraCadenaINT<cadena.length();letraCadenaINT++){ //bucle que recorre la cadena que se introduce en el parametro
            char letraCadenaCHR = cadena.charAt(letraCadenaINT);
            for(int letraAbecedarioINT = 0;letraAbecedarioINT<abecedarioMayus.length;letraAbecedarioINT++){ //bucle que itera en el abecedario mayusculas
                char letraAbecedarioMayusCHR = abecedarioMayus[letraAbecedarioINT];
                char letraAbecedarioMinusCHR = abecedarioMinus[letraAbecedarioINT];
                if(letraAbecedarioMayusCHR!=letraCadenaCHR){ //si no es la misma letra en mayusculas, lo comprueba con las minusculas
                    if(letraAbecedarioMinusCHR!=letraCadenaCHR){
                        continue;
                    }
                }
                boolean sePasa = letraAbecedarioINT < 13 ; // boolean que indica si se pasa o no
                if(sePasa){
                    if(Character.isUpperCase(letraCadenaCHR)){
                        cadenaDescifrada += abecedarioMayus[abecedarioMayus.length - (13-letraAbecedarioINT)];
                        continue;
                    }
                    cadenaDescifrada += abecedarioMinus[abecedarioMayus.length - (13-letraAbecedarioINT)];
                    continue;
                }
                if(Character.isUpperCase(letraCadenaCHR)){
                    cadenaDescifrada += abecedarioMayus[letraAbecedarioINT - 13];
                    continue;
                }
                cadenaDescifrada += abecedarioMinus[letraAbecedarioINT - 13];
                continue;
            }
        }
        return cadenaDescifrada;
        
    }

    public static void main(String[] args) {
        String texto = "";
        String opcion = "";
        Scanner scanner = new Scanner(System.in);
        while(!opcion.equals("c")&&!opcion.equals("d")){
                System.out.printf("Cifrar o Descifrar?(c) o (d): ");
                opcion = scanner.nextLine().trim().toLowerCase();
                System.out.println();
                if(!opcion.equals("c")&&!opcion.equals("d")){
                    System.out.println("Opcion no valida");
                    continue;
                }
        }
        while(texto.isBlank()){
            System.out.printf("Ingresa un texto: ");
            texto = scanner.nextLine();
            System.out.println();
            if(texto.isBlank()){
                System.out.println("El texto no puede ser en blanco");
                continue;
            }
        }
        if(opcion.equals("c")){
            System.out.printf("Texto cifrado: %s%n",xifraRot13(texto));
            scanner.close();
            return;
        }
        System.out.printf("Texto descifrado: %s%n",desxifraRot13(texto));
        scanner.close();
        return;
    }
}