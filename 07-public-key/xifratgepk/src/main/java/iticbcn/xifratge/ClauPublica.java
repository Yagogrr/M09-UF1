package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class ClauPublica {
    public KeyPair generaParellClausRSA() throws Exception{
        //generador de claves con el algortimo RSA
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  

        //genera las claves
        KeyPair keyPair = keyPairGen.generateKeyPair();
        return keyPair;
    }
    public byte[] xifraRSA(String msg, PublicKey clauPublica)throws Exception{
        //convertir el mensaje a bytes siguiendo el standard UTF-8
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);

        //inicializa el cipher con el algoritmo RSA(que es el que hemos usado para generar las claves)
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //lo iniciamos en modo encriptacion junto a la clave publica
        cipher.init(Cipher.ENCRYPT_MODE,clauPublica);

        //haces la encriptación
        byte[] msgEncripted = cipher.doFinal(msgBytes);

        return msgEncripted;
    }
    public String desxifraRSA(byte[] msgXifrat, PrivateKey ClauPrivada)throws Exception{
        //inicializa el cipher con el algoritmo RSA(que es el que hemos usado para generar las claves)
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //inicializa en modo desencripta con la clave privada 
        cipher.init(Cipher.DECRYPT_MODE,ClauPrivada);

        //haces la desencriptación
        byte[] msgDecrypted = cipher.doFinal(msgXifrat);

        //devolvemos un string siguiendo el standard UTF8
        return new String(msgDecrypted, StandardCharsets.UTF_8);
    }
}
