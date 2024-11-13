import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public String forcaBruta(String alg, String hash, String salt) throws NoSuchAlgorithmException, Exception {
        String charset = "abcdefABCDEF1234567890!";
        int maxLength = 6;
        npass = 0;
    
        for (int length = 1; length <= maxLength; length++) {
            char[] password = new char[length];
            String result = bruteForceRecursive(alg, hash, salt, charset, password, 0);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
    
    private String bruteForceRecursive(String alg, String hash, String salt, String charset, char[] password, int position) throws NoSuchAlgorithmException, Exception {
        if (position == password.length) {
            npass++;
            String guess = new String(password);
            String generatedHash = alg.equals("SHA-512") ? getSHA512AmbSalt(guess, salt) : getPBKDF2AmbSalt(guess, salt);
            if (generatedHash.equals(hash)) {
                return guess;
            }
            return null;
        }
    
        for (int i = 0; i < charset.length(); i++) {
            password[position] = charset.charAt(i);
            String result = bruteForceRecursive(alg, hash, salt, charset, password, position + 1);
            if (result != null) {
                return result;
            }
        }
        return null;
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