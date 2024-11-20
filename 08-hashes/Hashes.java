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
        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, 65536, 128);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash);
    }

    public String forcaBruta(String alg, String hash, String salt)
    throws NoSuchAlgorithmException, Exception {
        String charset = "abcdefABCDEF1234567890!";
        npass = 0;

        // longitud 1
        for (int i1 = 0; i1 < charset.length(); i1++) {
            npass++;
            String contraseña = "" + charset.charAt(i1);
            if (checkPassword(contraseña, alg, hash, salt)) {
                return contraseña;
            }

            // longitud 2
            for (int i2 = 0; i2 < charset.length(); i2++) {
                npass++;
                contraseña = "" + charset.charAt(i1) + charset.charAt(i2);
                if (checkPassword(contraseña, alg, hash, salt)) {
                    return contraseña;
                }

                // Longitud 3
                for (int i3 = 0; i3 < charset.length(); i3++) {
                    npass++;
                    contraseña = "" + charset.charAt(i1) + charset.charAt(i2) + charset.charAt(i3);
                    if (checkPassword(contraseña, alg, hash, salt)) {
                        return contraseña;
                    }

                    for (int i4 = 0; i4 < charset.length(); i4++) {
                        npass++;
                        contraseña = "" + charset.charAt(i1) + charset.charAt(i2) + charset.charAt(i3) + charset.charAt(i4);
                        if (checkPassword(contraseña, alg, hash, salt)) {
                            return contraseña;
                        }

                        for (int i5 = 0; i5 < charset.length(); i5++) {
                            npass++;
                            contraseña = "" + charset.charAt(i1) + charset.charAt(i2) + charset.charAt(i3) + charset.charAt(i4)
                                    + charset.charAt(i5);
                            if (checkPassword(contraseña, alg, hash, salt)) {
                                return contraseña;
                            }


                            for (int i6 = 0; i6 < charset.length(); i6++) {
                                npass++;
                                contraseña = "" + charset.charAt(i1) + charset.charAt(i2) + charset.charAt(i3) + charset.charAt(i4)
                                        + charset.charAt(i5) + charset.charAt(i6);
                                if (checkPassword(contraseña, alg, hash, salt)) {
                                    return contraseña;
                                }
                            }
                        }
                    }
                }
            }
        }

        return null; //no ha encontrado la contraseña
    }

        //metodo auxiliar para comprobar la contraseña
    private boolean checkPassword(String guess, String alg, String hash, String salt)
        throws NoSuchAlgorithmException, Exception {
        String generatedHash = alg.equals("SHA-512") ? getSHA512AmbSalt(guess, salt)
                : getPBKDF2AmbSalt(guess, salt);
        return generatedHash.equals(hash);
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