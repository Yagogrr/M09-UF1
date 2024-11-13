import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {

    public int npass = 0;

    public String getSHA512AmbSalt(String pw, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());
        byte[] bytes = md.digest(pw.getBytes());
        HexFormat hex = HexFormat.of();
        return hex.formatHex(bytes);
    }

    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        char[] passwordChars = pw.toCharArray();
        byte[] saltBytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, 10000, 128);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash);
    }

    public String forcaBruta(String alg, String hash, String salt)
            throws NoSuchAlgorithmException, Exception {
        String charset = "abcdefABCDEF1234567890!";
        int maxLength = 6;
        npass = 0;

        // Iterar sobre todas las posibles longitudes de la contraseña
        for (int length = 1; length <= maxLength; length++) {
            // Inicializar un array de caracteres para almacenar la contraseña actual
            char[] password = new char[length];
            Arrays.fill(password, charset.charAt(0));

            // Generar y probar todas las combinaciones posibles de esta longitud
            while (true) {
                npass++;
                String guess = new String(password);
                String generatedHash = alg.equals("SHA-512") ? getSHA512AmbSalt(guess, salt)
                        : getPBKDF2AmbSalt(guess, salt);

                if (generatedHash.equals(hash)) {
                    return guess;
                }

                // Incrementar la contraseña al siguiente valor lexicográfico
                if (!incrementPassword(password, charset)) {
                    break; // Si no se puede incrementar más, terminamos esta longitud
                }
            }
        }
        return null; // No se encontró ninguna coincidencia
    }

    // Método auxiliar para incrementar la contraseña
    private boolean incrementPassword(char[] password, String charset) {
        for (int i = password.length - 1; i >= 0; i--) {
            int index = charset.indexOf(password[i]);
            if (index < charset.length() - 1) {
                password[i] = charset.charAt(index + 1);
                return true;
            } else {
                password[i] = charset.charAt(0);
            }
        }
        return false; // Si hemos recorrido toda la longitud y no se puede incrementar más
    }

    public String getInterval(long t1, long t2) {
        long millis = t2 - t1;
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = (millis / (1000 * 60 * 60)) % 24;
        long days = (millis / (1000 * 60 * 60 * 24));
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis",
                days, hours, minutes, seconds, millis % 1000);
    }

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = { "SHA-512", "PBKDF2" };
        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("---------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---------------------------\n\n");
        }
    }
}