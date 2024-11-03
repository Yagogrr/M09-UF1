package iticbcn.xifratge;

/*
 * permutaAlfabet(): permuta el alfabeto en base a objeto RANDOM
 * xifraPoliAlfabet(): después de comprobar que la letra es una letra, invoca a permutaAlfabet
 * desxifraPoliAlfa(): lo mismo que xifraPoliAlfabet
 * initRandom(): inicializa la variable Random
 */

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    public static final char[] ABECEDARIO_MAYUS = {
        'A', 'Á', 'À', 'Ä', 'B', 'C', 'Ç', 'D', 'É', 'È', 'Ë', 'E',
        'F', 'G', 'H', 'Í', 'Ì', 'Ï', 'I', 'J', 'K', 'L', 'M', 'N',
        'Ñ', 'Ó', 'Ò', 'Ö', 'O', 'P', 'Q', 'R', 'S', 'T', 'Ú', 'Ù',
        'Ü', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private char[] abecedario_permutado;
    private Random random;

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            initRandom(Long.parseLong(clau));
            String resultado = xifraPoliAlfa(msg);
            return new TextXifrat(resultado.getBytes(StandardCharsets.UTF_8));
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            initRandom(Long.parseLong(clau));
            String cadena = new String(xifrat.getBytes(), StandardCharsets.UTF_8);
            return desxifraPoliAlfa(cadena);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
    }

    private void permutaAlfabet() {
        // Creamos una lista y la completamos con el abecedario normal
        List<Character> abecedarioList = new ArrayList<>();
        for (char c : ABECEDARIO_MAYUS) {
            abecedarioList.add(c);
        }

        // Lo mezclamos de forma aleatoria
        Collections.shuffle(abecedarioList, random);

        // Inicializamos el abecedario permutado
        abecedario_permutado = new char[abecedarioList.size()];
        for (int i = 0; i < abecedarioList.size(); i++) {
            abecedario_permutado[i] = abecedarioList.get(i);
        }
    }

    private String xifraPoliAlfa(String cadena) {
        return xifraDesxifra(cadena, true);
    }

    private String desxifraPoliAlfa(String cadena) {
        return xifraDesxifra(cadena, false);
    }

    private void initRandom(long contrasenya) {
        random = new Random(contrasenya);
    }

    private String xifraDesxifra(String cadena, boolean xifra) {
        StringBuilder cadenaCifrada = new StringBuilder();

        // Bucle que recorre la cadena
        for (int i = 0; i < cadena.length(); i++) {
            char letra = cadena.charAt(i);

            // Si no es una letra, simplemente la añade
            if (!Character.isLetter(letra)) {
                cadenaCifrada.append(letra);
                continue;
            }

            // Permutamos el alfabeto
            permutaAlfabet();

            boolean encontrado = false;

            // Bucle que recorre el abecedario
            for (int j = 0; j < ABECEDARIO_MAYUS.length; j++) {
                if (xifra) {
                    // Si se cifra, busca la letra en ABECEDARIO_MAYUS
                    if (Character.toUpperCase(letra) == ABECEDARIO_MAYUS[j]) {
                        char letraP = abecedario_permutado[j];
                        // Mantiene el caso original
                        letraP = Character.isLowerCase(letra) ? Character.toLowerCase(letraP) : letraP;
                        cadenaCifrada.append(letraP);
                        encontrado = true;
                        break;
                    }
                } else {
                    // Si se descifra, busca la letra en abecedario_permutado
                    if (Character.toUpperCase(letra) == abecedario_permutado[j]) {
                        char letraP = ABECEDARIO_MAYUS[j];
                        // Mantiene el caso original
                        letraP = Character.isLowerCase(letra) ? Character.toLowerCase(letraP) : letraP;
                        cadenaCifrada.append(letraP);
                        encontrado = true;
                        break;
                    }
                }
            }

            // Si no se encontró la letra en el abecedario, la añade tal cual
            if (!encontrado) {
                cadenaCifrada.append(letra);
            }
        }

        return cadenaCifrada.toString();
    }
}
