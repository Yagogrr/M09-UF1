/*
 * Fixes:
 *  -Upper Constants :)
 *  -Accents :)
 *  -If is not letter, it write it without codify it :)
 *  -Change variable names :)
 *  -String buffer :)
 *  - Fix acentos :)
 * 
 * V1 changes:
 *  -xifraRotX(): Like the old vesion but instead of 13,x :)
 *  -desxifrarotx(): Like the old vesion but instead of 13, x :)
 *  -forcaBrutaRotX(): Tries every posibility to decode :)
 *  - main tests :)
 * 
 */
package iticbcn.xifratge;

public class XifradorRotX implements Xifrador{
    public static final char[] ABECEDARIO_MAYUS = {'Á', 'À', 'Ä', 'A', 'B', 'C', 'Ç', 'D', 'É', 'È', 'Ë', 'E', 'F', 'G', 'H', 'Í', 'Ì', 'Ï', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'Ó', 'Ò', 'Ö', 'O', 'P', 'Q', 'R', 'S', 'T', 'Ú', 'Ù', 'Ü', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static final char[] ABECEDARIO_MINUS = {'á', 'à', 'ä', 'a', 'b', 'c', 'ç', 'd', 'é', 'è', 'ë', 'e', 'f', 'g', 'h', 'í', 'ì', 'ï', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'ó', 'ò', 'ö', 'o', 'p', 'q', 'r', 's', 't', 'ú', 'ù', 'ü', 'u', 'v', 'w', 'x', 'y', 'z'};

    public TextXifrat xifra(String msg,String clau)throws ClauNoSuportada{
        int clauInt = Integer.parseInt(clau);
        String resultat = xifraRotx(new StringBuffer(msg), clauInt).toString();
        byte[] resltatBytes = resultat.getBytes();
        return new TextXifrat(resltatBytes);
    }
    public String desxifra(TextXifrat xifrat, String clau)  throws ClauNoSuportada{
        String xifratStr = xifrat.toString();
        int clauInt =  Integer.parseInt(clau);
        String resultado = desxifraRotx(new StringBuffer(xifratStr), clauInt).toString();
        return resultado;
    }

    //función para cifrar
    public StringBuffer xifraRotx(StringBuffer cadena,int x){
        if(cadena.isEmpty()){ return null;} //si la cadena esta vacia no hace nada
        StringBuffer cadenaCifrada = new StringBuffer();
        for(int i = 0;i<cadena.length();i++){ //bucle que recorre la cadena que se introduce en el parametro
            char letra = cadena.charAt(i);
            if(!Character.isLetter(letra)){
                cadenaCifrada.append(letra);
                continue;
            }
            for(int j = 0;j<ABECEDARIO_MAYUS.length;j++){ //bucle que itera en el abecedario mayusculas
                if(Character.toLowerCase(letra)!=ABECEDARIO_MINUS[j]){ //si no es esa letra del abecedario, continua con la siguiente
                    continue;
                }
                boolean sePasa = j + x >= ABECEDARIO_MAYUS.length; // boolean que indica si se pasa o no
                if(sePasa){
                    if(Character.isUpperCase(letra)){
                        cadenaCifrada.append(ABECEDARIO_MAYUS[(j + x) - ABECEDARIO_MAYUS.length]);
                        continue;
                    }
                    cadenaCifrada.append(ABECEDARIO_MINUS[(j + x) - ABECEDARIO_MAYUS.length]);
                    continue;
                }
                if(Character.isUpperCase(letra)){
                    cadenaCifrada.append(ABECEDARIO_MAYUS[j + x]);
                    continue;
                }
                cadenaCifrada.append(ABECEDARIO_MINUS[j + x]);
                continue;

            }
        }
        return cadenaCifrada;
    }
    public StringBuffer desxifraRotx(StringBuffer cadena,int x){
        if(cadena.isEmpty()){ return null;} //si la cadena esta vacia no hace nada
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
                boolean sePasa = j < x ; // boolean que indica si se pasa o no
                if(sePasa){
                    if(Character.isUpperCase(letra)){
                        cadenaDescifrada.append( ABECEDARIO_MAYUS[ABECEDARIO_MAYUS.length - (x-j)]);
                        continue;
                    }
                    cadenaDescifrada.append( ABECEDARIO_MINUS[ABECEDARIO_MAYUS.length - (x-j)]);
                    continue;
                }
                if(Character.isUpperCase(letra)){
                    cadenaDescifrada.append( ABECEDARIO_MAYUS[j - x]);
                    continue;
                }
                cadenaDescifrada.append( ABECEDARIO_MINUS[j - x]);
                continue;
            }
        }
        return cadenaDescifrada;
        
    }
    public void forcaBrutaRotX(StringBuffer cadenaCifrada){
        for(int i = 0;i<ABECEDARIO_MAYUS.length;i++){
            System.out.printf("%d: %s%n",i,desxifraRotx(cadenaCifrada, i));
        }
    }
    public void main(String[] args) {
        StringBuffer textoCifrado = new StringBuffer(xifraRotx(new StringBuffer("Buenas Tardes"), 4));
        System.out.printf("Texto cifrado: %s%n",textoCifrado);
        System.out.printf("Texto descifrado: %s%n",desxifraRotx(textoCifrado,4));
        System.out.println("Fuerza bruta:");
        forcaBrutaRotX(textoCifrado);
    }
}