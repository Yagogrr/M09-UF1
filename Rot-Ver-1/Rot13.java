/*
 * Fixes:
 *  -Upper Constants :)
 *  -Accents :)
 *  -If is not letter, it write it without codify it :)
 *  -Change variable names :)
 *  -String buffer
 * 
 * V1 changes:
 *  -xifraRotX(): Like the old vesion but instead of 13, x
 *  -desxifrarotx(): Like the old vesion but instead of 13, x
 *  -forcaBrutaRotX(): Tries every posibility to decode
 */
import java.util.Scanner;

public class Rot13{
    public static final char[] ABECEDARIO_MAYUS = {'Á', 'À', 'Ä', 'A', 'B', 'C', 'Ç', 'D', 'É', 'È', 'Ë', 'E', 'F', 'G', 'H', 'Í', 'Ì', 'Ï', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'Ó', 'Ò', 'Ö', 'O', 'P', 'Q', 'R', 'S', 'T', 'Ú', 'Ù', 'Ü', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static final char[] ABECEDARIO_MINUS = {'á', 'à', 'ä', 'a', 'b', 'c', 'ç', 'd', 'é', 'è', 'ë', 'e', 'f', 'g', 'h', 'í', 'ì', 'ï', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'ó', 'ò', 'ö', 'o', 'p', 'q', 'r', 's', 't', 'ú', 'ù', 'ü', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static boolean contains(char[] array,char c){
        for(int i = 0;i<array.length;i++){
            if(array[i]==c){return true;}
        }
        return false;
    }
    //función para cifrar
    public static StringBuffer xifraRot13(String cadena){
        if(cadena.isBlank()){ return null;} //si la cadena esta vacia no hace nada
        StringBuffer cadenaCifrada = new StringBuffer();
        for(int i = 0;i<cadena.length();i++){ //bucle que recorre la cadena que se introduce en el parametro
            char letra = cadena.charAt(i);
            if(!Character.isLetter(letra)){
                cadenaCifrada.append(letra);
                continue;
            }
            for(int j = 0;j<ABECEDARIO_MAYUS.length;j++){ //bucle que itera en el abecedario mayusculas
                if(Character.toLowerCase(letra)!=ABECEDARIO_MINUS[j]){
                    continue;
                }
                boolean sePasa = j + 13 >= ABECEDARIO_MAYUS.length; // boolean que indica si se pasa o no
                if(sePasa){
                    if(Character.isUpperCase(letra)){
                        cadenaCifrada.append(ABECEDARIO_MAYUS[(j + 13) - ABECEDARIO_MAYUS.length]);
                        continue;
                    }
                    cadenaCifrada.append(ABECEDARIO_MINUS[(j + 13) - ABECEDARIO_MAYUS.length]);
                    continue;
                }
                if(Character.isUpperCase(letra)){
                    cadenaCifrada.append(ABECEDARIO_MAYUS[j + 13]);
                    continue;
                }
                cadenaCifrada.append(ABECEDARIO_MINUS[j + 13]);
                continue;

            }
        }
        return cadenaCifrada;
    }
    public static StringBuffer desxifraRot13(String cadena){
        if(cadena.isBlank()){ return null;} //si la cadena esta vacia no hace nada
        StringBuffer cadenaDescifrada = new StringBuffer();
        for(int i = 0;i<cadena.length();i++){ //bucle que recorre la cadena que se introduce en el parametro
            char letra = cadena.charAt(i);
            if(!Character.isLetter(letra)){
                cadenaDescifrada.append(letra);
                continue;
            }
            for(int j = 0;j<ABECEDARIO_MAYUS.length;j++){ //bucle que itera en el abecedario mayusculas
                if(Character.toLowerCase(letra)!=ABECEDARIO_MINUS[j]){
                    continue;
                }
                boolean sePasa = j < 13 ; // boolean que indica si se pasa o no
                if(sePasa){
                    if(Character.isUpperCase(letra)){
                        cadenaDescifrada.append( ABECEDARIO_MAYUS[ABECEDARIO_MAYUS.length - (13-j)]);
                        continue;
                    }
                    cadenaDescifrada.append( ABECEDARIO_MINUS[ABECEDARIO_MAYUS.length - (13-j)]);
                    continue;
                }
                if(Character.isUpperCase(letra)){
                    cadenaDescifrada.append( ABECEDARIO_MAYUS[j - 13]);
                    continue;
                }
                cadenaDescifrada.append( ABECEDARIO_MINUS[j - 13]);
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