package br.org.apoliv.Crypto;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Crypto {

    Cipher ecipher;

    Cipher dcipher;

    private static final String KEY_STRING = "O Grêmio Vai Sair Campeão!";

    Crypto(SecretKey key) {
        // Create an 8-byte initialization vector
        byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        
        try {
            ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

            // CBC requires an initialization vector
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (java.security.InvalidAlgorithmParameterException e) {
        } catch (javax.crypto.NoSuchPaddingException e) {
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.security.InvalidKeyException e) {}
    }

    // Buffer used to transport the bytes from one stream to another
    byte[] buf = new byte[1024];

    public void encrypt(InputStream in, OutputStream out) {
        try {
            // Bytes written to out will be encrypted
            out = new CipherOutputStream(out, ecipher);

            // Read in the cleartext bytes and write to out to encrypt
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0) {
                out.write(buf, 0, numRead);
            }
            out.close();
        } catch (java.io.IOException e) {
        }
    }

    public void decrypt(InputStream in, OutputStream out) {
        try {
            // Bytes read from in will be decrypted
            in = new CipherInputStream(in, dcipher);

            // Read in the decrypted bytes and write the cleartext to out
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0) {
                out.write(buf, 0, numRead);
            }
            out.close();
        } catch (java.io.IOException e) {
        }
    }

    public static void main(String[] args) {
        try {
            // Generate a temporary key. In practice, you would save this key.
            // See also e464 Encrypting with DES Using a Pass Phrase.
            //SecretKey key = KeyGenerator.getInstance("DES").generateKey();
            SecretKey key = getKey();

            // Create encrypter/decrypter class
            Crypto encrypter = new Crypto(key);

            // Encrypt
            encrypter.encrypt(new FileInputStream("c://arquivo_original.jpg"), new FileOutputStream("c://arquivo_criptogrado.jpg"));

            // Decrypt
            encrypter.decrypt(new FileInputStream("c://arquivo_criptogrado.jpg"), new FileOutputStream("c://arquivo_descriptogrado.jpg"));
        } catch (Exception e) {
        }
    }

    private static SecretKey getKey() {
        try {
            byte[] bytes = getBytes(KEY_STRING);
            DESKeySpec pass = new DESKeySpec(bytes);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            SecretKey s = skf.generateSecret(pass);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
  
    private static byte[] getBytes( String str ) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bos.write( str.getBytes() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
}