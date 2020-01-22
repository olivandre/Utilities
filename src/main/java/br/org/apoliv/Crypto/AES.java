package br.org.apoliv.Crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES {
    
    private static final String METHOD_ENCRYPT = "AES";
    
    private static final byte[] KEY = { 20, 50, 2, -45, 90, 2, 46, 0, 127, 48, 10, -1, -37, -90, 70, -40};

    /**
     * Criptografa a valor passado por parametro
     * @param value String a ser criptografada
     * @return String criptografada
     */
    public static String encrypt(String vlr) {

        try {
            SecretKeySpec skeySpec = new SecretKeySpec(KEY, METHOD_ENCRYPT);
            
            Cipher cipher = Cipher.getInstance(METHOD_ENCRYPT);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(vlr.getBytes());

            return new BASE64Encoder().encode(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar informações " + e.getMessage());
        }
    }

    /**
     * Metodo utilizado para descriptografar a senha passada.
     * @param vlr String criptografada
     * @return Valor descriptografado
     */
    public static String dencrypt(String vlr) {

        byte[] decrypted = null;

        try {
            SecretKeySpec skeySpec = new SecretKeySpec(KEY, METHOD_ENCRYPT);

            byte[] decoded = new BASE64Decoder().decodeBuffer(vlr);

            Cipher cipher = Cipher.getInstance(METHOD_ENCRYPT);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decrypted = cipher.doFinal(decoded);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar informações " + e.getMessage());
        }
        return new String(decrypted);
    }
}