package iticbcn.xifratge;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class XifradorAES implements Xifrador{
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "LaClauSecretaQueVulguis";

    public byte[] xifraAES(String msg, String password) throws Exception{
        // Obtenir els bytes de l'String
        byte[] bytes = msg.getBytes("UTF-8");

        // Genera IV aleatori   
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivparameterspec = new IvParameterSpec(iv);

        // Genera hash del password per obtenir la clau
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyBytes = digest.digest(password.getBytes("UTF-8"));
        
        // Ens quedem amb els 16 bytes per a AES-128
        byte[] keyBytes16 = Arrays.copyOf(keyBytes, 16);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes16, ALGORISME_XIFRAT);

        // Encrypt
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivparameterspec);
        byte[] encrypted = cipher.doFinal(bytes);

        // Combinar IV i part xifrada
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(iv);
        outputStream.write(encrypted);
        byte[] encryptedMessage = outputStream.toByteArray();

        // return iv + msg xifrat
        return encryptedMessage;
    }

    public String desxifraAES(byte[] bMsgXifrat, String password)throws Exception{
        return null;
    }
    public void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet",
        "Hola Andrés cómo está tu cuñado",
        "Àgora ïlla Ôtto"};
        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES (bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: "
                + e.getLocalizedMessage ());
            }
            System.out.println("--------------------" );
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }
}