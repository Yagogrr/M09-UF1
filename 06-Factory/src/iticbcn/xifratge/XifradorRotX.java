/*
 * Fixes:
 *  - Upper Constants :)
 *  - Accents :)
 *  - If it is not a letter, it writes it without encoding :)
 *  - Change variable names :)
 *  - StringBuffer usage :)
 * 
 * V1 changes:
 *  - xifraRotX(): Like the old version but instead of 13, x :)
 *  - desxifraRotX(): Like the old version but instead of 13, x :)
 *  - forcaBrutaRotX(): Tries every possibility to decode :)
 *  - main tests :)
 * 
 */
package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    public static final char[] ABECEDARIO_MAYUS = {
        'Á', 'À', 'Ä', 'A', 'B', 'C', 'Ç', 'D', 'É', 'È', 'Ë', 'E',
        'F', 'G', 'H', 'Í', 'Ì', 'Ï', 'I', 'J', 'K', 'L', 'M', 'N',
        'Ñ', 'Ó', 'Ò', 'Ö', 'O', 'P', 'Q', 'R', 'S', 'T', 'Ú', 'Ù',
        'Ü', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    public static final char[] ABECEDARIO_MINUS = {
        'á', 'à', 'ä', 'a', 'b', 'c', 'ç', 'd', 'é', 'è', 'ë', 'e',
        'f', 'g', 'h', 'í', 'ì', 'ï', 'i', 'j', 'k', 'l', 'm', 'n',
        'ñ', 'ó', 'ò', 'ö', 'o', 'p', 'q', 'r', 's', 't', 'ú', 'ù',
        'ü', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    @Override
    public TextXifrat xifra(String mensaje, String clave) throws ClauNoSuportada {
        int claveInt;
        try {
            claveInt = Integer.parseInt(clave);
            if (claveInt < 0 || claveInt > ABECEDARIO_MAYUS.length) {
                throw new ClauNoSuportada("La clave de RotX debe ser un entero de 0 a " + ABECEDARIO_MAYUS.length);
            }
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clave de RotX debe ser un entero de 0 a " + ABECEDARIO_MAYUS.length);
        }

        StringBuffer resultadoCifrado = xifraRotX(new StringBuffer(mensaje), claveInt);
        byte[] resultadoBytes = resultadoCifrado.toString().getBytes();
        return new TextXifrat(resultadoBytes);
    }

    @Override
    public String desxifra(TextXifrat textoCifrado, String clave) throws ClauNoSuportada {
        int claveInt;
        try {
            claveInt = Integer.parseInt(clave);
            if (claveInt < 0 || claveInt > ABECEDARIO_MAYUS.length) {
                throw new ClauNoSuportada("La clave de RotX debe ser un entero de 0 a " + ABECEDARIO_MAYUS.length);
            }
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clave de RotX debe ser un entero de 0 a " + ABECEDARIO_MAYUS.length);
        }

        String mensajeCifrado = new String(textoCifrado.getBytes());
        StringBuffer resultadoDescifrado = desxifraRotX(new StringBuffer(mensajeCifrado), claveInt);
        return resultadoDescifrado.toString();
    }

    // Función para cifrar
    public StringBuffer xifraRotX(StringBuffer cadena, int x) {
        if (cadena == null || cadena.length() == 0) {
            return new StringBuffer();
        }

        StringBuffer cadenaCifrada = new StringBuffer();
        for (int i = 0; i < cadena.length(); i++) {
            char letra = cadena.charAt(i);
            if (!Character.isLetter(letra)) {
                cadenaCifrada.append(letra);
                continue;
            }

            boolean encontrada = false;
            for (int j = 0; j < ABECEDARIO_MINUS.length; j++) {
                if (Character.toLowerCase(letra) != ABECEDARIO_MINUS[j]) {
                    continue;
                }

                int nuevaPosicion = (j + x) % ABECEDARIO_MAYUS.length;
                if (Character.isUpperCase(letra)) {
                    cadenaCifrada.append(ABECEDARIO_MAYUS[nuevaPosicion]);
                } else {
                    cadenaCifrada.append(ABECEDARIO_MINUS[nuevaPosicion]);
                }
                encontrada = true;
                break; // Salir del bucle una vez encontrada la letra
            }

            // Si la letra no se encuentra en el abecedario, se añade tal cual
            if (!encontrada) {
                cadenaCifrada.append(letra);
            }
        }
        return cadenaCifrada;
    }

    // Función para descifrar
    public StringBuffer desxifraRotX(StringBuffer cadena, int x) {
        if (cadena == null || cadena.length() == 0) {
            return new StringBuffer();
        }

        StringBuffer cadenaDescifrada = new StringBuffer();
        for (int i = 0; i < cadena.length(); i++) {
            char letra = cadena.charAt(i);
            if (!Character.isLetter(letra)) {
                cadenaDescifrada.append(letra);
                continue;
            }

            boolean encontrada = false;
            for (int j = 0; j < ABECEDARIO_MINUS.length; j++) {
                if (Character.toLowerCase(letra) != ABECEDARIO_MINUS[j]) {
                    continue;
                }

                int nuevaPosicion = (j - x);
                if (nuevaPosicion < 0) {
                    nuevaPosicion += ABECEDARIO_MAYUS.length;
                }

                if (Character.isUpperCase(letra)) {
                    cadenaDescifrada.append(ABECEDARIO_MAYUS[nuevaPosicion]);
                } else {
                    cadenaDescifrada.append(ABECEDARIO_MINUS[nuevaPosicion]);
                }
                encontrada = true;
                break; // Salir del bucle una vez encontrada la letra
            }

            // Si la letra no se encuentra en el abecedario, se añade tal cual
            if (!encontrada) {
                cadenaDescifrada.append(letra);
            }
        }
        return cadenaDescifrada;
    }

    // Función de fuerza bruta para descifrar
    public void forcaBrutaRotX(StringBuffer cadenaCifrada) {
        for (int i = 0; i < ABECEDARIO_MAYUS.length; i++) {
            StringBuffer posibleDescifrado = desxifraRotX(cadenaCifrada, i);
            System.out.printf("%d: %s%n", i, posibleDescifrado.toString());
        }
    }
}
