package org.jcrypt.rsa;

import javax.crypto.Cipher;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;



public class RSAEncryption {
    KeyManager keyManager;

    public RSAEncryption(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public byte[] rsaEncrypt(byte[] data){
        RSAPublicKey publicKey = keyManager.getPublicKey();
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherData = cipher.doFinal(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherData;

    }

    public byte[] rsaDecrypt(byte[] data)  {
        RSAPrivateKey privateKey = keyManager.getPrivateKey();
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            cipherData = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherData;

    }
}
