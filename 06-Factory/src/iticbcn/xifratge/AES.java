package iticbcn.xifratge;
import javax.crypto.Cipher; // Importa la clase Cipher, necesaria para el cifrado y descifrado.
import javax.crypto.spec.SecretKeySpec; // Importa SecretKeySpec, que define la clave para el cifrado.
import javax.crypto.spec.IvParameterSpec; // Importa IvParameterSpec, que define el IV (Vector de Inicialización).
import java.security.*; // Importa las clases de seguridad, incluidas SecureRandom y MessageDigest.
import java.io.ByteArrayOutputStream; // Importa ByteArrayOutputStream para manipular arrays de bytes.
import java.util.Arrays; // Importa Arrays, que se usa para manipular arrays de bytes.

public class AES implements Xifrador{
    // Definición de constantes
    public static final String ALGORISME_XIFRAT = "AES"; // Algoritmo de cifrado a usar (AES).
    public static final String ALGORISME_HASH = "SHA-256"; // Algoritmo de hash a usar (SHA-256).
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding"; // Modo de operación del AES (AES/CBC con relleno PKCS5).

    private static final int MIDA_IV = 16; // Tamaño del Vector de Inicialización (16 bytes).
    private static byte[] iv = new byte[MIDA_IV]; // Vector de Inicialización de 16 bytes.
    private static final String CLAU = "LaClauSecretaQueVulguis"; // Clave secreta para el cifrado.

    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada{
        try{
            return new TextXifrat(xifraAES(msg, clau));
        } catch (Exception e){
            throw new ClauNoSuportada(null);
        }
    }

    public String desxifra(TextXifrat xifrat, String clau)  throws ClauNoSuportada{
        try{
            return desxifraAES(xifrat.getBytes(), clau);
        } catch (Exception e){
            throw new ClauNoSuportada(null);
        }
    }

    // Método para cifrar un mensaje usando AES
    public static byte[] xifraAES(String msg, String password) throws Exception {
        // Convierte el mensaje a bytes en UTF-8
        byte[] bytes = msg.getBytes("UTF-8");

        // Genera IV aleatorio
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv); // Llena el IV con valores aleatorios.
        IvParameterSpec ivparameterspec = new IvParameterSpec(iv); // Crea una especificación para el IV.

        // Genera hash del password para obtener la clave de cifrado
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyBytes = digest.digest(password.getBytes("UTF-8")); // Calcula el hash del password.

        // Nos quedamos con los primeros 16 bytes para AES-128
        byte[] keyBytes16 = Arrays.copyOf(keyBytes, 16);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes16, ALGORISME_XIFRAT); // Crea la clave secreta con los 16 bytes.

        // Inicializa el cifrador en modo ENCRYPT_MODE (cifrar)
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivparameterspec);
        byte[] encrypted = cipher.doFinal(bytes); // Cifra los datos.

        // Combina el IV y el mensaje cifrado en un solo array de bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(iv); // Añade el IV.
        outputStream.write(encrypted); // Añade el mensaje cifrado.
        byte[] encryptedMessage = outputStream.toByteArray(); // Combina ambos.

        // Devuelve el IV concatenado con el mensaje cifrado
        return encryptedMessage;
    }

    // Método para descifrar un mensaje usando AES
    public static String desxifraAES(byte[] bMsgXifrat, String password) throws Exception {
        // Separa el IV del mensaje cifrado
        byte[] iv = Arrays.copyOfRange(bMsgXifrat, 0, MIDA_IV); // Los primeros 16 bytes son el IV.
        byte[] encryptedMessage = Arrays.copyOfRange(bMsgXifrat, MIDA_IV, bMsgXifrat.length); // El resto es el mensaje cifrado.
        IvParameterSpec ivparameterspec = new IvParameterSpec(iv); // Crea una especificación para el IV.

        // Genera hash del password para obtener la clave de descifrado
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyBytes = digest.digest(password.getBytes("UTF-8"));

        // Nos quedamos con los primeros 16 bytes para AES-128
        byte[] keyBytes16 = Arrays.copyOf(keyBytes, 16);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes16, ALGORISME_XIFRAT); // Crea la clave secreta con los 16 bytes.

        // Inicializa el cifrador en modo DECRYPT_MODE (descifrar)
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivparameterspec);
        byte[] decrypted = cipher.doFinal(encryptedMessage); // Descifra los datos.

        // Devuelve el mensaje descifrado como un String
        return new String(decrypted, "UTF-8");
    }

}